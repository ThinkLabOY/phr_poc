package org.ech.phr.model.generic;

import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.ech.phr.hbase.dto.ValueProvider;
import org.ech.phr.model.exception.BusinessException;

@Slf4j
public abstract class MapperDto extends ValueProvider {

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
