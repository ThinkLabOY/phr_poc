package org.ech.phr.util;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class RequestContext {
	
	final static Logger log = Logger.getLogger(RequestContext.class);
	private Connection connection;

	private void initConnection() {
		if (connection == null) {
			connection = HbaseUtil.getHbaseConnection();
		}
	}
	
	public Connection getConnection() {
		initConnection();
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void closeContext() {
		if (connection != null) {
			try {
				connection.close();
			} 
			catch (IOException e) {
				log.error("getHbaseConnection IOException", e);
			}
		}
	}

}
