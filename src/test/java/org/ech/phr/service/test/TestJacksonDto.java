package org.ech.phr.service.test;

import org.apache.log4j.Logger;
import org.ech.phr.model.Person;
import org.junit.Assert;
import org.junit.Test;



public class TestJacksonDto {

	final static Logger log = Logger.getLogger(TestJacksonDto.class);

	
    @Test
	public void testToAndFromJson() {
    	Person person = TestData.getPersonOne();
    	byte[] personAsBytes = person.toValue();
    	String personAsString = person.toString();
    	Person personCopy = new Person();
    	personCopy.fromValue(personAsBytes);
    	String personCopyAsString = personCopy.toString();
    	Assert.assertEquals(personAsString, personCopyAsString);
	}

}
