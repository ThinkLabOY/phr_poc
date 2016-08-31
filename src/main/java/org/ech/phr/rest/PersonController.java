package org.ech.phr.rest;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.util.FhirUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.ech.phr.model.Person;
import org.ech.phr.service.PersonService;

@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/findPerson/{id}", method = RequestMethod.GET)
	public Person findPerson(@PathVariable String id) {

		Person person = null;
		try {
			person = personService.findPerson(id, "http://www.politsei.ee/", "ORG1", FhirUtil.OID_PHR);
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
		return person;
	}

	@RequestMapping(value = "/savePerson", method = RequestMethod.POST)
	public Person savePerson(@RequestBody Person person) {

		try {
			person = personService.findOrInsertPerson(person.getPersonId(),
					person.getPersonIdOid(), "ORG1", FhirUtil.OID_PHR);
		}
		catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}

		return person;
	}
}
