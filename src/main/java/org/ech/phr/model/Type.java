package org.ech.phr.model;

import org.ech.phr.model.generic.JsonDto;

public class Type extends JsonDto {
	
	private String typeCode;
	private String typeCodeOid;
	
	public Type() {
		super();
	}

	public Type(String typeCode, String typeCodeOid) {
		super();
		this.typeCode = typeCode;
		this.typeCodeOid = typeCodeOid;
	}

	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeCodeOid() {
		return typeCodeOid;
	}
	public void setTypeCodeOid(String typeCodeOid) {
		this.typeCodeOid = typeCodeOid;
	}

}
