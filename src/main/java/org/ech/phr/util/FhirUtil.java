package org.ech.phr.util;

import java.net.URI;

import org.ech.phr.model.fhir.Identifier;
import org.ech.phr.model.fhir.ResourceTypeEnum;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FhirUtil {
	
	public static final String DELIMITER = "/";
	public static final String DELIMITER_START_PARAM = "?";
	
	public static final String OID_PHR = "https://org.ech.phr/";
	public static final String OID_PHR_INTERNAL = "https://org.ech.phr/internal/";
	public static final String OID_MASTER = "https://org.ech.phr/master/";
	public static final String OID_FHIR = "http://hl7.org/fhir";
	
	public static String composeId(String id, String idOid, ResourceTypeEnum resourceType) {
		return idOid + resourceType.getText() + DELIMITER + id;
	}

	public static String composeId(String id, String idOid) {
		return idOid + id;
	}

	public static String createReference(ResourceTypeEnum resourceType, String organizationId) {
		return resourceType.getText() + DELIMITER + organizationId;
	}

	public static String createLocationPath(ResourceTypeEnum resource, Identifier identifier, String systemParameterName) {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		queryParams.add(systemParameterName, identifier.getSystem());
		return createLocationPath(resource, identifier, queryParams);
	}

	public static String createLocationPath(ResourceTypeEnum resource, MultiValueMap<String, String> queryParams) {
		return createLocationPath(resource, null, queryParams);
	}
	
	public static String createLocationPath(ResourceTypeEnum resource, Identifier identifier, MultiValueMap<String, String> queryParams) {
		String path = "";
		if (identifier != null) {
			path += FhirUtil.DELIMITER + identifier.getValue();
		}
		URI pathUri = UriComponentsBuilder.fromPath(path)
				.queryParams(queryParams)
				.build()
				.toUri();
		return pathUri.toString();
	}

}
