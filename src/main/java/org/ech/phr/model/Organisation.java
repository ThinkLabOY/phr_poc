package org.ech.phr.model;

import org.ech.phr.model.generic.JsonDto;

public class Organisation extends JsonDto {
	
	private String id;
	private String idOid;
	private String phrId;
	private String phrIdOid;
	private String url;

	public Organisation() {
		super();
	}
	
	public Organisation(String id, String idOid, String phrId, String phrIdOid, String url) {
		super();
		this.id = id;
		this.idOid = idOid;
		this.phrId = phrId;
		this.phrIdOid = phrIdOid;
		this.url = url;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdOid() {
		return idOid;
	}
	public void setIdOid(String idOid) {
		this.idOid = idOid;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
