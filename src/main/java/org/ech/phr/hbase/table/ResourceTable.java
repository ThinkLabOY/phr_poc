package org.ech.phr.hbase.table;

import java.util.List;

import org.ech.phr.hbase.table.generic.HbaseColumn;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.hbase.table.generic.HbaseTable;
import org.ech.phr.model.Resource;

public class ResourceTable extends HbaseTable {
	
	public static String name = "phr:resource";
	
	public static HbaseColumn<Resource> ID = createColumn("id", Resource.class);
	public static ResourceTable RESOURCE_TABLE = new ResourceTable();

	public ResourceTable() {
		super(name);
	}

	public static HbaseRecord<Resource> createIdRecord(String rowId) {
		return RESOURCE_TABLE.createRecord(ID, rowId);
	}

	public static HbaseRecord<Resource> createIdRecord(String columnQualifier, String rowId) {
		return RESOURCE_TABLE.createRecord(ID, columnQualifier, rowId);
	}

	public static HbaseRecord<Resource> createIdRecord(String columnQualifier, String rowId, Resource resource) {
		return RESOURCE_TABLE.createRecord(ID, columnQualifier, rowId, resource);
	}

	public static HbaseRecord<Resource> createIdRecord(String columnQualifier, String rowId, List<Resource> resources) {
		return RESOURCE_TABLE.createRecord(ID, columnQualifier, rowId, resources);
	}

}
