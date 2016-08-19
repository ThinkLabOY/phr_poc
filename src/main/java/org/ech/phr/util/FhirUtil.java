package org.ech.phr.util;

public class FhirUtil {
	
	public static final String TYPE_PATIENT = "Patient";
	public static final String TYPE_OBSERVATION = "Observation";
	public static final String DELIMITER = "/";
	
	public static final String OID_PHR = "https://org.ech.phr/";
	public static final String OID_MASTER = "MASTER";
	
	public static String composeId(String id, String idOid, String resourceType) {
		return idOid + DELIMITER + resourceType + DELIMITER + id;
	}

	public static String composeId(String id, String idOid) {
		return idOid + DELIMITER + id;
	}

}
