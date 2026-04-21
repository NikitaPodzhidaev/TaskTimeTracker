# Task Time Tracker API

Backend REST-сервис для учета рабочего времени сотрудников по задачам.

## Описание проекта

Сервис позволяет:
- создавать задачи;
- получать информацию о задаче по ID;
- изменять статус задачи;
- создавать записи о затраченном времени сотрудника на задачу;
- получать список записей о затраченном времени сотрудника за указанный период.

Проект реализован в рамках тестового задания на Java и Spring Boot.

## Реализованный функционал

### Основной функционал
- создание задачи;
- получение задачи по ID;
- изменение статуса задачи;
- создание записи о затраченном времени сотрудника на задачу;
- получение записей о затраченном времени сотрудника за определенный период.

### Дополнительно
- документация API через Swagger / SpringDoc OpenAPI;
- валидация входных DTO через Bean Validation;
- централизованная обработка исключений через `@RestControllerAdvice`;
- Bearer Authentication на основе JWT;
- unit-тесты сервисного слоя;
- интеграционные тесты DAO-слоя с PostgreSQL через Testcontainers.

## Стек технологий

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Security
- MyBatis
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- Testcontainers
- SpringDoc OpenAPI
- JWT (JJWT)

## Структура проекта

```text
src/main/java/ru/testcdek/tasktimetracker
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── exception
├── mapper
├── model
├── security
├── service
└── TasktimetrackerApplication.java
```

## Сущности

### Task
- `id`
- `title`
- `description`
- `status` (`NEW`, `IN_PROGRESS`, `DONE`)

### TimeRecord
- `id`
- `employeeId`
- `taskId`
- `startTime`
- `endTime`
- `workDescription`

### AppUser
- `id`
- `username`
- `password`
- `role`

## API эндпоинты

### Аутентификация

#### `POST /api/auth/login`
Получение JWT-токена.

Пример запроса:

```json
{
  "username": "nikita",
  "password": "password"
}
```

Пример ответа:

```json
{
  "token": "jwt-token"
}
```

---

### Tasks

#### `POST /api/tasks`
Создать задачу.

Пример запроса:

```json
{
  "title": "Implement REST API",
  "description": "Create controllers and services"
}
```

Пример ответа:

```json
{
  "id": 1,
  "title": "Implement REST API",
  "description": "Create controllers and services",
  "status": "NEW"
}
```

#### `GET /api/tasks/{id}`
Получить задачу по ID.

Пример ответа:

```json
{
  "id": 1,
  "title": "Implement REST API",
  "description": "Create controllers and services",
  "status": "NEW"
}
```

#### `PATCH /api/tasks/{id}/status`
Изменить статус задачи.

Пример запроса:

```json
{
  "status": "IN_PROGRESS"
}
```

Пример ответа:

```json
{
  "id": 1,
  "title": "Implement REST API",
  "description": "Create controllers and services",
  "status": "IN_PROGRESS"
}
```

---

### Time Records

#### `POST /api/time-records`
Создать запись о затраченном времени.

Пример запроса:

```json
{
  "employeeId": 101,
  "taskId": 1,
  "startTime": "2026-04-21T10:00:00",
  "endTime": "2026-04-21T12:00:00",
  "workDescription": "Implemented service layer"
}
```

Пример ответа:

```json
{
  "id": 1,
  "employeeId": 101,
  "taskId": 1,
  "startTime": "2026-04-21T10:00:00",
  "endTime": "2026-04-21T12:00:00",
  "workDescription": "Implemented service layer"
}
```

#### `GET /api/time-records?employeeId=101&startTime=2026-04-21T00:00:00&endTime=2026-04-21T23:59:59`
Получить записи сотрудника за период.

Пример ответа:

```json
[
  {
    "id": 1,
    "employeeId": 101,
    "taskId": 1,
    "startTime": "2026-04-21T10:00:00",
    "endTime": "2026-04-21T12:00:00",
    "workDescription": "Implemented service layer"
  }
]
```

## Авторизация

Все основные эндпоинты, кроме логина и Swagger, защищены JWT.

Для доступа к защищенным эндпоинтам необходимо:
1. выполнить запрос на `/api/auth/login`;
2. получить токен;
3. передавать его в заголовке:

```http
Authorization: Bearer <jwt-token>
```

## Swagger

После запуска приложения документация доступна по адресу:

```text
http://localhost:8080/swagger-ui.html
```

или

```text
http://localhost:8080/swagger-ui/index.html
```

## Настройка PostgreSQL

Необходимо убедиться, что PostgreSQL запущен.

Создание базы данных:

```sql
CREATE DATABASE task_time_tracker;
```

Параметры подключения задаются в `src/main/resources/application.properties`.

Пример:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_time_tracker
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

При старте приложения выполняется `schema.sql`, который создает необходимые таблицы.

## Сборка и запуск приложения

### 1. Клонирование репозитория

```bash
git clone https://github.com/NikitaPodzhidaev/TaskTimeTracker.git
cd tasktimetracker
```

### 2. Сборка проекта

```bash
./mvnw clean package -DskipTests
```

### 3. Запуск приложения

```bash
./mvnw spring-boot:run
```

После запуска приложение будет доступно по адресу:

```text
http://localhost:8080
```

## Запуск тестов

### Все тесты

```bash
./mvnw clean test
```

### Только unit-тесты

```bash
./mvnw -Dtest=TaskServiceImplTest,TimeRecordServiceImplTest test
```

### Только integration tests

```bash
./mvnw -Dtest=TaskMapperIntegrationTest,TimeRecordMapperIntegrationTest test
```

## Требования для integration tests

Для запуска интеграционных тестов необходимо:
- установленный и запущенный Docker;
- доступ текущего пользователя к Docker daemon.

Проверка:

```bash
docker ps
```

## Проверка работоспособности

Сервис можно проверить двумя способами:
1. через Swagger UI;
2. через Postman collection.

Рекомендуемый порядок проверки:
1. `POST /api/auth/login`
2. `POST /api/tasks`
3. `GET /api/tasks/{id}`
4. `PATCH /api/tasks/{id}/status`
5. `POST /api/time-records`
6. `GET /api/time-records`

## Postman collection

В проект приложены файлы для проверки API через Postman:

- `docs/postman/task_time_tracker_postman_collection.json`
- `docs/postman/task_time_tracker_local_environment.json`

Для проверки:
1. импортируйте оба файла в Postman;
2. выберите environment `Task Time Tracker Local`;
3. выполните запросы по порядку:
    - `Auth - Login`
    - `Tasks - Create Task`
    - `Tasks - Get Task By Id`
    - `Tasks - Update Task Status`
    - `Time Records - Create`
    - `Time Records - Get By Employee And Period`
## Обработка ошибок

В проекте реализована централизованная обработка ошибок через `@RestControllerAdvice`.

Поддерживаются:
- ошибки валидации DTO (`400 Bad Request`);
- некорректные enum-значения (`400 Bad Request`);
- отсутствие задачи (`404 Not Found`);
- ошибки авторизации (`401 Unauthorized`);
- прочие ошибки (`500 Internal Server Error`).


Примеры взаимодействия с ИИ будут приложены в отдельной папке `docs/` и файле ai-prompts.md

## Итог

Проект реализует требуемый по ТЗ функционал и дополнительно включает:
- Swagger / OpenAPI;
- Bean Validation;
- централизованную обработку ошибок;
- JWT-аутентификацию;
- unit-тесты;
- интеграционные тесты DAO-слоя через Testcontainers.
