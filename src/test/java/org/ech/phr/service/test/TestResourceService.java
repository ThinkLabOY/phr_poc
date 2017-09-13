package org.ech.phr.service.test;

import static org.ech.phr.model.fhir.ResourceTypeEnum.PATIENT;

import java.util.List;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.fhir.ResourceTypeEnum;
import org.ech.phr.model.hbase.Resource;
import org.ech.phr.service.ResourceService;
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
public class TestResourceService {

	@Autowired
	ResourceService resourceService;

    @Test
	public void testFindOrInsertPerson() {
    	try {
			Resource resource = resourceService.putResourceReference("37966661234", "http://www.politsei.ee/", "ORG1", FhirUtil.OID_PHR, PATIENT.getText(), FhirUtil.OID_PHR, "37966661234", "http://www.politsei.ee/");
			Assert.assertNotNull("Person should not be null!", resource);
			List<Resource> resources = resourceService.getResourceReferences("37966661234", "http://www.politsei.ee/", "ORG1", FhirUtil.OID_PHR, PATIENT.getText(), FhirUtil.OID_PHR);
			Resource resourceCopy = resources.get(0);
			String resourceAsString = resource.toString();
			String resourceCopyAsString = resourceCopy.toString();
			Assert.assertEquals(resourceAsString, resourceCopyAsString);
		} 
		catch (BusinessException e) {
			Assert.fail("BusinessException: " + e.getErrorCode());
		}
    	
    }


}
