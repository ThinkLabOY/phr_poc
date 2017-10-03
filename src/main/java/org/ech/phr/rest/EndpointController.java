package org.ech.phr.rest;

import static org.ech.phr.model.fhir.ResourceTypeEnum.ORGANIZATION;
import static org.ech.phr.rest.ParameterConstants.MANAGING_ORGANIZATION;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.fhir.Endpoint;
import org.ech.phr.model.fhir.HasManagingOrganization;
import org.ech.phr.model.fhir.Reference;
import org.ech.phr.model.fhir.ResourceTypeEnum;
import org.ech.phr.model.hbase.Organisation;
import org.ech.phr.service.OrganisationService;
import org.ech.phr.util.FhirUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/fhir/Endpoint")
public class EndpointController {

	@Autowired
	private OrganisationService organisationService;
	private static final String EXAMPLE = "{\"resourceType\": \"Endpoint\", \"managingOrganization\": {\"reference\": \"Organization/ORG1\"}, \"address\": \"http://org1.org/fhir\"}";

	@ApiOperation("Query registered endpoints managed by organization")
	@RequestMapping(method = RequestMethod.GET)
	public List<Endpoint> findEndpoint(@RequestParam(value = MANAGING_ORGANIZATION, required = false) @ApiParam(example = "ORG1", required=false) String organisationId) throws BusinessException {
		List<Endpoint> endpointList  = null;
		if (organisationId != null) {
			Organisation organisation = organisationService.getOrganisationById(organisationId, FhirUtil.OID_PHR);
			if (organisation != null) {
				endpointList = ImmutableList.of(mapEndpointFromOrganisation(organisation));
			}
		}
		else {
			List<Organisation> organisationList = organisationService.getOrganisations();
			if(organisationList != null) {
				endpointList = new LinkedList<>();
				Map<String, List<Organisation>> organisationMap = organisationList.stream().collect(Collectors.groupingBy(Organisation::getColumnQualifier));
				for (List<Organisation> distinctOrganisationList : organisationMap.values()) {
					//take only first value, as all elements with the same qualifier reference the same entity
					endpointList.add(mapEndpointFromOrganisation(distinctOrganisationList.get(0)));
				}
			}
		}
		return endpointList;
	}

	@ApiOperation("Register endpoint managed by organization")
	@RequestMapping(method = RequestMethod.POST)
	public Endpoint saveEndpoint(@ApiParam(required = true, value = EXAMPLE) @RequestBody @Valid Endpoint endpoint, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		Organisation organisation = mapOrganisationFromEndpoint(endpoint);
		organisation = organisationService.registerOrganisation(organisation.getId(), FhirUtil.OID_PHR, organisation.getUrl());
		Endpoint resultEndpoint = mapEndpointFromOrganisation(organisation);
		addLocationPath(ORGANIZATION, organisation.getId(), request, response);
		return resultEndpoint;
	}

	private void addLocationPath(ResourceTypeEnum resource, String organisationId, HttpServletRequest request, HttpServletResponse response) {
		String serverPath = request.getRequestURL().toString();
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		queryParams.add(MANAGING_ORGANIZATION, organisationId);
		response.setHeader(ParameterConstants.HTTP_HEADER_LOCATION, serverPath + FhirUtil.createLocationPath(resource, queryParams));
	}


	private Organisation mapOrganisationFromEndpoint(Endpoint endpoint) {
		String organizationId = extractOrganizationId(endpoint);
		Organisation organisation = Organisation.builder()
				.id(organizationId)
				.url(endpoint.getAddress())
				.build();
		return organisation;
	}

	public static String extractOrganizationId(HasManagingOrganization hasManagingOrganization) {
		Reference managingOrganization = hasManagingOrganization.getManagingOrganization();
		String[] referenceElements = managingOrganization.getReference().split(FhirUtil.DELIMITER);
		String organizationId = referenceElements[referenceElements.length-1];
		return organizationId;
	}

	private Endpoint mapEndpointFromOrganisation(Organisation organisation) {
		Reference managingOrganization = Reference.builder()
				.reference(ResourceTypeEnum.ORGANIZATION.getText() + FhirUtil.DELIMITER + organisation.getId())
				.build();
		Endpoint endpoint = Endpoint.builder()
				.resourceType(ResourceTypeEnum.ENDPOINT.getText())
				.address(organisation.getUrl())
				.managingOrganization(managingOrganization)
				.build();
		return endpoint;
	}

}
