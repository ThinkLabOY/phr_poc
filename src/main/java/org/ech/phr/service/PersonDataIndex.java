package org.ech.phr.service;

import java.io.IOException;
import java.util.List;

import org.ech.phr.model.Organisation;
import org.ech.phr.model.Person;
import org.ech.phr.model.Resource;
import org.ech.phr.model.Type;

public interface PersonDataIndex {

	public void putResourceReference(Person person, Resource resource) throws IOException;
	public List<Resource> getResourceReferences(Person person) throws IOException;
	public void deleteResourceReference(Person person, Organisation dataProvider, Type type) throws IOException;

}
