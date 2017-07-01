package org.ech.phr.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.ech.phr.PhrPocApplication;
import org.ech.phr.model.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;

public class HbaseUtil {

	public static Configuration configuration = getConf();
	
	private static Configuration getConf() {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", PhrPocApplication.hbaseZookeeperQuorom); // Docker ip
		conf.set("hbase.client.retries.number", Integer.toString(1));
		conf.set("zookeeper.session.timeout", Integer.toString(60000));
		conf.set("zookeeper.recovery.retry", Integer.toString(1));
		return conf;
	}

	public static Connection getHbaseConnection() throws BusinessException {
		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(HbaseUtil.configuration);
		}
		catch (IOException e) {
			BusinessException.throwBusinessException(BusinessException.EX_HBS_001, e);
		}
		return connection;
	}
	
}
