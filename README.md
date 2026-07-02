# Production Work Planner

Production Work Planner is a REST API for planning and tracking production work tasks.

The project was created as a learning and portfolio project to practice backend development with Java, Spring Boot, PostgreSQL, REST API design, validation, exception handling, DTOs, testing, and OpenAPI documentation.

## Features

- Create production work tasks
- Get all tasks
- Get task by ID
- Update task status
- Filter tasks by status
- Filter tasks by priority
- Filter tasks by status and priority
- Validate incoming requests
- Return structured error responses
- Provide API documentation with Swagger/OpenAPI

## Technology Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Jakarta Validation
- JUnit 5
- Mockito
- MockMvc
- Maven
- Swagger / OpenAPI

## Project Structure

```text
controller  - REST controllers
service     - business logic
repository  - data access layer
entity      - JPA entities
dto         - request and response DTOs
enums       - domain enums
exception   - custom exceptions and global exception handling
config      - application configuration
```

## Architecture

The application follows a simple layered architecture:

- Controller → Service → Repository → PostgreSQL
- Controller handles HTTP requests and responses.
- Service contains business logic.
- Repository provides access to the database through Spring Data JPA.
- Entity represents the internal domain model.
- DTOs are used for API input and output.

## Main Entity

WorkTask represents a production task.

Main fields:

- id
- title
- description
- status
- priority
- production area
- assignee name
- planned start date
- planned end date 
- created at
- updated at

The task also has calculated overdue status.

## API Endpoints

Create task

`POST /api/tasks`


Request body:
```
{
"title": "Prepare report",
"description": "Weekly production report",
"priority": "NORMAL",
"productionArea": "QUALITY_CONTROL",
"assigneeName": "Ivanov",
"plannedStartDate": "2026-07-01",
"plannedEndDate": "2026-07-02"
}
```
### Get all tasks

`GET /api/tasks`
### Filter tasks

```
GET /api/tasks?status=NEW
GET /api/tasks?priority=NORMAL
GET /api/tasks?status=NEW&priority=NORMAL
```
### Get task by ID

`GET /api/tasks/{id}`
### Update task status

`PATCH /api/tasks/{id}/status`

Request body:
```
{
"status": "IN_PROGRESS"
}
```
### Task Statuses
````
NEW
IN_PROGRESS
DONE
````
### Task Priorities
````
NORMAL
HIGH
````
### Production Areas
````
PREPARATION
PRIMARY_PROCESSING
SECONDARY_PROCESSING
FINAL_PROCESSING
QUALITY_CONTROL
````
### Validation

The application validates incoming requests.

Examples:

- title must not be blank
- priority must not be null
- production area must not be null
- planned end date must not be null
- planned end date cannot be before planned start date

Invalid requests return ```400 Bad Request```

### Error Handling

The application uses global exception handling.

Examples:

- `404 Not Found` when task does not exist
- `400 Bad Request` for invalid input
- `409 Conflict` for invalid state changes

Example error response:
```
{
"timestamp": "2026-07-01T12:00:00",
"status": 404,
"message": "Work task not found with id: 999"
}
```

## Swagger / OpenAPI

Swagger UI is available after application startup:

`http://localhost:8080/swagger-ui.html`

or:

`http://localhost:8080/swagger-ui/index.html`

OpenAPI JSON:

`http://localhost:8080/v3/api-docs`

## Database Configuration

The application uses PostgreSQL.

Example `application.properties:`
````
spring.datasource.url=jdbc:postgresql://localhost:5432/production_work_planner
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
````

Before running the application, create a PostgreSQL database:

`CREATE DATABASE production_work_planner;`

## Running the Application

Run with Maven:

`mvn spring-boot:run`

Or run the main class from your IDE:

`ProductionWorkPlannerApplication`
### Running Tests
`mvn test`

The project includes:

-entity unit tests
-service unit tests with Mockito
-controller tests with MockMvc
]]
## Notes

This project is focused on backend fundamentals:

- REST API design
- layered architecture
- DTO usage
- validation
- exception handling
- database access with Spring Data JPA
- automated testing
- API documentation
