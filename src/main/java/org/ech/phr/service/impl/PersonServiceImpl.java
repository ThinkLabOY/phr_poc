package org.ech.phr.service.impl;

import static org.ech.phr.model.fhir.ResourceTypeEnum.ORGANIZATION;
import static org.ech.phr.model.fhir.ResourceTypeEnum.PATIENT;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.ech.phr.hbase.table.PersonTable;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Organisation;
import org.ech.phr.model.hbase.Person;
import org.ech.phr.service.OrganisationService;
import org.ech.phr.service.PersonService;
import org.ech.phr.util.FhirUtil;
import org.ech.phr.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonServiceImpl implements PersonService {

	@Autowired
	OrganisationService organisationService;

	public Person findPerson(String id, String idOid, String organisationId, String organisationIdOid) throws BusinessException {
		return findOrInsertPersonInternal(id, idOid, organisationId, organisationIdOid, false);
	}

	public Person findOrInsertPerson(String id, String idOid, String organisationId, String organisationIdOid) throws BusinessException {
		return findOrInsertPersonInternal(id, idOid, organisationId, organisationIdOid, true);
	}

	private Person findOrInsertPersonInternal(String id, String idOid, String organisationId, String organisationIdOid, boolean createNewIfNotFound) throws BusinessException {
		Person person = null;
		Organisation organisation = organisationService.getOrganisationById(organisationId, organisationIdOid);
		if (organisation != null) {
			String organisationPhrIdFull = FhirUtil.composeId(organisation.getPhrId(), organisation.getPhrIdOid(), ORGANIZATION.getText());
			String personIdFull = FhirUtil.composeId(id, idOid, PATIENT.getText());
			HbaseRecord<Person> findPersonRecord = PersonTable.createIdRecord(organisationPhrIdFull, personIdFull);
			person = findPersonRecord.get().getValue();
			if (person == null && createNewIfNotFound) {
				String phrId = StringUtil.getHash(personIdFull+organisationPhrIdFull);
				person = Person.builder()
						.personId(id)
						.personIdOid(idOid)
						.phrId(phrId)
						.phrIdOid(FhirUtil.OID_PHR_INTERNAL)
						.masterId(phrId)
						.masterIdOid(FhirUtil.OID_MASTER)
						.organisation(organisation)
						.build();
				HbaseRecord<Person> personRecord = PersonTable.createIdRecord(organisationPhrIdFull, personIdFull, person);
				String personIdPhrFull = FhirUtil.composeId(person.getPhrId(), person.getPhrIdOid(), PATIENT.getText());
				HbaseRecord<Person> personHashRecord = PersonTable.createIdRecord(organisationPhrIdFull, personIdPhrFull, person);
				personHashRecord.put();
				String personMasterIdPhrFull = FhirUtil.composeId(person.getMasterId(), person.getMasterIdOid(), PATIENT.getText());
				HbaseRecord<Person> personMasterRecord = PersonTable.createIdRecord(organisationPhrIdFull, personMasterIdPhrFull, person);
				personMasterRecord.put();
				person = personRecord.put().getValue();
			}
		}
		else {
			BusinessException.throwBusinessException(BusinessException.EX_USR_001);
		}
		return person;
	}

	@Override
	public List<Person> findPerson(String id, String idOid) throws BusinessException {
		String personIdFull = FhirUtil.composeId(id, idOid, PATIENT.getText());
		HbaseRecord<Person> findPersonRecord = PersonTable.createIdRecord(null, personIdFull);
		List<Person> personList = findPersonRecord.get().getValues();
		if (CollectionUtils.isNotEmpty(personList)) {
			for (Person person : personList) {
				String organisationFullId = person.getColumnQualifier();
				person.setOrganisation(organisationService.getOrganisationById(organisationFullId));
			}
		}
		return personList;
	}


}
