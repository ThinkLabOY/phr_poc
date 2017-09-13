package org.ech.phr.service.test;

import lombok.extern.slf4j.Slf4j;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Person;
import org.ech.phr.service.PersonService;
import org.ech.phr.util.FhirUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test-config.xml"})
@WebAppConfiguration
public class TestPersonService {
	
	@Autowired
	PersonService personService;

    @Test
	public void testFindOrInsertPerson() {
    	try {
			Person person = personService.findOrInsertPerson("37966661234", "http://www.politsei.ee/", "ORG1", FhirUtil.OID_PHR);
			Assert.assertNotNull("Person should not be null!", person);
		} 
		catch (BusinessException e) {
			Assert.fail("BusinessException: " + e.getErrorCode());
		}
    	
    }

}
