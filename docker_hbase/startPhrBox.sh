
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

java -jar -Djava.security.egd="file:/dev/./urandom" /app.jar

sh /opt/hbase/bin/start-pseudo-distributed.sh