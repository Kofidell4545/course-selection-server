# Course Selection System

A comprehensive course selection and enrollment system for undergraduate students built with Spring Boot, PostgreSQL, and JWT authentication.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Project Configuration](#project-configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Authentication](#authentication)
- [Sample Data](#sample-data)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)

## Prerequisites

### 1. Install Java 17

#### macOS (using Homebrew)
```bash
brew install openjdk@17
```

#### macOS (Manual Installation)
1. Download Java 17 from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://adoptium.net/)
2. Install the downloaded package
3. Verify installation:
```bash
java -version
```
You should see: `openjdk version "17.x.x"`

#### Set JAVA_HOME (if needed)
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
```

### 2. Install PostgreSQL

#### macOS (using Homebrew)
```bash
brew install postgresql@14
brew services start postgresql@14
```

#### macOS (Manual Installation)
1. Download PostgreSQL from [postgresql.org](https://www.postgresql.org/download/macosx/)
2. Install the downloaded package
3. Start PostgreSQL service

#### Verify PostgreSQL Installation
```bash
psql --version
```

### 3. Install Maven (Optional - Spring Boot includes Maven Wrapper)

#### macOS (using Homebrew)
```bash
brew install maven
```

#### Verify Maven Installation
```bash
mvn -version
```

**Note:** The project includes Maven Wrapper (`mvnw`), so Maven installation is optional.

## Installation

### 1. Clone or Navigate to Project Directory
```bash
cd /Users/user/Downloads/course-allocation
```

### 2. Verify Project Structure
Ensure you have the following structure:
```
course-allocation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── pom.xml
└── README.md
```

## Database Setup

### 1. Start PostgreSQL Service

#### macOS (Homebrew)
```bash
brew services start postgresql@14
```

#### Or start manually
```bash
pg_ctl -D /usr/local/var/postgres start
```

### 2. Create Database

Connect to PostgreSQL:
```bash
psql -U postgres
```

If you get a connection error, try:
```bash
psql postgres
```

Create the database:
```sql
CREATE DATABASE course_allocation;
```

Verify database creation:
```sql
\l
```

You should see `course_allocation` in the list.

Exit PostgreSQL:
```sql
\q
```

### 3. Verify Database Connection

Test connection:
```bash
psql -U postgres -d course_allocation
```

If successful, you'll see the PostgreSQL prompt. Exit with `\q`.

## Project Configuration

### 1. Update Database Credentials (if needed)

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/course_allocation
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Important:** Update the `username` and `password` if your PostgreSQL setup uses different credentials.

### 2. JWT Configuration

The JWT secret and expiration are already configured in `application.properties`:

```properties
jwt.secret=mySecretKeyForJWTTokenGenerationAndValidationInCourseAllocationSystem2024
jwt.expiration=86400000
```

**Security Note:** For production, change the JWT secret to a strong, randomly generated key.

## Running the Application

### Method 1: Using Maven Wrapper (Recommended)

```bash
./mvnw spring-boot:run
```

On Windows:
```bash
mvnw.cmd spring-boot:run
```

### Method 2: Using Maven (if installed)

```bash
mvn spring-boot:run
```

### Method 3: Using IDE (IntelliJ IDEA / Eclipse)

1. Open the project in your IDE
2. Wait for Maven dependencies to download
3. Locate `CourseAllocationApplication.java`
4. Right-click and select "Run" or "Debug"

### 4. Verify Application is Running

You should see output like:
```
Started CourseAllocationApplication in X.XXX seconds
```

The application runs on: `http://localhost:8080`

## API Documentation

### Access Swagger UI

Once the application is running, open your browser and navigate to:

```
http://localhost:8080/docs
```

You'll see the Swagger UI with all available API endpoints.

### API Documentation JSON

The OpenAPI JSON specification is available at:
```
http://localhost:8080/v3/api-docs
```

## Authentication

### 1. Login to Get JWT Token

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "studentId": "STU001",
  "pin": "1234"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "studentId": 1,
    "studentIdNumber": "STU001",
    "firstName": "Kwame",
    "lastName": "Asante",
    "email": "kwame.asante@university.edu",
    "department": "Computer Science",
    "year": 2,
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### 2. Using Token in Swagger UI

1. Copy the `token` value from the login response
2. Click the **"Authorize"** button (lock icon) at the top right of Swagger UI
3. Paste **only the token** (without "Bearer" prefix)
4. Click **"Authorize"**
5. All protected endpoints will now work

### 3. Using Token in API Requests

Include the token in the Authorization header:

```
Authorization: Bearer <your-token-here>
```

## Sample Data

The application automatically seeds sample data on first startup:

### Students (Pre-loaded)

| Student ID | Name | Department | Year | PIN |
|------------|------|------------|------|-----|
| STU001 | Kwame Asante | Computer Science | 2 | 1234 |
| STU002 | Ama Mensah | Computer Science | 3 | 5678 |
| STU003 | Kofi Osei | Mathematics | 1 | 9012 |
| STU004 | Akosua Boateng | Computer Science | 2 | 3456 |

### Courses (Pre-loaded)

- **CS201** - Data Structures and Algorithms (Level 2, 3 credits)
- **CS301** - Database Systems (Level 3, 3 credits)
- **CS202** - Object-Oriented Programming (Level 2, 4 credits)
- **MATH101** - Calculus I (Level 1, 4 credits)
- **CS401** - Software Engineering (Level 4, 3 credits)
- **CS302** - Operating Systems (Level 3, 3 credits)

### Semester

- **Fall 2024** (Active) - September 1, 2024 to December 15, 2024

## API Endpoints

### Authentication
- `POST /api/auth/login` - Student login (Public)

### Student Management (Protected)
- `POST /api/students` - Create student
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `GET /api/students/student-id/{studentId}` - Get by student ID
- `GET /api/students/department/{department}` - Get by department
- `GET /api/students/year/{year}` - Get by year
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

### Course Management (Protected)
- `POST /api/courses` - Create course
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/semester/{semesterId}` - Get by semester
- `GET /api/courses/department/{department}` - Get by department
- `GET /api/courses/instructor/{instructor}` - Get by instructor
- `GET /api/courses/search?name={name}` - Search courses
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course

### Semester Management (Protected)
- `POST /api/semesters` - Create semester
- `GET /api/semesters` - Get all semesters
- `GET /api/semesters/{id}` - Get semester by ID
- `GET /api/semesters/active` - Get active semester
- `GET /api/semesters/active/list` - Get all active semesters
- `PUT /api/semesters/{id}` - Update semester
- `DELETE /api/semesters/{id}` - Delete semester

### Course Selection (Student Endpoints - Protected)
- `GET /api/student/courses/available?studentId={id}&semesterId={id}` - Get available courses
- `POST /api/student/courses/select?studentId={id}` - Select/enroll in course
- `DELETE /api/student/courses/drop/{enrollmentId}?studentId={id}` - Drop course
- `GET /api/student/courses/summary?studentId={id}&semesterId={id}` - Get enrollment summary

### Enrollment Management (Protected)
- `POST /api/enrollments` - Create enrollment
- `GET /api/enrollments` - Get all enrollments
- `GET /api/enrollments/{id}` - Get enrollment by ID
- `GET /api/enrollments/student/{studentId}` - Get by student
- `GET /api/enrollments/course/{courseId}` - Get by course
- `PUT /api/enrollments/{id}` - Update enrollment
- `DELETE /api/enrollments/{id}` - Delete enrollment

## Testing

### Quick Test Flow

1. **Start the application**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Access Swagger UI**
   - Open: `http://localhost:8080/docs`

3. **Login**
   - Use `POST /api/auth/login`
   - Student ID: `STU001`
   - PIN: `1234`
   - Copy the token from response

4. **Authorize in Swagger**
   - Click "Authorize" button
   - Paste token
   - Click "Authorize"

5. **Get Available Courses**
   - Use `GET /api/student/courses/available`
   - Parameters: `studentId=1`, `semesterId=1`

6. **Select a Course**
   - Use `POST /api/student/courses/select`
   - Parameter: `studentId=1`
   - Body: `{"courseId": 1}`

7. **View Enrollment Summary**
   - Use `GET /api/student/courses/summary`
   - Parameters: `studentId=1`, `semesterId=1`

## Troubleshooting

### Database Connection Issues

**Error:** `Connection refused` or `Failed to determine a suitable driver class`

**Solution:**
1. Verify PostgreSQL is running:
   ```bash
   brew services list
   ```
2. Check database exists:
   ```bash
   psql -U postgres -l
   ```
3. Verify credentials in `application.properties`

### Port Already in Use

**Error:** `Port 8080 is already in use`

**Solution:**
1. Find process using port 8080:
   ```bash
   lsof -i :8080
   ```
2. Kill the process or change port in `application.properties`:
   ```properties
   server.port=8081
   ```

### JWT Token Issues

**Error:** `Invalid or expired token`

**Solution:**
1. Token expires after 24 hours - login again to get a new token
2. Ensure you're pasting the full token (it's a long string)
3. Don't include "Bearer" prefix in Swagger UI

### Swagger Not Loading

**Error:** `Failed to load API definition` or 500 error

**Solution:**
1. Ensure application started successfully
2. Check application logs for errors
3. Try accessing: `http://localhost:8080/v3/api-docs` directly
4. Clear browser cache and try again

## Project Structure

```
course-allocation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/courseallocation/course_allocation/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST controllers
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       ├── exception/       # Exception handlers
│   │   │       ├── model/           # JPA entities
│   │   │       ├── repository/      # Data access layer
│   │   │       └── service/         # Business logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Technology Stack

- **Java 17** - Programming language
- **Spring Boot 3.5.7** - Framework
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Database
- **JWT (jjwt 0.12.5)** - Token-based authentication
- **Swagger/OpenAPI** - API documentation
- **Lombok** - Boilerplate reduction
- **Maven** - Build tool

## Features

- Student authentication with JWT tokens
- Course filtering by program and year level
- Course selection with credit and course limits
- Enrollment management
- Semester management
- Comprehensive API documentation
- Data validation
- Error handling
- Sample data seeding

## Security Features

- JWT-based authentication
- Token expiration (24 hours)
- Role-based access control
- Protected endpoints
- Secure password handling

## Credit and Course Limits

- **Maximum Credits:** 21 per semester
- **Maximum Courses:** 7 per semester
- **Minimum Credits:** 12 per semester (recommended)

## Support

For issues or questions, please check:
1. Application logs in the console
2. Swagger UI documentation at `/docs`
3. Database connection status
4. JWT token validity

## License

This project is for educational purposes.

---

**Happy Coding! - Joe**

