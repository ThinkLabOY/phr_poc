package org.ech.phr.service.impl;

import java.util.List;

import org.ech.phr.hbase.table.ResourceTable;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.model.Person;
import org.ech.phr.model.Resource;
import org.ech.phr.model.Type;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.service.OrganisationService;
import org.ech.phr.service.PersonService;
import org.ech.phr.service.ResourceService;
import org.ech.phr.util.FhirUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	PersonService personService;
	@Autowired
	OrganisationService organisationService;

	public Resource putResourceReference(String personId, String personIdOid, String organisationId, String organisationIdOid, String typeCode, String typeCodeOid, String resourceId, String resourceIdOid) throws BusinessException {
		Resource resource = null;
		Person person = personService.findOrInsertPerson(personId, personIdOid, organisationId, organisationIdOid);
		if (person != null) {
			String personPhrIdFull = FhirUtil.composeId(person.getPhrId(), person.getPhrIdOid(), FhirUtil.TYPE_PATIENT);
			Type type = new Type(typeCode, typeCodeOid);
			String typeIdFull = FhirUtil.composeId(typeCode, typeCodeOid, FhirUtil.TYPE_TYPE);
		    resource = new Resource(type, resourceId, resourceIdOid);
			HbaseRecord<Resource> resourceRecord = ResourceTable.createIdRecord(typeIdFull, personPhrIdFull, resource);
			resource = resourceRecord.put().getValue();
		}
		return resource;
	}

	public List<Resource> getResourceReferences(String personId, String personIdOid, String organisationId, String organisationIdOid, String typeCode, String typeCodeOid) throws BusinessException {
		List<Resource> result = null;
		Person person = personService.findPerson(personId, personIdOid, organisationId, organisationIdOid);
		if (person != null) {
			String personPhrIdFull = FhirUtil.composeId(person.getPhrId(), person.getPhrIdOid(), FhirUtil.TYPE_PATIENT);
			String typeIdFull = FhirUtil.composeId(typeCode, typeCodeOid, FhirUtil.TYPE_TYPE);
			HbaseRecord<Resource> resourceRecord = ResourceTable.createIdRecord(typeIdFull, personPhrIdFull);
			result = resourceRecord.get().getValues();
		}
		return result;
	}

	public void deleteResourceReference(String personId, String personIdOid, String typeCode, String typeCodeOid) throws BusinessException {
		// TODO Auto-generated method stub
	}

}
