package org.ech.phr.service;

import org.ech.phr.model.Organisation;
import org.ech.phr.model.exception.BusinessException;

public interface OrganisationService {

	public Organisation registerOrganisation(String organisationId, String organisationIdOid, String url) throws BusinessException;
	public Organisation getOrganisationById(String organisationId, String organisationIdOid) throws BusinessException;

}
