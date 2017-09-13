package org.ech.phr.rest;

import static org.ech.phr.rest.ParameterConstants.CODE_CODE;
import static org.ech.phr.rest.ParameterConstants.CODE_SYSTEM;
import static org.ech.phr.rest.ParameterConstants.ORGANISATION_IDENTIFIER_VALUE;
import static org.ech.phr.rest.ParameterConstants.PATIENT_IDENTIFIER_SYSTEM;
import static org.ech.phr.rest.ParameterConstants.PATIENT_IDENTIFIER_VALUE;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Organisation;
import org.ech.phr.model.hbase.Person;
import org.ech.phr.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/fhir/Observation")
public class ObservationController {


	@Autowired
	private PersonService personService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@ApiOperation("Query observations for certain patient with certain observation code and code system")
	@RequestMapping(method = RequestMethod.GET)
	public String getObservations(
			@RequestParam(value = PATIENT_IDENTIFIER_VALUE, required = true) String patientIdentifierValue,
			@RequestParam(value = PATIENT_IDENTIFIER_SYSTEM, required = true) String patientIdentifierSystem,
			@RequestParam(value = ORGANISATION_IDENTIFIER_VALUE, required = true) String organisationIdentifierValue,
			@RequestParam(value = CODE_CODE) String codeCode, 
			@RequestParam(value = CODE_SYSTEM) String codeSystem) throws BusinessException {

		log.info("getObservations: " + patientIdentifierValue + " " + patientIdentifierSystem + " " + organisationIdentifierValue + " " + codeCode + " "
				+ codeSystem);
		String result = null;

		List<Person> personList = personService.findPerson(patientIdentifierValue, patientIdentifierSystem);
		if (CollectionUtils.isNotEmpty(personList)) {
			ArrayNode resultArrayNode = objectMapper.createArrayNode();
			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
			queryParams.add(PATIENT_IDENTIFIER_VALUE, patientIdentifierValue);
			queryParams.add(PATIENT_IDENTIFIER_SYSTEM, patientIdentifierSystem);
			queryParams.add(ORGANISATION_IDENTIFIER_VALUE, organisationIdentifierValue);
			queryParams.add(CODE_CODE, codeCode);
			queryParams.add(CODE_SYSTEM, codeSystem);
			for (Person person : personList) {
				Organisation organisation = person.getOrganisation();
				List<JsonNode> subNodes = queryOtherPhrSystems(queryParams, organisation.getUrl());
				resultArrayNode.addAll(subNodes);
			}
			try {
				result = objectMapper.writeValueAsString(resultArrayNode);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private List<JsonNode> queryOtherPhrSystems(MultiValueMap<String, String> queryParams, String endpoint) {
		URI targetUrl = UriComponentsBuilder.fromUriString(endpoint)
				.path("/Observation")
				.queryParams(queryParams)
				.build()
				.toUri();
		log.info("Querying: \"" + targetUrl.toString() + "\".");
		ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.GET, null, String.class);
		List<JsonNode> subNodes = null;
		try {
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			subNodes = Lists.newArrayList(jsonNode.elements());
		} catch (IOException e) {
			log.warn("IOExcpetion accessing: \"" + targetUrl.toString() + "\": " + e.getMessage());
		}
		return subNodes;
	}

}
