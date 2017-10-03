package org.ech.phr.hbase.table.generic;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.ech.phr.hbase.dto.ValueProvider;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.generic.JsonDto;
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

	public HbaseRecord<V> put() throws BusinessException {
		Connection connection = getRequestContext().getConnection();
		Table tableHbase = null;
		try {
			tableHbase = connection.getTable(this.getTable().getTableNameHbase());
			
			Put put = new Put(getRowId());
			put.add(this.getColumn().getColumnNameValue(), getColumnQualifier(), getValue().toValue());
			tableHbase.put(put);
		}
		catch (IOException ioe) {
			BusinessException.throwBusinessException(BusinessException.EX_HBS_001, ioe);
		}
		finally {
			if (tableHbase != null) { 
				try {
					tableHbase.close();
				}
				catch (IOException ioe) {
					BusinessException.throwBusinessException(BusinessException.EX_HBS_001, ioe);
				}
			}
		}
		return this;
	}

	public HbaseRecord<V> get() throws BusinessException {
		Connection connection = getRequestContext().getConnection();
		Table tableHbase = null;
		try {
			tableHbase = connection.getTable(this.getTable().getTableNameHbase());
			Class<V> valueClass = getColumn().getType();
			if (getRowId() != null) {
				Get get = new Get(getRowId());
				if (getColumnQualifier() != null) {
					get.addColumn(getColumn().getColumnNameValue(), getColumnQualifier());
				}
				else {
					get.addFamily(getColumn().getColumnNameValue());
				}
				Result result = tableHbase.get(get);
				mapResultsToValueObjects(valueClass, result);
			}
			else {
				ResultScanner resultScanner = tableHbase.getScanner(getColumn().getColumnNameValue());
				Iterator<Result> resultList = resultScanner.iterator();
				while (resultList.hasNext()) {
					mapResultsToValueObjects(valueClass, resultList.next());
				}
			}
		}
		catch (IOException ioe) {
			BusinessException.throwBusinessException(BusinessException.EX_HBS_001, ioe);
		}
		finally {
			if (tableHbase != null) { 
				try {
					tableHbase.close();
				}
				catch (IOException ioe) {
					BusinessException.throwBusinessException(BusinessException.EX_HBS_001, ioe);
				}
			}
		}
		return this;
	}

	private void mapResultsToValueObjects(Class<V> valueClass, Result result) throws BusinessException {
		List<Cell> resultCells = result.listCells();
		if (resultCells != null) {
			for (Cell cell : resultCells) {
				V valueObject = mapCellToValueObject(valueClass, cell);
				addToValuesList(valueObject);
				setAsValue(valueObject);
			}
		}
	}

	private void setAsValue(V valueObject) {
		if (this.value == null) {
			this.value = valueObject;
		}
	}

	private void addToValuesList(V valueObject) {
		if (values == null) {
			values = new LinkedList<V>();
		}
		values.add(valueObject);
	}
	
	private V mapCellToValueObject(Class<V> valueClass, Cell cell) throws BusinessException {
		byte[] columnQualifierBytes = CellUtil.cloneQualifier(cell);
		byte[] valueBytes = CellUtil.cloneValue(cell);
		V valueObject = JsonDto.create(valueBytes, valueClass);
		if (columnQualifierBytes != null && columnQualifierBytes.length > 0) {
			valueObject.setColumnQualifierBytes(columnQualifierBytes);
		}
		return valueObject;
	}
}
