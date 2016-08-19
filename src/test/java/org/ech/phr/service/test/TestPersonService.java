package org.ech.phr.service.test;

import java.io.IOException;

import org.ech.phr.model.Person;
import org.ech.phr.service.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test-config.xml"})
@WebAppConfiguration
public class TestPersonService {
	
	@Autowired
	PersonService personService;

    @Test
	public void testFindOrInsertPerson() {
    	try {
			Person person = personService.findOrInsertPerson("37966661234", "http://www.politsei.ee/", "Organization/ORG1");
			Assert.assertNotNull("Person should not be null!", person);
		} 
    	catch (IOException e) {
    		Assert.fail("testToAndFromJson IOException: " + e.getMessage());
		}
    	
    }

}
