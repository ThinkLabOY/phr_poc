package org.ech.phr.service;

import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Organisation;

public interface OrganisationService {

	public Organisation registerOrganisation(String organisationId, String organisationIdOid, String url) throws BusinessException;
	public Organisation getOrganisationById(String organisationId, String organisationIdOid) throws BusinessException;
	public Organisation getOrganisationById(String fullId) throws BusinessException;

}
