FROM openjdk:23-ea-17-slim-bullseye
WORKDIR /app
COPY jars/config-server.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]
