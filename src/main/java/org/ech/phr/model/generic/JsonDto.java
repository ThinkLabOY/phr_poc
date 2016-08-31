package org.ech.phr.model.generic;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.ech.phr.hbase.dto.ValueProvider;
import org.ech.phr.model.exception.BusinessException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDto extends MapperDto {

	final static Logger log = Logger.getLogger(JsonDto.class);

	private ObjectMapper mapper = new ObjectMapper();
	
	public byte[] toValue() throws BusinessException {
		byte[] valueAsBytes = null;
		try {
			valueAsBytes = mapper.writeValueAsBytes(this);
		} 
		catch (JsonProcessingException e) {
			BusinessException.throwBusinessException(BusinessException.EX_JSN_001, e);
		}
		return valueAsBytes;
	}

	public void fromValue(byte[] valueAsBytes) throws BusinessException {
		try {
			JsonDto valueAsObject = mapper.readValue(valueAsBytes, this.getClass());
			this.mapFrom(valueAsObject);
		} 
		catch (JsonParseException e) {
			BusinessException.throwBusinessException(BusinessException.EX_JSN_001, e);
		} 
		catch (JsonMappingException e) {
			BusinessException.throwBusinessException(BusinessException.EX_JSN_002, e);
		} 
		catch (IOException e) {
			BusinessException.throwBusinessException(BusinessException.EX_JSN_003, e);
		}
	}
	
	public static <V extends ValueProvider> V create(byte[] valueAsBytes, Class<V> valueClass) throws BusinessException {
		V valueAsObject = null;
		try {
			ObjectMapper mapperI = new ObjectMapper();
			valueAsObject = mapperI.readValue(valueAsBytes, valueClass);
		} 
		catch (JsonParseException e) {
			BusinessException.throwBusinessException(BusinessException.EX_JSN_002, e);
		} 
		catch (JsonMappingException e) {
			BusinessException.throwBusinessException(BusinessException.EX_JSN_003, e);
		} 
		catch (IOException e) {
			BusinessException.throwBusinessException(BusinessException.EX_JSN_004, e);
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
			BusinessException.logException(BusinessException.EX_JSN_001, e);
		}
		return valueAsString;
	}


}
