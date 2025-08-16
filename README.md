# CI/CD app with Java and Monitoring

This is a simple example of a RESTful API built with Spring Boot using a PostgreSQL database. The project implements basic operations to manage users.

---

## Description

The application provides a REST API for creating and retrieving users.  
The user entity `User` contains fields `id`, `username`, and `email`.

---

## Technologies

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA (Hibernate)
- PostgreSQL 15
- Docker and Docker Compose
- Lombok
- Gradle

---

## Project Structure

- `src/main/java/com/example/dev` — application source code
- `src/main/resources/application.properties` — application configurations
- `Dockerfile` — to build Docker image
- `docker-compose.yml` — to run app and database containers
- `build.gradle` — build and dependencies

---

## Running the Project

### Requirements

- Docker and Docker Compose
- Java 24 (for local run without Docker)
- PostgreSQL (inside Docker container or locally for test without Docker)

### Run with Docker

```bash
docker compose up --build
```

## REST API

### Check Application Status

```bash
curl -X GET http://localhost:8080/health
```

Expected response: {"status":"UP"}

### Get All Users

```bash
curl -X GET http://localhost:8080/api/users
```

Expected response: {all users in database}

### Create a New User

```bash
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-d '{"username": "username", "email": "useremail@example.com"}'
```
Expected response: {"id":5,"username":"username","email":"useremail@example.com"}


