package org.ech.phr.hbase.table.generic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.ech.phr.hbase.dto.ValueProvider;
import org.ech.phr.model.generic.JsonDto;
import org.ech.phr.util.RequestContext;
import org.ech.phr.util.SpringUtil;

public class HbaseRecord <V extends ValueProvider> {
	//V - Value class
	
	private HbaseTable table;
	private HbaseColumn<V> column;
	private byte[] columnQualifier;
	private byte[] rowId;
	private V value;
	private List<V> values;

	public HbaseRecord(HbaseTable table, HbaseColumn<V> column, byte[] columnQualifier, byte[] rowId, V value) {
		super();
		this.table = table;
		this.column = column;
		this.columnQualifier = columnQualifier;
		this.rowId = rowId;
		this.value = value;
	}
	
	public HbaseRecord(HbaseTable table, HbaseColumn<V> column, byte[] columnQualifier, byte[] rowId, List<V> values) {
		super();
		this.table = table;
		this.column = column;
		this.columnQualifier = columnQualifier;
		this.rowId = rowId;
		this.values = values;
	}

	public HbaseRecord(HbaseTable table, HbaseColumn<V> column, byte[] columnQualifier, byte[] rowId) {
		super();
		this.table = table;
		this.column = column;
		this.columnQualifier = columnQualifier;
		this.rowId = rowId;
	}
	
	public HbaseRecord(HbaseTable table, HbaseColumn<V> column, byte[] rowId) {
		super();
		this.table = table;
		this.column = column;
		this.rowId = rowId;
	}

	public HbaseTable getTable() {
		return table;
	}
	public void setTable(HbaseTable table) {
		this.table = table;
	}
	public HbaseColumn<V> getColumn() {
		return column;
	}
	public void setColumn(HbaseColumn<V> column) {
		this.column = column;
	}
	public byte[] getColumnQualifier() {
		return columnQualifier;
	}
	public void setColumnQualifier(byte[] columnQualifier) {
		this.columnQualifier = columnQualifier;
	}
	public byte[] getRowId() {
		return rowId;
	}
	public void setRowId(byte[] rowId) {
		this.rowId = rowId;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	public List<V> getValues() {
		return values;
	}
	public void setValues(List<V> values) {
		this.values = values;
	}
	
	private RequestContext getRequestContext() {
		return SpringUtil.getBean(RequestContext.class);
	}

	public HbaseRecord<V> put() throws IOException {
		Connection connection = getRequestContext().getConnection();
		Table tableHbase = null;
		try {
			tableHbase = connection.getTable(this.getTable().getTableNameHbase());
			
			Put put = new Put(getRowId());
			put.add(this.getColumn().getColumnNameValue(), getColumnQualifier(), getValue().toValue());
			tableHbase.put(put);
		}
		finally {
			if (tableHbase != null) { 
				tableHbase.close();
			}
		}
		return this;
	}

	public HbaseRecord<V> get() throws IOException {
		Connection connection = getRequestContext().getConnection();
		Table tableHbase = null;
		try {
			tableHbase = connection.getTable(this.getTable().getTableNameHbase());
			Get get = new Get(getRowId());
			if (getColumnQualifier() != null) {
				get.addColumn(getColumn().getColumnNameValue(), getColumnQualifier());
			}
			else {
				get.addFamily(getColumn().getColumnNameValue());
				
			}
			Result result = tableHbase.get(get);
			List<Cell> resultCells = result.listCells();
			if (resultCells != null) {
				Class<V> valueClass = getColumn().getType();
				values = new LinkedList<V>();
				for (Cell cell : resultCells) {
					byte[] columnQualifierBytes = CellUtil.cloneQualifier(cell);
					byte[] valueBytes = CellUtil.cloneValue(cell);
					V valueObject = JsonDto.create(valueBytes, valueClass);
					if (columnQualifierBytes != null && columnQualifierBytes.length > 0) {
						valueObject.setColumnQualifierBytes(columnQualifierBytes);
					}
					values.add(valueObject);
					if (this.value == null) {
						this.value = valueObject;
					}
				}
			}
		}
		finally {
			if (tableHbase != null) { 
				tableHbase.close();
			}
		}
		return this;
	}
}
