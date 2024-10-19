# Agile Project Management System (Backend)

This project is a **Backend API** for an Agile Project Management system, designed to help teams manage projects, sprints, and tasks efficiently. It supports role-based access control (RBAC) with specific roles such as **Project Manager**, **Scrum Master**, **Product Owner**, **Developer**, and **Tester**. The system allows project managers to create projects, add users, and assign them roles, while Scrum Masters can create sprints and manage task assignments. The project implements **Clean Architecture**, ensuring maintainability, scalability, and testability.

## Features

- **JWT Authentication & Role-Based Authorization**
  - Secure authentication using JSON Web Tokens (JWT).
  - Roles: **Project Manager**, **Scrum Master**, **Product Owner**, **Developer**, and **Tester**.
  - Role-based access control for managing project-related activities.

- **Project Management**
  - Project creation by the Project Manager.
  - Adding and managing users within a project.
  - Scrum Master can create sprints, assign tasks to Developers and Testers.

- **Task & Sprint Management**
  - Sprint creation by Scrum Master, task assignment to Developers and Testers.
  - Ability to track tasks within each sprint.

- **Testing & Reliability**
  - Comprehensive **Unit** and **Integration Tests** using **JUnit** and **Mockito**.
  - Ensures high code reliability and robustness with role-based API access testing.

## Technologies Used

- **Java 17**
- **Spring Boot 3**
- **PostgreSQL** (Database)
- **JWT** (Authentication & Authorization)
- **MapStruct** (DTO Mapping)
- **JUnit & Mockito** (Testing)
- **Swagger** (API Documentation)
- **Clean Architecture**

## API Documentation

The API is documented using **Swagger**, which allows interactive API testing and exploration. After running the project, access the Swagger UI by visiting:

http://localhost:8080/swagger-ui/index.html

## Project Structure

This project follows **Clean Architecture**, ensuring a separation of concerns across layers.

- **Application Layer**: Contains interfaces for services and repositories.
- **Domain Layer**: Defines core business entities such as `User`, `Project`, `Sprint`, and `Task`.
- **Infrastructure Layer**: Contains actual implementations of repositories and services.
- **API Layer**: Handles all incoming HTTP requests and maps them to the appropriate service calls.

## How to Run

### Prerequisites

- Java 17
- PostgreSQL (or any preferred database)
- Maven

### Setup Instructions

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-repository/agile-project-management-backend.git
   cd agile-project-management-backend
   ```

2. **Database Setup**:

   Set up a PostgreSQL database locally or on the cloud.

   Update the database credentials in `src/main/resources/application.yml`:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/your_database
       username: your_username
       password: your_password
     jpa:
       hibernate:
         ddl-auto: update
   ```

3. **Build the project**:

   ```bash
   mvn clean install
   ```

4. **Run the application**:

   ```bash
   mvn spring-boot:run
   ```

   The API will be accessible at http://localhost:8080.

## Running Tests

This project includes comprehensive unit and integration tests to ensure proper functionality.

To run the tests, use the following command:

```bash
mvn test
```

## API Endpoints

### Authentication & Authorization
- `POST /api/users/register`: Register a new user.
- `POST /api/users/login`: User login and token generation.
- `POST /api/users/refresh-token`: Refresh JWT access token.

### Project Management
- `POST /api/projects`: Create a new project (Project Manager only).
- `GET /api/projects/creator/{email}`: Get all projects created by the user.
- `GET /api/projects/user/{email}`: Get all projects a user is involved in.
- `POST /api/projects/{projectId}/add-user`: Add a user to the project by email.

### Sprint & Task Management
- `POST /api/sprints`: Create a new sprint (Scrum Master only).
- `GET /api/sprints/project/{projectId}`: Get all sprints for a project.

## Project Roles

The system defines five main roles, each with different permissions:

- **Project Manager**: Creates and manages projects. Adds users to projects.
- **Scrum Master**: Creates sprints and assigns tasks to developers and testers.
- **Product Owner**: Oversees the project's progress but does not manage users or sprints.
- **Developer**: Works on tasks assigned within a sprint.
- **Tester**: Tests tasks assigned within a sprint.

## Security

- JWT-based authentication: Secured API endpoints, with access restricted based on roles.
- Role-Based Authorization: Different roles have specific access levels for different API endpoints.

## Contributions

Contributions, issues, and feature requests are welcome! Feel free to check the issues page.

## License

This project is licensed under the MIT License.
