package org.ech.phr.hbase.dto;

import org.apache.hadoop.hbase.util.Bytes;
import org.ech.phr.model.exception.BusinessException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ValueProvider {
	
	
	@JsonIgnore 
	private byte[] columnQualifierBytes;
	@JsonIgnore 
	private String columnQualifier;
	
	public abstract byte[] toValue() throws BusinessException ;
	public abstract void fromValue(byte[] rowId) throws BusinessException ;
	
	
	public byte[] getColumnQualifierBytes() {
		return columnQualifierBytes;
	}
	
	public void setColumnQualifierBytes(byte[] columnQualifierBytes) {
		this.columnQualifierBytes = columnQualifierBytes;
		if (columnQualifierBytes != null) {
			this.setColumnQualifier(Bytes.toString(columnQualifierBytes));
		}
		else {
			this.setColumnQualifier(null);
		}
		
	}
	
	public String getColumnQualifier() {
		return columnQualifier;
	}
	public void setColumnQualifier(String columnQualifier) {
		this.columnQualifier = columnQualifier;
	}
	
}
