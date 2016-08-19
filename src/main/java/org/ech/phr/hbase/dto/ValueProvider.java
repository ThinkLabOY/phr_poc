package org.ech.phr.hbase.dto;

import org.apache.hadoop.hbase.util.Bytes;

public abstract class ValueProvider {
	
	private byte[] columnQualifierBytes;
	private String columnQualifier;
	
	public abstract byte[] toValue();
	public abstract void fromValue(byte[] rowId);
	
	
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
