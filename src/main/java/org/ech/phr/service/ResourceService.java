package org.ech.phr.service;

import java.util.List;

import org.ech.phr.model.Resource;
import org.ech.phr.model.exception.BusinessException;

public interface ResourceService {

	public Resource putResourceReference(String personId, String personIdOid, String organisationId, String organisationIdOid, String typeCode, String typeCodeOid, String resourceId, String resourceIdOid) throws BusinessException;
	public List<Resource> getResourceReferences(String personId, String personIdOid, String organisationId, String organisationIdOid, String typeCode, String typeCodeOid) throws BusinessException;
	public void deleteResourceReference(String personId, String personIdOid, String typeCode, String typeCodeOid) throws BusinessException;

}
