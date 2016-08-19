package org.ech.phr.service.test;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.ech.phr.model.Person;
import org.ech.phr.model.Resource;
import org.ech.phr.service.impl.PersonDataIndexImpl;

public class TestPhrDistIndex {
	
	final static Logger log = Logger.getLogger(TestPhrDistIndex.class);
	
	public TestPhrDistIndex() {
		createTestData();
		logPersonResources(TestData.getPersonTwo());
	}
	
	private void logPersonResources(Person person) {
		log.debug("Person resource list.");
		PersonDataIndexImpl phrDistributedIndex = new PersonDataIndexImpl();
		try {
			List<Resource> resourceList = phrDistributedIndex.getResourceReferences(person);
			log.debug(person);
			for (Resource resource : resourceList) {
				log.debug(resource);
			}
			log.debug("END.");
		} 
		catch (IOException e) {
			log.error("IOE", e);
		}
	}
	
	private void createTestData() {
		log.debug("Create test data.");
		PersonDataIndexImpl phrDistributedIndex = new PersonDataIndexImpl();
		try {
			phrDistributedIndex.putResourceReference(TestData.getPersonOne(), TestData.getResourceOne());
			phrDistributedIndex.putResourceReference(TestData.getPersonOne(), TestData.getResourceTwo());
			phrDistributedIndex.putResourceReference(TestData.getPersonTwo(), TestData.getResourceThree());
			phrDistributedIndex.putResourceReference(TestData.getPersonTwo(), TestData.getResourceFour());
			phrDistributedIndex.putResourceReference(TestData.getPersonTwo(), TestData.getResourceFive());
			log.debug("Success!");
		} 
		catch (IOException e) {
			log.error("IOE", e);
		}
	}
	
	public static void main(String[] args) {
		new TestPhrDistIndex();
	}
}
