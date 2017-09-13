FROM centos:7.3.1611

RUN yum -y install wget glibc

RUN mkdir /jdk_home
WORKDIR /jdk_home
RUN wget -qO- --no-cookies \
            --no-check-certificate \
            --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \
            "http://download.oracle.com/otn-pub/java/jdk/8u144-b01/090f390dda5b47b9b721c7dfaa008135/jdk-8u144-linux-x64.tar.gz" | tar xzf -
WORKDIR /

ENV JAVA_HOME "/jdk_home/jdk1.8.0_144"
ENV JRE_HOME "/jdk_home/jdk1.8.0_144/jre"
ENV PATH "$PATH:/jdk_home/jdk1.8.0_144/bin:/jdk_home/jdk1.8.0_144/jre/bin"

ADD ./install-hbase.sh /tmp/install-hbase.sh
RUN /tmp/install-hbase.sh
RUN rm /tmp/install-hbase.sh

RUN /opt/hbase/bin/hbase-config.sh

ADD hbase-site.xml /opt/hbase/conf/hbase-site.xml
ADD start-pseudo-distributed.sh /opt/hbase/bin/start-pseudo-distributed.sh
ADD create_data_structure.txt /opt/hbase/bin/create_data_structure.txt
ADD create_data_structure.sh /opt/hbase/bin/create_data_structure.sh

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
# PHR application HTTP
EXPOSE 8081

WORKDIR /opt/hbase/bin

ENV PATH=$PATH:/opt/hbase/bin
CMD /opt/hbase/bin/start-pseudo-distributed.sh
