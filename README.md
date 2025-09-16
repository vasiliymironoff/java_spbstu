# Task Manager
## Task
### Step 1 (branch 1). Basic Rest API with In-Memory Storage 
- Learn basic differences between Gradle and Maven
- Create a Spring Boot project using start.spring.io
- Implement a simple TaskController with endpoints (GET, POST, DELETE)
- Implement a simple UserController with endpoints (GET, POST)
- Implement a simple NotificationController with endpoints (2x GET)
- Implement separate classes for each controller and service!
- Store tasks in a List or Map (no database yet!!)
- Return JSON responses with adequate http codes

### Step 2 (branch 2). Write unit-tests
- Using JUnit or TestNG write unit-tests for your application

### Step 3 (branch 3). In-Memory Database (H2) 
- Add H2 database as an in-memory database
- Configure Spring Data JPA
- Convert in-memory HashMap storage to a database-backed repository
- Implement Repository for all services using Spring Data JPA

### Step 4 (branch 4). Add Docker Support
- Write a Dockerfile for the Spring Boot application
- Use Docker Compose to start the database and the app together
- Test the application running in containers

### Step 5 (branch 5). Switch to a database (PostgreSQL, MongoDB, Cassandra, InfluxDB, Firebase, Clickhouse)
- Replace H2 with PostgreSQL
- Update application.properties for PostgreSQL connection
- Use Flyway for database migration
- Write new tests. Use mockito to mock responses from the database

### Step 6 (branch 6): Implement Caching (Redis)
- Use Spring Cache.
- Cache task retrieval to improve performance
- Search for entries in Caching database, and if not found, then search in database
- Set timeouts for values

### Step 7 (branch 7): Impelement Messaging (Kafka)
- Set up RabbitMQ or Kafka or any other message broker (RabbitMQ is a bit simpler, Kafka is faster)
- Publish a message when a new task is created
- Remake the Notification service to receive updates !! ONLY !! from the message broker
- Create a listener to process messages asynchronously

### Step 8 (branch 8): Add Scheduling & Async Tasks
- Use @Scheduled to periodically check for overdue tasks
- Use @Async for background processing

## Important conditions
- Use interfaces and separate classes for storing data
- The data storage implementation should be selected with profiles
- Use proper OOP and write tests
- Each task should on github and its own branch and/or pull request


## Step 1

### Basic differences between Gradle and Maven

Gradle and Maven are both build automation tools used in Java projects, but they differ significantly in several aspects. Gradle is a flexible build automation tool that allows for complex configurations and uses a domain-specific language (DSL) based on Groovy or Kotlin. This flexibility enables dynamic and expressive build scripts. In contrast, Maven is primarily a project management tool that emphasizes convention over configuration, using XML for its configuration, which can be more verbose.

In terms of performance, Gradle supports incremental builds and caching, leading to faster build times, especially in larger projects. Maven typically performs full builds every time, which can be slower. When it comes to dependency management, Gradle employs a dynamic resolution model, allowing for more flexibility, while Maven uses a static resolution model, which is simpler but less adaptable.

Gradle has a highly customizable plugin system, enabling users to create and utilize a wide range of plugins, whereas Maven has a more limited set of predefined plugins. For multi-project builds, Gradle makes it easier to manage complex structures, while Maven can be cumbersome to configure for multi-module projects.

The learning curve for Gradle is steeper due to its flexibility and DSL, while Maven is generally easier for beginners to grasp because of its straightforward conventions. Finally, Gradle has a growing community and is increasingly adopted in modern development environments, while Maven has a well-established community with extensive documentation and resources.

### API Endpoint

#### UserController (GET, POST)
| Method | Path                | Description  |
|--------|---------------------|--------------|
| GET    | /api/users/login    | Get users    |
| POST   | /api/users/register | Create users |


#### TaskController (GET, POST, DELETE)
| Method | Path                             | Description                      |
|--------|----------------------------------|----------------------------------|
| GET    | /api/tasks/user/{userId}         | Get all task by userId           |
| GET    | /api/tasks/user/{userId}/pending | Get only pending tasks by userId | 
| POST   | /api/tasks                       | Create task                      |
| DELETE | /api/tasks/{taskId}              | Delete task by taskId            |


#### NotificationController (GET)

| Method | Path                                  | Description                              |
|--------|---------------------------------------|------------------------------------------|
| GET    | /api/notifications/{userId}           | Get notifications by userId              |
| GET    | /api/notifications/{userId}}/pending  | Get notifications by userId only pending |
| POST   | /api/notifications                    | Create noficiation                       |


### Run

Run `./gradlew bootRun --info`

## Step 2

Tests run: `./gradlew test`

Count tests: 39 items

Tests for controllers: 
- NotificationControllerTest (5 tests)
- TaskControllerTest (5 tests)
- UserControllerTest (4 tests)

Tests for repository:
- NotificationRepositoryTest (4)
- TaskRepositoryTest (7)
- UserRepositoryTest (4)

Tests for service:
- NotificationServiceTest (4)
- TaskServiceTest (4)
- UserServiceTest (5)

## Step 3

Run `./gradlew bootRun --info`

ULR for console: http://localhost:8080/h2-console


## Step 4
Command for running: 

`docker-compose up`

