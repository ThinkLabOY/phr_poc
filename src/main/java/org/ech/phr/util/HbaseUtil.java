package org.ech.phr.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.log4j.Logger;
import org.ech.phr.model.exception.BusinessException;

public class HbaseUtil {

	final static Logger log = Logger.getLogger(HbaseUtil.class);

	private static Configuration getConf() {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.15.166");
		return conf;
	}

	public static Connection getHbaseConnection() throws BusinessException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(getConf());
		} 
		catch (IOException e) {
			BusinessException.throwBusinessException(BusinessException.EX_HBS_001, e);
		}
		return connection;
	}


}
