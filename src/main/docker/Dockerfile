#FROM frolvlad/alpine-oraclejdk8:slim
#VOLUME /tmp
#ADD gs-spring-boot-docker-0.1.0.jar app.jar
#RUN sh -c 'touch /app.jar'
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM centos:6.6
MAINTAINER Raul Piiber <raul.piiber@affecto.com>

RUN yum install -y java-1.8.0-openjdk.x86_64
ENV JAVA_HOME=/usr/lib/jvm/jre

RUN yum install -y wget
RUN yum install -y nc
RUN yum install -y tar

RUN mkdir /hbase-setup
WORKDIR /hbase-setup

ADD ./install-hbase.sh /hbase-setup/
RUN ./install-hbase.sh

RUN /opt/hbase/bin/hbase-config.sh

ADD hbase-site.xml /opt/hbase/conf/hbase-site.xml
ADD start-pseudo-distributed.sh /opt/hbase/bin/start-pseudo-distributed.sh

ADD startPhrBox.sh /opt/hbase/bin/startPhrBox.sh

# zookeeper
EXPOSE 2181
# HBase Master API port
EXPOSE 60000
# HBase Master Web UI
EXPOSE 60010
# Regionserver API port
EXPOSE 60020
# HBase Regionserver web UI
EXPOSE 60030

WORKDIR /opt/hbase/bin

ENV PATH=$PATH:/opt/hbase/bin


VOLUME /tmp
ADD phr-0.0.1.jar /app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["/opt/hbase/bin/startPhrBox.sh"]

# start HBase
#CMD /opt/hbase/bin/start-pseudo-distributed.sh
#CMD /opt/hbase/bin/startPhrBox.sh