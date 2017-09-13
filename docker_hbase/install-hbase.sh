#! /bin/bash

wget -q -o out.log -P /tmp http://www-eu.apache.org/dist/hbase/stable/hbase-1.2.6-bin.tar.gz


tar xzf /tmp/hbase-1.2.6-bin.tar.gz -C /usr/local
rm /tmp/hbase-1.2.6-bin.tar.gz
mv /usr/local/hbase-1.2.6 /opt/hbase-1.2.6
ln -s /opt/hbase-1.2.6 /opt/hbase