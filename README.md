# Shareable Lock Backend Server

This is the backend server for the master thesis project "Shareable Lock".
We use microservices architecture to build the backend server, which is modified from
[ali-bouali/springboot-3-micro-service-demo](https://github.com/ali-bouali/springboot-3-micro-service-demo)
The project's documentation is available [here](https://devruibin.github.io/ShareableLockDocs/).

## Authors

1. [Ruibin Zhang](https://github.com/devruibin)
2. [Hasan Bilgin](https://github.com/hasancpp)

## Technologies Used

### Programming Languages and Frameworks
- Java 17
- Spring Ecosystem
- Lombok

### Build and Dependency Management
- Maven
- Gradle

### Databases and Data Management
- PostgreSQL
- Elasticsearch
- pgAdmin

### Messaging and Communication
- RabbitMQ
- MQTT
- LoRaWAN
- The Things Network

### Containerization and Orchestration
- Docker

### Monitoring and Logging
- Zipkin
- Kibana

### API and Documentation
- OpenFeign
- Swagger

### Web Server
- Nginx


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


> The docs necessary is in a submodule, see how to use the module [here](https://devruibin.github.io/ShareableLockDocs/submodule.html)

## Acknowledgements

1. [Ali Bouali](https://github.com/ali-bouali)