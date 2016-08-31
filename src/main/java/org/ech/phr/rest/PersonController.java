package org.ech.phr.rest;

import java.io.IOException;

import org.ech.phr.model.Person;
import org.ech.phr.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {
	private static final Logger log = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/findPerson/{id}", method = RequestMethod.GET)
	public Person findPerson(@PathVariable String id) {

		Person person = null;
		try {
			person = personService.findPerson(id, "http://www.politsei.ee/", "Organization/ORG1");
		}
		catch (IOException e) {
			log.error("error: " + e.getMessage());
		}

		return person;
	}
}
