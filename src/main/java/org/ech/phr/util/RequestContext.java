package org.ech.phr.util;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Connection;
import org.ech.phr.model.exception.BusinessException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("request")
public class RequestContext {

	private Connection connection;

	private void initConnection() throws BusinessException  {
		if (connection == null) {
			connection = HbaseUtil.getHbaseConnection();
		}
	}
	
	public Connection getConnection() throws BusinessException  {
		initConnection();
		return connection;
	}
	

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void closeContext() throws BusinessException {
		if (connection != null) {
			try {
				connection.close();
			} 
			catch (IOException ioe) {
				BusinessException.throwBusinessException(BusinessException.EX_HBS_001);
			}
		}
	}

}
