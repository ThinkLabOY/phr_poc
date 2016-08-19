package org.ech.phr.model.generic;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.ech.phr.hbase.dto.ValueProvider;

public abstract class MapperDto extends ValueProvider {
	
	final static Logger log = Logger.getLogger(MapperDto.class);

	public void mapTo(MapperDto to) {
		to.mapFrom(this);
	}
	
	public void mapFrom(MapperDto from) {
		try {
			BeanUtils.copyProperties(this, from);
		} 
		catch (IllegalAccessException e) {
			log.error("mapFrom IllegalAccessException", e);
		} 
		catch (InvocationTargetException e) {
			log.error("mapFrom InvocationTargetException", e);
		}
	}

}
