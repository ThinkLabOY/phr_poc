package org.ech.phr.model;

import org.ech.phr.model.generic.JsonDto;

public class Resource extends JsonDto {
	
	private Organisation organisation;
	private Type type;
	
	private String resourceId;
	private String resourceIdOid;

	public Resource() {
		super();
	}	

	public Resource(Organisation organisation, Type type, String resourceId, String resourceIdOid) {
		super();
		this.organisation = organisation;
		this.type = type;
		this.resourceId = resourceId;
		this.resourceIdOid = resourceIdOid;
	}

	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceIdOid() {
		return resourceIdOid;
	}
	public void setResourceIdOid(String resourceIdOid) {
		this.resourceIdOid = resourceIdOid;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}


}
