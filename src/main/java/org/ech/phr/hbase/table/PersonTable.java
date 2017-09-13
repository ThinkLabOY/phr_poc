package org.ech.phr.hbase.table;

import java.util.List;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.ech.phr.hbase.table.generic.HbaseColumn;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.hbase.table.generic.HbaseTable;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Person;

public class PersonTable extends HbaseTable {
	
	public static String name = "phr:person";
	
	public static HbaseColumn<Person> ID = createColumn("id", Person.class);
	public static PersonTable PERSON_TABLE = new PersonTable();
	public static HTableDescriptor PERSON_TABLE_ID_DESCRITPOR = PERSON_TABLE.getDescriptorForColumn(ID);

	public PersonTable() {
		super(name);
	}
	
	public static HbaseRecord<Person> createIdRecord(String rowId) throws BusinessException {
		return PERSON_TABLE.createRecord(ID, rowId);
	}

	public static HbaseRecord<Person> createIdRecord(String columnQualifier, String rowId) throws BusinessException {
		return PERSON_TABLE.createRecord(ID, columnQualifier, rowId);
	}

	public static HbaseRecord<Person> createIdRecord(String columnQualifier, String rowId, Person person) throws BusinessException {
		return PERSON_TABLE.createRecord(ID, columnQualifier, rowId, person);
	}

	public static HbaseRecord<Person> createIdRecord(String columnQualifier, String rowId, List<Person> persons) throws BusinessException {
		return PERSON_TABLE.createRecord(ID, columnQualifier, rowId, persons);
	}

}
