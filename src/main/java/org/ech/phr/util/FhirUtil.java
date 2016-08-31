package org.ech.phr.util;

public class FhirUtil {
	
	public static final String TYPE_PATIENT = "Patient";
	public static final String TYPE_ORGANISATION = "Organisation";
	public static final String TYPE_OBSERVATION = "Observation";
	public static final String TYPE_TYPE = "Type";
	public static final String DELIMITER = "/";
	
	public static final String OID_PHR = "https://org.ech.phr/";
	public static final String OID_PHR_INTERNAL = "https://org.ech.phr/internal/";
	public static final String OID_MASTER = "https://org.ech.phr/master/";
	
	public static String composeId(String id, String idOid, String resourceType) {
		return idOid + resourceType + DELIMITER + id;
	}

	public static String composeId(String id, String idOid) {
		return idOid + id;
	}

}
