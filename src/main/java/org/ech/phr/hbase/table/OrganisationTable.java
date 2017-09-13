package org.ech.phr.hbase.table;

import java.util.List;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.ech.phr.hbase.table.generic.HbaseColumn;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.hbase.table.generic.HbaseTable;
import org.ech.phr.model.exception.BusinessException;
import org.ech.phr.model.hbase.Organisation;

public class OrganisationTable extends HbaseTable {
	
	public static String name = "phr:organisation";
	
	public static HbaseColumn<Organisation> ID = createColumn("id", Organisation.class);
	public static OrganisationTable ORGANISATION_TABLE = new OrganisationTable();
	public static HTableDescriptor ORGANISATION_TABLE_ID_DESCRITPOR = ORGANISATION_TABLE.getDescriptorForColumn(ID);

	public OrganisationTable() {
		super(name);
	}
	
	public static HbaseRecord<Organisation> createIdRecord(String rowId) throws BusinessException {
		return ORGANISATION_TABLE.createRecord(ID, rowId);
	}

	public static HbaseRecord<Organisation> createIdRecord(String columnQualifier, String rowId) throws BusinessException {
		return ORGANISATION_TABLE.createRecord(ID, columnQualifier, rowId);
	}

	public static HbaseRecord<Organisation> createIdRecord(String columnQualifier, String rowId, Organisation organisation) throws BusinessException {
		return ORGANISATION_TABLE.createRecord(ID, columnQualifier, rowId, organisation);
	}

	public static HbaseRecord<Organisation> createIdRecord(String columnQualifier, String rowId, List<Organisation> organisations) throws BusinessException {
		return ORGANISATION_TABLE.createRecord(ID, columnQualifier, rowId, organisations);
	}
	
}
