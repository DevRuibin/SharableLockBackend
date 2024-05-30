FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/Server-0.0.1-SNAPSHOT.jar static-server.jar
ENTRYPOINT ["java","-jar","/static-server.jar"]

