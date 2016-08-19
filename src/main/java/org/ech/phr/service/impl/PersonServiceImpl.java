package org.ech.phr.service.impl;

import java.io.IOException;

import org.ech.phr.hbase.table.PersonTable;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.model.Person;
import org.ech.phr.service.PersonService;
import org.ech.phr.util.FhirUtil;
import org.ech.phr.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class PersonServiceImpl implements PersonService {

	public Person findPerson(String id, String idOid, String organisationHashId) throws IOException {
		String fullId = FhirUtil.composeId(id, idOid, FhirUtil.TYPE_PATIENT);
		HbaseRecord<Person> personRecord = PersonTable.createIdRecord(organisationHashId, fullId);
		Person result = personRecord.get().getValue();
		return result;
	}

	public Person findOrInsertPerson(String id, String idOid, String organisationHashId) throws IOException {
		String fullId = FhirUtil.composeId(id, idOid, FhirUtil.TYPE_PATIENT);
		Person person = findPerson(id, idOid, organisationHashId);
		if (person == null) {
			person = new Person(id, idOid);
			String phrId = StringUtil.getHash(fullId+organisationHashId);
			person.setPhrId(phrId);
			person.setPhrIdOid(FhirUtil.OID_PHR);
			person.setMasterId(phrId);
			person.setMasterIdOid(FhirUtil.OID_MASTER);
			HbaseRecord<Person> personRecord = PersonTable.createIdRecord(organisationHashId, fullId, person);
			person = personRecord.put().getValue();
		}
		return person;
	}

}
