FROM gradle:jdk17-jammy AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build -x test
RUN ls -l build/libs/

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/Server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]
