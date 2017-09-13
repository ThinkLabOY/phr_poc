package org.ech.phr.service.impl;

import static org.ech.phr.model.fhir.ResourceTypeEnum.ORGANIZATION;

import org.ech.phr.hbase.table.OrganisationTable;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.fhir.ResourceTypeEnum;
import org.ech.phr.model.hbase.Organisation;
import org.ech.phr.service.OrganisationService;
import org.ech.phr.util.FhirUtil;
import org.ech.phr.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrganisationServiceImpl implements OrganisationService {

	public Organisation registerOrganisation(String organisationId, String organisationIdOid, String url) throws BusinessException {
		String fullId = FhirUtil.composeId(organisationId, organisationIdOid, ORGANIZATION.getText());
		//check whether organisation with this id allready exists
		Organisation organisation = getOrganisationById(organisationId, organisationIdOid);
		if (organisation == null) {
			//create new organisation record
			String phrId = StringUtil.getHash(fullId);
			String phrIdFull = FhirUtil.composeId(phrId, FhirUtil.OID_PHR_INTERNAL, ORGANIZATION.getText());
			organisation = new Organisation(organisationId, organisationIdOid, phrId, FhirUtil.OID_PHR_INTERNAL, url);
			HbaseRecord<Organisation> organisationRecord = OrganisationTable.createIdRecord(null, fullId, organisation);
			HbaseRecord<Organisation> organisationHashRecord = OrganisationTable.createIdRecord(null, phrIdFull, organisation);
			organisationHashRecord.put();
			organisation = organisationRecord.put().getValue();
		}
		else {
			//update record data if necessary
			if (url != null && !url.equals(organisation.getUrl())) {
				organisation.setUrl(url);
				String phrIdFull = FhirUtil.composeId(organisation.getPhrId(), organisation.getPhrIdOid(), ORGANIZATION.getText());
				HbaseRecord<Organisation> organisationRecord = OrganisationTable.createIdRecord(null, fullId, organisation);
				HbaseRecord<Organisation> organisationHashRecord = OrganisationTable.createIdRecord(null, phrIdFull, organisation);
				organisationHashRecord.put();
				organisation = organisationRecord.put().getValue();
			}
		}
		return organisation;
	}

	public Organisation getOrganisationById(String organisationId, String organisationIdOid) throws BusinessException {
		String fullId = FhirUtil.composeId(organisationId, organisationIdOid, ORGANIZATION.getText());
		return getOrganisationById(fullId);
	}

	public Organisation getOrganisationById(String fullId) throws BusinessException {
		HbaseRecord<Organisation> personRecord = OrganisationTable.createIdRecord(null, fullId);
		Organisation result = personRecord.get().getValue();
		return result;
	}


}
