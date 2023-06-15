# User Validantion API 


This is my first developed REST API, created as an active study method for applying my recent Spring Boot concepts learned.

The project contains functions for create, read, update and delete users, in addition of a stateless acess control with JWT Tokens.
The application also has a role validation, roles as ADMIN can use all functions, but default users are more limited.

## Model

## Database


## Technologies
- Java
- Spring Boot
- JPA / Hibernate
- Maven

## How to run


Prerequisite Java 17

```bash
# clone repository
git clone https://github.com/JJpedroGomes/User-Validation-API.git

# execute the project
mvn install

java "-Dspring.profiles.active=prod" "-DDATASOURCE_URL=DATABASE URL" "-DDATASOURCE_USERNAME=DATABASE USERNAME" "-DDATASOURCE_PASSWORD=DATA BASE PASSWORD" -jar .\target\api-0.0.1-SNAPSHOT.jar
```

# Autor

João Pedro Gomes

https://www.linkedin.com/in/jo%C3%A3o-pedro-gomes-082193177/

