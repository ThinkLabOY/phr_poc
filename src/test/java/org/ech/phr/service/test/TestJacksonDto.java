package org.ech.phr.service.test;

import lombok.extern.slf4j.Slf4j;
import org.ech.phr.model.Person;
import org.ech.phr.model.exception.BusinessException;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class TestJacksonDto {


    @Test
	public void testToAndFromJson() {
    	Person person = TestData.getPersonOne();
    	byte[] personAsBytes;
		try {
			personAsBytes = person.toValue();
	    	String personAsString = person.toString();
	    	Person personCopy = new Person();
	    	personCopy.fromValue(personAsBytes);
	    	String personCopyAsString = personCopy.toString();
	    	Assert.assertEquals(personAsString, personCopyAsString);
		} 
		catch (BusinessException e) {
			Assert.fail("BusinessException: " + e.getErrorCode());
		}
	}

}
