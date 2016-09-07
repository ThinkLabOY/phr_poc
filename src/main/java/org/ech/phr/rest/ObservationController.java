package org.ech.phr.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import org.ech.phr.model.Resource;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.service.ResourceService;
import org.ech.phr.util.FhirUtil;

@Slf4j
@RestController
@RequestMapping("/fhir/Observation")
public class ObservationController {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Resource> getObservations(@RequestParam(value = "patient.identifier.value", required = true) String patientIdentifierValue,
			@RequestParam(value = "patient.identifier.system", required = true) String patientIdentifierSystem,
			@RequestParam(value = "organisation.identifier.value", required = true) String organisationIdentifierValue,
			@RequestParam(value = "code.code", required = true) String codeCode, @RequestParam(value = "code.system", required = true) String codeSystem) {

		log.debug("getObservations: " + patientIdentifierValue + " " + patientIdentifierSystem + " " + organisationIdentifierValue + " " + codeCode + " "
				+ codeSystem);
		List<Resource> resources = new ArrayList<>();
		try {
			resources.addAll(resourceService.getResourceReferences(patientIdentifierValue, patientIdentifierSystem, organisationIdentifierValue,
					FhirUtil.OID_PHR, codeCode, codeSystem));
		}
		catch (BusinessException e) {
			log.error("Error: {}", e.getStackTrace());
		}

		// TODO: query url and parameters should not be hard coded
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Resource[]> response = restTemplate.getForEntity(
				"http://localhost:9090/fhir/Observation?patient.identifier.value=37804230234&patient.identifier.system=http://www.politsei.ee/&organisation.identifier.value=ORG1&code.code=3141-9&code.system=http://loinc.org",
				Resource[].class);

		if (response != null && response.getBody() != null) {
			resources.addAll(Arrays.asList(response.getBody()));
		}

		resources.forEach(resource -> log.debug("resource - ResourceId: " + resource.getResourceId() + ", ResourceIdOid: " + resource.getResourceIdOid()
				+ ", TypeCode: " + resource.getType().getTypeCode()));

		return resources;
	}
	// Test url
	// http://localhost:8080/fhir/Observation?patient.identifier.value=37804230234&patient.identifier.system=http://www.politsei.ee/&organisation.identifier.value=ORG1&code.code=3141-9&code.system=http://loinc.org
}
