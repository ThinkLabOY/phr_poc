package org.ech.phr.service;

import org.ech.phr.model.Person;
import org.ech.phr.model.exception.BusinessException;

public interface PersonService {

	public Person findPerson(String id, String idOid, String organisationId, String organisationIdOid) throws BusinessException;
	public Person findOrInsertPerson(String id, String idOid, String organisationId, String organisationIdOid) throws BusinessException;
	
}
