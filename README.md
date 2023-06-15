# User Validantion API 


This is my first developed REST API, created as an active study method for applying my recent Spring Boot concepts learned.

The project contains functions for create, read, update and delete users, in addition of a stateless acess control with JWT Tokens.
The application also has a role validation, roles as ADMIN can use all functions, but default users are more limited.

## Technologies
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- MySQL
- Lombok
- Flyway
- Jason Web Token

## How to run


### Requisite
Java 17 / MySQL / Maven


```bash
# Clone repository
git clone https://github.com/JJpedroGomes/User-Validation-API.git

# Create MySQL database
create database user_api

# Build the project
mvn package

# execute the project
cd api
java "-Dspring.profiles.active=prod" "-DDATASOURCE_URL=DATABASE URL" "-DDATASOURCE_USERNAME=DATABASE USERNAME" "-DDATASOURCE_PASSWORD=DATABASE PASSWORD" -jar .\target\api-0.0.1-SNAPSHOT.jar
```
- The app will start running at http://localhost:8080
- Automatically Flyway will genarate tables in your database and create an ADMIN user
- Log in with the ADMIN user using the following [Post method](#login)
- Get the JWT token after logging in. 
- Using any Rest Client like Postman, put the JWT token in Bearer token field before any request
- It is also possible to test it using the application swagger: /swagger-ui.html - put the JWT token in authorize field


## Explore

The app defines following CRUD.

### Authentication

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /login | login | [JSON](#login) |

### User

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST    | /user/add | Creates a new user in the database | [JSON](#adduser)|
| GET    | /user | Get a list of all users | |
| GET    | /user/{userID} | Get details of a single user | |
| PUT    | /user | Update user information | [JSON](#update)|
| DELETE    | /user/{userID} | Set a user as inactive | |

## Sample Valid JSON Request Bodys

##### <a id="login">/login</a>
```json
{
	"login": "admin",
	"password": "admin"	
}
```

##### <a id="adduser">/user/add</a>
```json
{
	"name": "Pedro Cruz",
	"email": "pedro.cruz@email.com",
	"login": "pedro.cruz",
	"password": "Us123456"	
}

```

##### <a id="update">/user</a>
```json
{
	"id": "2", 
	"name": "Pedro Cruz Junior",
	"email": "pedro.cruz.junior@email.com",
	"login": "pedro.cruz13",
	"password": "Password2",
	"role": "ADMIN"
}

Only id is required
Role changes can only be used by Admin users
Admin users can update all users
Default users can only update their own info
```

# Author

João Pedro Gomes

https://www.linkedin.com/in/jo%C3%A3o-pedro-gomes-082193177/

