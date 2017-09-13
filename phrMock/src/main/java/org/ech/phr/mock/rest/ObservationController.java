package org.ech.phr.mock.rest;

import java.util.ArrayList;
import java.util.List;

import org.ech.phr.model.fhir.Code;
import org.ech.phr.model.fhir.Coding;
import org.ech.phr.model.fhir.Observation;
import org.ech.phr.model.fhir.Reference;
import org.ech.phr.model.fhir.ValueQuantity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/fhir/Observation")
public class ObservationController {

	@RequestMapping(method = RequestMethod.GET)
	public List<Observation> getObservations(@RequestParam(value = "patient.identifier.value", required = true) String patientIdentifierValue,
			@RequestParam(value = "patient.identifier.system", required = true) String patientIdentifierSystem,
			@RequestParam(value = "organisation.identifier.value", required = true) String organisationIdentifierValue,
			@RequestParam(value = "code.code") String codeCode, @RequestParam(value = "code.system") String codeSystem) {

		log.debug("getObservations: " + patientIdentifierValue + " " + patientIdentifierSystem + " " + organisationIdentifierValue + " " + codeCode + " "
				+ codeSystem);
		List<Observation> observations = generateRandomObservations(patientIdentifierValue, patientIdentifierSystem, organisationIdentifierValue, codeCode, codeSystem, 3);
		return observations;
	}

	private List<Observation> generateRandomObservations(String patientIdentifierValue, String patientIdentifierSystem, String organisationIdentifierValue, 
			String codeCode, String codeSystem, int nrOfElements) {
		List<Observation> results = new ArrayList<>();
		for (int i = 0; i < nrOfElements; i++) {
			Coding coding = Coding.builder()
					.system(codeSystem)
					.code(codeCode)
					.display(codeCode + " displayname")
					.build();
			Code code = Code.builder()
					.coding(ImmutableList.of(coding))
					.build();
			Reference subject = Reference.builder()
					.reference(patientIdentifierSystem + "/Patient/" + patientIdentifierValue)
					.build();
			ValueQuantity valueQuantity = ValueQuantity.builder()
					.value(182)
					.system("http://unitsofmeasure.org")
					.unit("cm")
					.code("cm")
					.build();
			Observation observation = Observation.builder()
					.resourceType("Observation")
					.code(code)
					.effectiveDateTime("2017-03-28")
					.subject(subject)
					.valueQuantity(valueQuantity)
					.build();
			results.add(observation);
		}
		return results;
	}

}
