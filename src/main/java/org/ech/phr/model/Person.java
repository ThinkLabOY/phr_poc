package org.ech.phr.model;

import org.ech.phr.model.generic.JsonDto;

public class Person extends JsonDto {
	
	private String personId;
	private String personIdOid;
	private String phrId;
	private String phrIdOid;
	private String masterId;
	private String masterIdOid;
	
	public Person() {
		super();
	}

	public Person(String personId, String personIdOid) {
		super();
		this.personId = personId;
		this.personIdOid = personIdOid;
	}

	public Person(String personId, String personIdOid, String phrId, String phrIdOid, String masterId, String masterIdOid) {
		super();
		this.personId = personId;
		this.personIdOid = personIdOid;
		this.phrId = phrId;
		this.phrIdOid = phrIdOid;
		this.masterId = masterId;
		this.masterIdOid = masterIdOid;
	}

	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonIdOid() {
		return personIdOid;
	}
	public void setPersonIdOid(String personIdOid) {
		this.personIdOid = personIdOid;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getPhrId() {
		return phrId;
	}

	public void setPhrId(String phrId) {
		this.phrId = phrId;
	}

	public String getPhrIdOid() {
		return phrIdOid;
	}

	public void setPhrIdOid(String phrIdOid) {
		this.phrIdOid = phrIdOid;
	}

	public String getMasterIdOid() {
		return masterIdOid;
	}

	public void setMasterIdOid(String masterIdOid) {
		this.masterIdOid = masterIdOid;
	}

}
