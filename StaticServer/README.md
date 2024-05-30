# Static Server with Nginx and Spring Boot

This project sets up a static server using Docker, Docker Compose, Spring Boot, and Nginx. The server allows users to download files through Nginx and upload files through a Spring Boot backend.

## Setup Instructions

1. Build the Spring Boot Application
   ```bash
    cd Server
    ./gradlew bootJar
   ```
2. Start the project
   ```bash
    docker-compose up --build
   ```
   
## Endpoints

```
URL: http://localhost:8080/api/upload
Method: POST
Form Data:
file: The file to be uploaded.
Upload File (Secrets):

URL: http://localhost:8080/api/upload-secrets
Method: POST
Form Data:
file: The file to be uploaded.
List Uploaded Files:

URL: http://localhost:8080/api/upload
Method: GET
Response: A list of filenames in the uploads directory.
```

## File Size
Adjust the maximum file size in application.properties if necessary:

```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```