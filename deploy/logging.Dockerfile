FROM openjdk:23-ea-17-slim-bullseye
WORKDIR /app
COPY  jars/logging.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN apt-get update && apt-get install -y curl netcat && rm -rf /var/lib/apt/lists/*
RUN chmod +x /wait-for-it.sh
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["sh", "/wait-for-it.sh", "spring-config-server", "8080", "java", "-jar", "app.jar"]