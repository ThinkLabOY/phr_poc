#! /bin/bash

wget --quiet http://www-eu.apache.org/dist/hbase/1.2.3/hbase-1.2.3-bin.tar.gz
tar xzf hbase-1.2.3-bin.tar.gz -C /opt/
ln -s /opt/hbase-1.2.3 /opt/hbase
rm hbase-1.2.3-bin.tar.gz
