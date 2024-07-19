
# Car Management Application

## Overview
The Car Management application consists of two modules: Angular for the frontend and Spring Boot for the backend. The application allows you to perform CRUD operations for cars and brands, as well as filtering and sorting functions.

## Getting Started

### Prerequisites
- Java JDK 17+
- Node.js ve npm
- Angular CLI
- Maven
- PostgreSQL

### Installation and Running

#### Backend Module
Follow the steps below to start the backend project:
```bash
cd BackendModule
mvn spring-boot:run
```
The backend project runs on the default port [http://localhost:10150](http://localhost:10150)

#### Frontend Module
Follow the steps below to start the frontend project:
```bash
cd ../FrontendModule
ng serve
```
The backend project runs on the default port [http://localhost:4150](http://localhost:4150)

## Port Change

### Backend Port Change
To change the port in the backend project, update the `server.port` value in the `application.properties` file:
```properties
server.port=10150
```

### Frontend Port Change
To change the port in the frontend project, update the following section in the `angular.json` file:
```json
{
  ...
  "projects": {
    "your-project-name": {
      "architect": {
        "serve": {
          "options": {
            "port": 4150
          }
        }
      }
    }
  }
  ...
}
```

## Swagger UI
To access the API documentation, you can visit the following URL after the backend module is running:
```
http://localhost:10150/swagger-ui/index.html
```

## Technologies Stack
### Frontend:
- Angular
- TypeScript
- HTML / CSS
- Bootstrap

### Backend:
- Spring Boot
- Java
- Maven
- JPA / Hibernate
- Camunda

### Database:
- PostgreSQL
- Redis
