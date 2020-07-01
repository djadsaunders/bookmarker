FROM ubuntu
RUN apt-get update
ENV DEBIAN_FRONTEND noninteractive

# Install Java, PSQL and wget
RUN apt-get install -y openjdk-8-jdk postgresql-client wget

# Install Flyway
RUN wget -qO- https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/6.5.0/flyway-commandline-6.5.0-linux-x64.tar.gz | tar xvz && ln -s `pwd`/flyway-6.5.0/flyway /usr/local/bin 

# Setup App
COPY src/sql/*.sql /opt/app/sql/
COPY target/*.jar /opt/app/
COPY docker-entrypoint.sh /usr/local/bin/
COPY wait-for-it.sh /usr/local/bin/
ENTRYPOINT ["java","-jar","/opt/app/bookmarker-0.0.1-SNAPSHOT.jar"]
