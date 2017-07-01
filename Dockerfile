FROM centos:7.3.1611

RUN yum -y install wget glibc

RUN mkdir /jdk_home
WORKDIR /jdk_home
RUN wget -qO- --no-cookies \
            --no-check-certificate \
            --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \
            "http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.tar.gz" | tar xzf -
WORKDIR /

ENV JAVA_HOME "/jdk_home/jdk1.8.0_131"
ENV JRE_HOME "/jdk_home/jdk1.8.0_131/jre"
ENV PATH "$PATH:/jdk_home/jdk1.8.0_131/bin:/jdk_home/jdk1.8.0_131/jre/bin"

RUN yum install -y tar

# PHR application HTTP
EXPOSE 8081

RUN mkdir /phr
WORKDIR /phr
ADD ./target/phr-0.0.7.jar /phr/phr-0.0.7.jar
ENTRYPOINT java -jar phr-0.0.7.jar
