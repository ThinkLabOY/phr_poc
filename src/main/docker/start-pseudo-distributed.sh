#!/bin/bash

echo "starting Spring application"
java -jar -Djava.security.egd="file:/dev/./urandom" /app.jar & apid=$!;   #-DFOREGROUND
echo "apid: " + apid

echo "starting HBase"
/opt/hbase/bin/hbase zookeeper > logzoo.log 2>&1 &
/opt/hbase/bin/hbase regionserver start > logregion.log 2>&1 &

/opt/hbase/bin/hbase master start --localRegionServers=0

echo "startup done"

