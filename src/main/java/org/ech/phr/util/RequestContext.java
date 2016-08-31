package org.ech.phr.util;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;
import org.ech.phr.model.exception.BusinessException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class RequestContext {
	
	final static Logger log = Logger.getLogger(RequestContext.class);
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
