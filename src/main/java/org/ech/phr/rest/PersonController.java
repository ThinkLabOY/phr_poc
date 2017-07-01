package org.ech.phr.rest;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.util.FhirUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.ech.phr.model.Person;
import org.ech.phr.service.PersonService;

@Slf4j
@RestController
@RequestMapping("/fhir/Person")
public class PersonController {

	@Autowired
	private PersonService personService;
		
	@RequestMapping(method = RequestMethod.GET)
	public Person findPerson(@RequestParam("patient.identifier.value") String id, @RequestParam("patient.identifier.system") String oid, @RequestParam("organisation.identifier.value") String organisationId) {

		Person person = null;
		try {
			person = personService.findPerson(id, oid, organisationId, FhirUtil.OID_PHR);
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
		return person;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Person savePerson(@RequestBody Person person) {

		try {
			person = personService.findOrInsertPerson(person.getPersonId(),
					person.getPersonIdOid(), person.getOrganisation().getId(), FhirUtil.OID_PHR);
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}

		return person;
	}
}
