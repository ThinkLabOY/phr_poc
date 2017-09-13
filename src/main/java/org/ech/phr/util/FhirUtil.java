package org.ech.phr.util;

public class FhirUtil {
	
	public static final String DELIMITER = "/";
	
	public static final String OID_PHR = "https://org.ech.phr/";
	public static final String OID_PHR_INTERNAL = "https://org.ech.phr/internal/";
	public static final String OID_MASTER = "https://org.ech.phr/master/";
	public static final String OID_FHIR = "http://hl7.org/fhir";
	
	public static String composeId(String id, String idOid, String resourceType) {
		return idOid + resourceType + DELIMITER + id;
	}

	public static String composeId(String id, String idOid) {
		return idOid + id;
	}

}
