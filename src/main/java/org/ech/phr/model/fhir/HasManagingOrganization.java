package org.ech.phr.model.fhir;

public interface HasManagingOrganization {
	
	public Reference getManagingOrganization();
	public void setManagingOrganization(Reference managingOrganization);

}
