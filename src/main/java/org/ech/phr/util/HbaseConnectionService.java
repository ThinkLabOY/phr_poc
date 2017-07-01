package org.ech.phr.util;

import org.apache.hadoop.hbase.client.Connection;
import org.ech.phr.model.exception.BusinessException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="request")
public class HbaseConnectionService {
	
	public Connection getHbaseConnection() throws BusinessException {
		Connection connection = HbaseUtil.getHbaseConnection();
		return connection;
	}

}
