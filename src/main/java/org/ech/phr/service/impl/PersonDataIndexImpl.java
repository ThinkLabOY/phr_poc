package org.ech.phr.service.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.ech.phr.model.Organisation;
import org.ech.phr.model.Person;
import org.ech.phr.model.Resource;
import org.ech.phr.model.Type;
import org.ech.phr.service.PersonDataIndex;
import org.ech.phr.util.HbaseUtil;

public class PersonDataIndexImpl implements PersonDataIndex {
	
	final static Logger log = Logger.getLogger(PersonDataIndexImpl.class);

	private static final TableName TABLE_NAME_PERSON_DATA_INDEX = TableName.valueOf("phr:person_data_index");
	private static final byte[] REFERENCE = Bytes.toBytes("r");
	private static final String DELIMITER = "+";
	
	public void putResourceReference(Person person, Resource resource) throws IOException {
		Connection connection = null;
		Table table = null;
		try {
			connection = HbaseUtil.getHbaseConnection();
			table = getTablePersonDataIndex(connection);
			
			byte[] rowIdBytes = getRowId(person);
			byte[] columnQualifier = getColumnQualifier(null, resource.getType());
			byte[] value = getValue(resource);
			
			Put p = new Put(rowIdBytes);
			p.add(REFERENCE, columnQualifier, value);
			table.put(p);
		}
		finally {
			if (table != null) { 
				table.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public List<Resource> getResourceReferences(Person person) throws IOException {
		List<Resource> resourceList = null;
		Connection connection = null;
		Table table = null;
		try {
			connection = HbaseUtil.getHbaseConnection();
			table = getTablePersonDataIndex(connection);
			
			byte[] rowIdBytes = getRowId(person);
			
			Get get = new Get(rowIdBytes);
			get.addFamily(REFERENCE);
			Result result = table.get(get);
			
			List<Cell> resultCells = result.listCells();
			if (resultCells != null) {
				resourceList = new LinkedList<Resource>();
				for (Cell cell : resultCells) {
					byte[] columnQualifierBytes = CellUtil.cloneQualifier(cell);
					Organisation dataProvider = getDataProvider(columnQualifierBytes);
					Type type = getType(columnQualifierBytes);
					byte[] valueBytes = CellUtil.cloneValue(cell);
					Resource resource = getResource(valueBytes);
					resource.setOrganisation(dataProvider);
					resource.setType(type);
					resourceList.add(resource);
				}
			}
		}
		finally {
			if (table != null) { 
				table.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return resourceList;
	}
	
	public void deleteResourceReference(Person person, Organisation dataProvider, Type type) throws IOException {
		
	}

	private Table getTablePersonDataIndex(Connection connection) throws IOException {
		Table table = connection.getTable(TABLE_NAME_PERSON_DATA_INDEX);
		return table;
	}

	private byte[] getRowId(Person person) {
		String rowId = person.getPersonId() + DELIMITER + person.getPersonIdOid();
		byte[] rowIdBytes = Bytes.toBytes(rowId);
		return rowIdBytes;
	}
	
	private byte[] getColumnQualifier(Organisation dataProvider, Type type) {
		String columnQualilfier = dataProvider.getId() + DELIMITER + dataProvider.getIdOid() + 
				DELIMITER + type.getTypeCode() + DELIMITER + type.getTypeCodeOid();
		byte[] columnQualilfierBytes = Bytes.toBytes(columnQualilfier);
		return columnQualilfierBytes;
	}
	
	private Organisation getDataProvider(byte[] columnQualifierBytes) {
		Organisation dataProvider = new Organisation();
		String columnQualifier = Bytes.toString(columnQualifierBytes);
		StringTokenizer tokenizer = new StringTokenizer(columnQualifier, DELIMITER);
		dataProvider.setId(tokenizer.nextToken());
		dataProvider.setIdOid(tokenizer.nextToken());
		return dataProvider;
	}
	
	private Type getType(byte[] columnQualifierBytes) {
		Type type = new Type();
		String columnQualifier = Bytes.toString(columnQualifierBytes);
		StringTokenizer tokenizer = new StringTokenizer(columnQualifier, DELIMITER);
		tokenizer.nextToken();
		tokenizer.nextToken();
		type.setTypeCode(tokenizer.nextToken());
		type.setTypeCodeOid(tokenizer.nextToken());
		return type;
	}
	
	private byte[] getValue(Resource resource) {
		String value = resource.getResourceId() + DELIMITER + resource.getResourceIdOid();
		byte[] valueBytes = Bytes.toBytes(value);
		return valueBytes;
	}
	
	private Resource getResource(byte[] valueBytes) {
		Resource resource = new Resource();
		String value = Bytes.toString(valueBytes);
		StringTokenizer tokenizer = new StringTokenizer(value, DELIMITER);
		resource.setResourceId(tokenizer.nextToken());
		resource.setResourceIdOid(tokenizer.nextToken());
		return resource;
	}
	
}
