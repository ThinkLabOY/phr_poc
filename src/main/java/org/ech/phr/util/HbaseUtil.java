package org.ech.phr.util;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.ech.phr.model.exception.BusinessException;

@Slf4j
public class HbaseUtil {

	private static Configuration getConf() {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "127.0.0.1");      // Local ip
//		conf.set("hbase.zookeeper.quorum", "192.168.15.166"); // Vagrant ip
//		conf.set("hbase.zookeeper.quorum", "192.168.99.100"); // Docker ip
//		conf.set("hbase.client.retries.number", Integer.toString(1));
//		conf.set("zookeeper.session.timeout", Integer.toString(60000));
//		conf.set("zookeeper.recovery.retry", Integer.toString(1));
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
