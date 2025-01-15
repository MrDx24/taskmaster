# TaskMaster - Task Management and Collaboration Platform

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)

TaskMaster is a robust task management and team collaboration platform built with Spring Boot, offering comprehensive features for task tracking, team collaboration, and project management.

## Features

### User Management
- Secure user authentication and authorization
- JWT-based session management
- User registration and profile management
- Secure password hashing implementation

### Task Management
- Create, read, update, and delete tasks
- Advanced task filtering and search capabilities
- Task status tracking
- Due date management
- Task assignment and reassignment

### Team Collaboration
- Team and project creation
- Member invitations and management
- Task comments and attachments
- Real-time collaboration features

## Technology Stack

- **Backend Framework:** Spring Boot
- **Database:** PostgreSQL (Containerized)
- **Authentication:** JWT (JSON Web Tokens)
- **Build Tool:** Gradle
- **Containerization:** Docker

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Gradle
- PostgreSQL (Docker container)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/MrDx24/taskmaster.git
cd taskmaster
```

### 2. Database Setup

Start the PostgreSQL container using Docker:

```bash
docker-compose up -d
```

### 3. Configure Application

Update `application.properties` with your database credentials and JWT configuration.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmaster
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build and Run

Using Gradle:
```bash
./gradlew build
./gradlew bootRun
```

## API Endpoints

### Authentication
- `POST /user/register` - User registration
- `POST /user/authenticate` - User login
- `POST /user/profile/view` - User profile view
- `POST /user/profile/update` - User profile update
- `POST /user/logout` - User logout

### User Management
- `GET /user/profile/view` - Get user profile
- `PUT /user/profile/update` - Update user profile

### Task Management
- `POST /task/create` - Create new task
- `GET /task/assigned` - Get all tasks
- `PUT /task/status` - Update task status
- `GET /task/filter` - Filter tasks
- `DELETE /task/search` - Search tasks

### Project Management
- `POST /project/create` - Create new team
- `POST /project/addUser` - Add team member

## User Stories Implemented

1. User account creation and authentication
2. Secure login functionality
3. Profile management
4. Task creation and management
5. Task assignment and viewing
6. Task completion marking
7. Team member task assignment
8. Task filtering by status
9. Task search functionality
10. Team collaboration features
11. Team/project creation and member invitation
12. Secure logout implementation

## Security

- Implemented JWT-based authentication
- Password hashing using secure algorithms
- Role-based access control
- Input validation and sanitization
- Secure session management

## Acknowledgments

- Spring Boot documentation
- PostgreSQL documentation
- Docker documentation
