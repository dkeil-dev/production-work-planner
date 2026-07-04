# Production Work Planner

Production Work Planner — это REST API для планирования и контроля производственных задач.

Идея проекта основана на типовых задачах производственного отдела: создание задач, назначение ответственного, контроль статуса, приоритета и сроков выполнения. Проект реализован как backend MVP без пользовательского интерфейса, но с документацией API через Swagger/OpenAPI.


## Возможности

* Создание производственной задачи
* Получение списка задач
* Получение задачи по id
* Фильтрация задач по статусу
* Фильтрация задач по приоритету
* Фильтрация задач по статусу и приоритету одновременно
* Изменение статуса задачи
* Просмотр просроченных задач
* Проверка входных данных через validation
* Обработка ошибок через global exception handler
* Документация API через Swagger/OpenAPI
* Автоматические тесты сервисного и web-слоя

## Технологии

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* JUnit 5
* Mockito
* MockMvc
* Swagger / OpenAPI

## Архитектура проекта

Проект построен по классической layered architecture:

```text
Controller -> Service -> Repository -> Database
```

Основные слои:

* `controller` — REST endpoints
* `service` — бизнес-логика
* `repository` — работа с базой данных через Spring Data JPA
* `entity` — JPA-сущности
* `dto` — объекты для входящих и исходящих данных API
* `exception` — пользовательские исключения и обработка ошибок
* `enums` — статусы, приоритеты и производственные участки
* `config` — конфигурация приложения

## Основная сущность

Главная сущность проекта — `WorkTask`.

Задача содержит:

* id
* название
* описание
* статус
* приоритет
* производственный участок
* имя ответственного
* плановую дату начала
* плановую дату окончания
* дату создания
* дату обновления

Статусы задачи:

```text
NEW
IN_PROGRESS
DONE
```

Приоритеты:

```text
NORMAL
HIGH
```

Пример производственных участков:

```text
PREPARATION
PRIMARY_PROCESSING
SECONDARY_PROCESSING
FINAL_PROCESSING
QUALITY_CONTROL
```

## API endpoints

### Создать задачу

```http
POST /api/tasks
```

Пример тела запроса:

```json
{
  "title": "Prepare production report",
  "description": "Prepare weekly report for quality control",
  "priority": "NORMAL",
  "productionArea": "QUALITY_CONTROL",
  "assigneeName": "Ivanov",
  "plannedStartDate": "2026-07-01",
  "plannedEndDate": "2026-07-05"
}
```

### Получить все задачи

```http
GET /api/tasks
```

### Получить задачу по id

```http
GET /api/tasks/{id}
```

### Получить задачи по статусу

```http
GET /api/tasks?status=NEW
```

### Получить задачи по приоритету

```http
GET /api/tasks?priority=HIGH
```

### Получить задачи по статусу и приоритету

```http
GET /api/tasks?status=NEW&priority=HIGH
```

### Изменить статус задачи

```http
PATCH /api/tasks/{id}/status
```
### Получить просроченные задачи

```http
GET /api/tasks/overdue

Пример тела запроса:

```json
{
  "status": "IN_PROGRESS"
}
```

## Пример сценария использования

Типовой сценарий работы с системой:

```md
Пример последовательности запросов:

```http
POST /api/tasks
GET /api/tasks
GET /api/tasks?status=NEW
GET /api/tasks?priority=HIGH
GET /api/tasks/overdue
PATCH /api/tasks/{id}/status
GET /api/tasks?status=DONE
```

## Validation

В проекте используется validation для входящих DTO.

Примеры правил:

* название задачи не может быть пустым
* приоритет обязателен
* производственный участок обязателен
* плановая дата окончания обязательна
* статус при обновлении задачи обязателен

Если входные данные некорректны, API возвращает ошибку со статусом `400 Bad Request`.

## Обработка ошибок

В проекте реализован global exception handler.

Примеры ошибок:

* задача не найдена — `404 Not Found`
* некорректные данные — `400 Bad Request`
* недопустимое изменение состояния задачи — `409 Conflict`

Пример ответа при ошибке:

```json
{
  "timestamp": "2026-07-04T12:00:00",
  "status": 404,
  "message": "Work task not found with id: 999"
}
```

## Swagger / OpenAPI

После запуска приложения документация API доступна по адресу:

```text
http://localhost:8080/swagger-ui.html
```

или:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Настройка базы данных

Проект использует PostgreSQL.

Пример настроек в `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/production_work_planner
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

Перед запуском нужно создать базу данных:

```sql
CREATE DATABASE production_work_planner;
```

## Запуск приложения

Собрать проект:

```bash
mvn clean package
```

Запустить приложение:

```bash
mvn spring-boot:run
```

Или запустить main-класс из IDE.

## Запуск тестов

```bash
mvn test
```

В проекте есть тесты для:

* domain logic
* service layer
* controller layer
* обработки ошибок
* фильтрации задач

## Что было отработано в проекте

В рамках проекта были отработаны:

* создание REST API на Spring Boot
* работа с PostgreSQL через Spring Data JPA
* разделение приложения на слои
* использование DTO для входящих и исходящих данных
* validation входных запросов
* exception handling
* derived query methods в Spring Data JPA
* тестирование через JUnit 5, Mockito и MockMvc
* документирование API через Swagger/OpenAPI
* работа с Git и Maven

## Roadmap

Возможные направления развития проекта:

* Добавить обновление данных задачи: название, описание, приоритет, сроки и ответственный
* Добавить историю изменения статусов
* Добавить комментарии к задачам
* Выделить пользователей и ответственных в отдельные сущности
* Добавить роли пользователей
* Добавить Spring Security
* Добавить Docker и Docker Compose
* Добавить миграции базы данных через Liquibase или Flyway
* Добавить интеграционные тесты с тестовой базой данных
* Добавить пагинацию и сортировку списка задач
* Добавить простой frontend или admin UI
* Подготовить production deployment configuration

Текущая версия сфокусирована на backend fundamentals: REST API, layered architecture, DTO, validation, exception handling, PostgreSQL, Spring Data JPA, automated tests and OpenAPI documentation.

## English Summary

Production Work Planner is a Spring Boot REST API for managing production work tasks.

The project includes task creation, status updates, filtering by status and priority, DTOs, validation, exception handling, PostgreSQL persistence, automated tests and OpenAPI documentation.

The project is based on typical production workflow scenarios: task planning, responsibility assignment, status tracking, priority control and deadline management.
