package org.ech.phr.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/fhir/Observation")
public class ObservationController {

	@RequestMapping(method = RequestMethod.GET)
	public List<Resource> getMockObservations(@RequestParam(value = "patient.identifier.value", required = true) String patientIdentifierValue,
			@RequestParam(value = "patient.identifier.system", required = true) String patientIdentifierSystem,
			@RequestParam(value = "organisation.identifier.value", required = true) String organisationIdentifierValue,
			@RequestParam(value = "code.code") String codeCode, @RequestParam(value = "code.system") String codeSystem) {

		log.debug("getObservations: " + patientIdentifierValue + " " + patientIdentifierSystem + " " + organisationIdentifierValue + " " + codeCode + " "
				+ codeSystem);
		List<Resource> resources = new ArrayList<>();

		Resource resource = new Resource();
		resource.setResourceId("37804230234");
		resource.setResourceIdOid("http://www.politsei.ee/");
		resource.setType(new Type("3141-9", "http://loinc.org"));

		resources.add(resource);

		return resources;
	}
}
