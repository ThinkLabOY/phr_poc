package org.ech.phr.service.test;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Organisation;
import org.ech.phr.service.OrganisationService;
import org.ech.phr.util.FhirUtil;
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
public class TestOrganisationService {

	@Autowired
	OrganisationService organisationService;

    @Test
	public void testFindOrInsertPerson() {
    	try {
    		//register organisation ORG1
			Organisation orgPhr1 = organisationService.registerOrganisation("ORG1", "https://org.ech.phr/", "https://phr1_url/");
			//check that organisation has internal Phr id
			String orgPhr1PhrId = orgPhr1.getPhrId();
			Assert.assertNotNull("Org1 phr id should not be null!", orgPhr1PhrId);
			//retrieve organisation data with phr id
			Organisation orgPhr1Copy = organisationService.getOrganisationById(orgPhr1PhrId, FhirUtil.OID_PHR_INTERNAL);
			//compare two records (use json representation)
			String orgPhr1AsString = orgPhr1.toString();
	    	String orgPhr1CopyAsString = orgPhr1Copy.toString();
	    	Assert.assertEquals(orgPhr1AsString, orgPhr1CopyAsString);
		} 
		catch (BusinessException e) {
			Assert.fail("BusinessException: " + e.getErrorCode());
		}
    	
    }

}
