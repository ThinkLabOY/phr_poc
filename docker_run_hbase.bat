docker run -t -i --restart always --name hbase --network phr_ntwrk --ip 172.30.0.10 -p 2181:2181 -p 60010:60010 -p 60000:60000 -p 60020:60020 -p 60030:60030 hbase:1.0