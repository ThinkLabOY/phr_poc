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

# PHR application HTTP
EXPOSE 8081

RUN mkdir /phr
WORKDIR /phr
ADD ./target/phr-0.0.10.jar /phr/phr-0.0.10.jar
ENTRYPOINT java -jar phr-0.0.10.jar
