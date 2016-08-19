package org.ech.phr.hbase.table.generic;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.ech.phr.hbase.dto.ValueProvider;
import org.ech.phr.util.StringUtil;

public class HbaseColumn <T extends ValueProvider> {
	//T - value class
	final static Logger log = Logger.getLogger(HbaseColumn.class);
	
	private String columnName;
	private byte[] columnNameValue;
    private final Class<T> type;

	public HbaseColumn(String columnName, Class<T> type) {
		super();
        this.type = type;
		this.columnName = columnName;
		if (columnName != null) {
			try {
				this.columnNameValue = columnName.getBytes(StringUtil.ENCODING_UTF_8);
			} 
			catch (UnsupportedEncodingException e) {
				log.error("HbaseColumn UnsupportedEncodingException", e);
			} 
		}
		else {
			this.columnNameValue = null;
		}
	}

	public String getColumnName() {
		return columnName;
	}

	public byte[] getColumnNameValue() {
		return columnNameValue;
	}

	public Class<T> getType() {
		return type;
	}
	
	

}
