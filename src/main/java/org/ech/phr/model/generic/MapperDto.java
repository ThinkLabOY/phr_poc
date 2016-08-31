package org.ech.phr.model.generic;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.ech.phr.hbase.dto.ValueProvider;
import org.ech.phr.model.exception.BusinessException;

public abstract class MapperDto extends ValueProvider {
	
	final static Logger log = Logger.getLogger(MapperDto.class);

	public void mapTo(MapperDto to) throws BusinessException {
		to.mapFrom(this);
	}
	
	public void mapFrom(MapperDto from) throws BusinessException {
		try {
			BeanUtils.copyProperties(this, from);
		} 
		catch (IllegalAccessException e) {
			BusinessException.throwBusinessException(BusinessException.EX_UTL_003, e);
		} 
		catch (InvocationTargetException e) {
			BusinessException.throwBusinessException(BusinessException.EX_UTL_003, e);
		}
	}

}
