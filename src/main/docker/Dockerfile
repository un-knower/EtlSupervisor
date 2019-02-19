FROM java:8
MAINTAINER leo
VOLUME /tmp
ADD EtlSupervisor1.0.tar.gz /opt/EtlSupervisor1.0.tar.gz
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/EtlSupervisor1.0.tar.gz/etlsupervisor-0.0.1-SNAPSHOT.jar"]