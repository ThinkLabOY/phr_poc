package org.ech.phr.service;

import java.util.List;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Person;

public interface PersonService {

	public List<Person> findPerson(String id, String idOid) throws BusinessException;
	public Person findPerson(String id, String idOid, String organisationId, String organisationIdOid) throws BusinessException;
	public Person findOrInsertPerson(String id, String idOid, String organisationId, String organisationIdOid) throws BusinessException;
	
}
