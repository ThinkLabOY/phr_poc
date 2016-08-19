package org.ech.phr.service;

import java.io.IOException;

import org.ech.phr.model.Person;

public interface PersonService {

	public Person findPerson(String id, String idOid, String organisationHashId) throws IOException;	
	public Person findOrInsertPerson(String id, String idOid, String organisationHashId) throws IOException;
	
}
