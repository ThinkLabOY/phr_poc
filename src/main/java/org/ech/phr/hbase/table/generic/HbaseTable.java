package org.ech.phr.hbase.table.generic;

import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.ech.phr.hbase.dto.ValueProvider;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.util.StringUtil;

public class HbaseTable {
	
	private String tableName;
	private TableName tableNameHbase;

	public HbaseTable(String tableName) {
		super();
		this.tableName = tableName;
		if (tableName != null) {
			this.setTableNameHbase(TableName.valueOf(tableName));
		}
	}

	public static <V extends ValueProvider> HbaseColumn<V> createColumn(String columnName, Class<V> valueClass) {
		HbaseColumn<V> column = new HbaseColumn<V>(columnName, valueClass);
		return column;
	}

	public <V extends ValueProvider> HbaseRecord<V> createRecord(HbaseColumn<V> column, String rowId) throws BusinessException {
		byte[] rowIdBytes =  StringUtil.getBytes(rowId);
		HbaseRecord<V> record = new HbaseRecord<V>(this, column, rowIdBytes);
		return record;
	}
	
	public <V extends ValueProvider> HbaseRecord<V> createRecord(HbaseColumn<V> column, String columnQualifier, String rowId) throws BusinessException {
		byte[] columnQualifierBytes =  null;
		if (columnQualifier != null && columnQualifier.length() > 0) {
			columnQualifierBytes =  StringUtil.getBytes(columnQualifier);
		}
		byte[] rowIdBytes =  StringUtil.getBytes(rowId);
		HbaseRecord<V> record = new HbaseRecord<V>(this, column, columnQualifierBytes, rowIdBytes);
		return record;
	}
	
	public <V extends ValueProvider> HbaseRecord<V> createRecord(HbaseColumn<V> column, String columnQualifier, String rowId, V value) throws BusinessException {
		byte[] columnQualifierBytes =  null;
		if (columnQualifier != null && columnQualifier.length() > 0) {
			columnQualifierBytes =  StringUtil.getBytes(columnQualifier);
		}
		byte[] rowIdBytes =  StringUtil.getBytes(rowId);
		HbaseRecord<V> record = new HbaseRecord<V>(this, column, columnQualifierBytes, rowIdBytes, value);
		return record;
	}
	
	public <V extends ValueProvider> HbaseRecord<V> createRecord(HbaseColumn<V> column, String columnQualifier, String rowId, List<V> values) throws BusinessException {
		byte[] columnQualifierBytes =  null;
		if (columnQualifier != null && columnQualifier.length() > 0) {
			columnQualifierBytes =  StringUtil.getBytes(columnQualifier);
		}
		byte[] rowIdBytes =  StringUtil.getBytes(rowId);
		HbaseRecord<V> record = new HbaseRecord<V>(this, column, columnQualifierBytes, rowIdBytes, values);
		return record;
	}
		
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		if (tableName != null) {
			this.setTableNameHbase(TableName.valueOf(tableName));
		}
	}

	public TableName getTableNameHbase() {
		return tableNameHbase;
	}

	public void setTableNameHbase(TableName tableNameHbase) {
		this.tableNameHbase = tableNameHbase;
	}

}
