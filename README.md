# Shareable Lock Backend Server

This is the backend server for the master thesis project "Shareable Lock".
We use microservices architecture to build the backend server, which is modified from
[ali-bouali/springboot-3-micro-service-demo](https://github.com/ali-bouali/springboot-3-micro-service-demo)

## Technologies

1. Java 17
2. Spring Boot 3.2.6
3. Spring Cloud
4. Spring Data JPA
5. PostgreSQL
6. Docker
7. Maven
8. Lombok
9. Gradle
10. RabbitMQ
11. Zipkin
12. OpenFeign
13. Swagger
14. Nginx
15. elasticsearch
16. Kibana
17. pgAdmin
18. Mqtt
19. LoRaWAN
20. The Things Network

## Project Components


  | Application               | Port  | API Endpoint               |
  |---------------------------|-------|----------------------------|
  | DiscoveryApplication      | 8761  |                            |
  | GatewayApplication        | 8222  |                            |
  | LockApplication           | 8300  | /api/v1/locks              |
  | MessageApplication        | 8200  | /api/v1/messages           |
  | SchoolApplication         | 8070  |                            |
  | UserApplication           | 8100  | /api/v1/users              |
  | ConfigServerApplication   | 8888  |                            |
  | StudentApplication        | 8090  |                            |
  | StaticServerApplication   | 8400  | /api/v1/files/             |
  | Nginx                     | 8500  |                            |
  | MQTT                      | 8600  |                            |
  | Logging                   | 8700  | /api/v1/logs/              |
  | Authentication            | 8800  | /api/v1/auth/              |

For more details, please refer to the [API documentation](https://devruibin.github.io/ShareableLockDocs/apis.html).


## The docs necessary is in a submodule, see how to use the module [here](https://devruibin.github.io/ShareableLockDocs/submodule.html)
