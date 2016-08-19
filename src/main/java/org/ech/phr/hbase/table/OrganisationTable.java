package org.ech.phr.hbase.table;

import java.util.List;

import org.ech.phr.hbase.table.generic.HbaseColumn;
import org.ech.phr.hbase.table.generic.HbaseRecord;
import org.ech.phr.hbase.table.generic.HbaseTable;
import org.ech.phr.model.Organisation;

public class OrganisationTable extends HbaseTable {
	
	public static String name = "phr:organisation";
	
	public static HbaseColumn<Organisation> ID = createColumn("id", Organisation.class);
	public static OrganisationTable ORGANISATION_TABLE = new OrganisationTable();

	public OrganisationTable() {
		super(name);
	}
	
	public static HbaseRecord<Organisation> createIdRecord(String rowId) {
		return ORGANISATION_TABLE.createRecord(ID, rowId);
	}

	public static HbaseRecord<Organisation> createIdRecord(String columnQualifier, String rowId) {
		return ORGANISATION_TABLE.createRecord(ID, columnQualifier, rowId);
	}

	public static HbaseRecord<Organisation> createIdRecord(String columnQualifier, String rowId, Organisation organisation) {
		return ORGANISATION_TABLE.createRecord(ID, columnQualifier, rowId, organisation);
	}

	public static HbaseRecord<Organisation> createIdRecord(String columnQualifier, String rowId, List<Organisation> organisations) {
		return ORGANISATION_TABLE.createRecord(ID, columnQualifier, rowId, organisations);
	}

}
