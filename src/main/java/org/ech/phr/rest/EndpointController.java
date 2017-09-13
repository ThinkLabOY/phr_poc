package org.ech.phr.rest;

import static org.ech.phr.rest.ParameterConstants.*;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiModelProperty;
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
	public Endpoint findEndpoint(@RequestParam(MANAGING_ORGANIZATION) @ApiParam(example = "ORG1") String organisationId) throws BusinessException {
		Endpoint endpoint  = null;
		if (organisationId != null) {
			Organisation organisation = organisationService.getOrganisationById(organisationId, FhirUtil.OID_PHR);
			if (organisation != null) {
				endpoint = mapEndpointFromOrganisation(organisation);
			}
		}
		return endpoint;
	}

	@ApiOperation("Register endpoint managed by organization")
	@RequestMapping(method = RequestMethod.POST)
	public Endpoint saveEndpoint(@ApiParam(required = true, value = EXAMPLE) @RequestBody @Valid Endpoint endpoint) throws BusinessException {
		Organisation organisation = mapOrganisationFromEndpoint(endpoint);
		organisation = organisationService.registerOrganisation(organisation.getId(), FhirUtil.OID_PHR, organisation.getUrl());
		Endpoint resultEndpoint = mapEndpointFromOrganisation(organisation);
		return resultEndpoint;
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
