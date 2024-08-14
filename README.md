# Student Management System

## Project Overview

This project is a Student Management System developed using **Spring Boot 3.3.0** and **Java 21**. It allows users to perform CRUD operations on student records, including creating, listing, updating, and deleting students. The application also supports data validation, error handling, and unit testing.

### Key Features:
- CRUD operations for students
- Data validation and error handling
- Integration with PostgreSQL
- Unit testing with JUnit and Mockito

## Technologies Used

- **Java 21**: Utilizes new features like Pattern Matching and improvements to the Stream API.
- **Spring Boot 3.3.0**: Framework used to create the REST API.
- **PostgreSQL**: Database for persistence.
- **JUnit & Mockito**: For unit testing.

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/dhamursv/student-management.git
   cd student-management
2. **Configure PostgreSQL:**
   Ensure PostgreSQL is installed and running. Create a database for the application.
   ```bash
    Name: student_management_db
    User: postgres
3. **Application Properties:**
   Look at the `application.properties` file under `src/main/resources` and modify it according to your needs.
4. **Build and Run the Application:**
   Using Maven:
    ```bash
    mvn clean install
    mvn spring-boot:run
  
  The application will run on port `8080`. Base URL: `http://localhost:8080`
  
### Usage
### API Endpoints

- GET /students: Retrieve a list of all students.
- GET /students/names: Retrieve student names and IDs.
- GET /students/{id}: Retrieve a student by ID.
- POST /students: Create a new student.
- PUT /students/{id}: Update an existing student.
- DELETE /students/{id}: Delete a student by ID.

For more information look at swagger UI: `http://localhost:8080/swagger-ui/index.html`

### Testing
Run unit tests using Maven:

```bash
mvn test
