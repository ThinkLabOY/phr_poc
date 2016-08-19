package org.ech.phr.model.generic;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.ech.phr.hbase.dto.ValueProvider;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDto extends MapperDto {

	final static Logger log = Logger.getLogger(JsonDto.class);

	private ObjectMapper mapper = new ObjectMapper();
	
	public byte[] toValue() {
		byte[] valueAsBytes = null;
		try {
			valueAsBytes = mapper.writeValueAsBytes(this);
		} 
		catch (JsonProcessingException e) {
			log.error("JsonProcessingException toValue", e);
		}
		return valueAsBytes;
	}

	public void fromValue(byte[] valueAsBytes) {
		try {
			JsonDto valueAsObject = mapper.readValue(valueAsBytes, this.getClass());
			this.mapFrom(valueAsObject);
		} 
		catch (JsonParseException e) {
			log.error("JsonParseException fromValue", e);
		} 
		catch (JsonMappingException e) {
			log.error("JsonMappingException fromValue", e);
		} 
		catch (IOException e) {
			log.error("IOException fromValue", e);
		}
	}
	
	public static <V extends ValueProvider> V create(byte[] valueAsBytes, Class<V> valueClass) {
		V valueAsObject = null;
		try {
			ObjectMapper mapperI = new ObjectMapper();
			valueAsObject = mapperI.readValue(valueAsBytes, valueClass);
		} 
		catch (JsonParseException e) {
			log.error("JsonParseException fromValue", e);
		} 
		catch (JsonMappingException e) {
			log.error("JsonMappingException fromValue", e);
		} 
		catch (IOException e) {
			log.error("IOException fromValue", e);
		}
		return valueAsObject;
	}
	
	@Override
	public String toString() {
		String valueAsString = null;
		try {
			valueAsString = mapper.writeValueAsString(this);
		} 
		catch (JsonProcessingException e) {
			log.error("JsonProcessingException toString", e);
		}
		return valueAsString;
	}


}
