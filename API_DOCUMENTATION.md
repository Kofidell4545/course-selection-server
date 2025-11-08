# Course Selection System - API Documentation

## Table of Contents
- [Overview](#overview)
- [Base URL](#base-url)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
  - [Authentication](#authentication-endpoints)
  - [Student Management](#student-management)
  - [Course Management](#course-management)
  - [Semester Management](#semester-management)
  - [Enrollment Management](#enrollment-management)
  - [Course Selection](#course-selection)
- [Data Models](#data-models)
- [Error Handling](#error-handling)

---

## Overview

The Course Selection System API provides a comprehensive solution for managing student course registrations, enrollments, and academic semester operations. Built with Spring Boot 3.5.7 and documented using OpenAPI 3.0 (Swagger).

### Technology Stack
- **Framework**: Spring Boot 3.5.7
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Tokens)
- **Documentation**: SpringDoc OpenAPI 3 (Swagger UI)
- **Java Version**: 17

---

## Base URL

```
http://localhost:8080/api
```

### Swagger UI
Access the interactive API documentation at:
```
http://localhost:8080/docs
```

### OpenAPI Specification
```
http://localhost:8080/v3/api-docs
```

---

## Authentication

The API uses JWT (JSON Web Token) for authentication. After successful login, include the JWT token in the Authorization header for protected endpoints.

### Headers
```
Authorization: Bearer <your-jwt-token>
```

### Token Configuration
- **Expiration**: 24 hours (86400000 ms)
- **Algorithm**: HS256

---

## API Endpoints

### Authentication Endpoints

#### 1. Student Login
Authenticate a student using their student ID and PIN.

**Endpoint**: `POST /api/auth/login`

**Request Body**:
```json
{
  "studentId": "string",
  "pin": "string"
}
```

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "studentId": "string",
    "name": "string",
    "department": "string",
    "year": 1
  }
}
```

**Error Responses**:
- `401 Unauthorized`: Invalid credentials
- `400 Bad Request`: Missing or invalid request data

---

### Student Management

#### 1. Create Student
Register a new student in the system.

**Endpoint**: `POST /api/students`

**Request Body**:
```json
{
  "studentId": "string",
  "name": "string",
  "email": "string",
  "department": "string",
  "year": 1,
  "pin": "string"
}
```

**Response** (201 Created):
```json
{
  "success": true,
  "message": "Student created successfully",
  "data": {
    "id": 1,
    "studentId": "string",
    "name": "string",
    "email": "string",
    "department": "string",
    "year": 1
  }
}
```

#### 2. Get Student by ID
Retrieve student information by their database ID.

**Endpoint**: `GET /api/students/{id}`

**Path Parameters**:
- `id` (Long): Student database ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Student retrieved successfully",
  "data": {
    "id": 1,
    "studentId": "string",
    "name": "string",
    "email": "string",
    "department": "string",
    "year": 1
  }
}
```

#### 3. Get Student by Student ID
Retrieve student information by their student ID.

**Endpoint**: `GET /api/students/student-id/{studentId}`

**Path Parameters**:
- `studentId` (String): Student ID

**Response** (200 OK): Same as Get Student by ID

#### 4. Get All Students
Retrieve a list of all students.

**Endpoint**: `GET /api/students`

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Students retrieved successfully",
  "data": [
    {
      "id": 1,
      "studentId": "string",
      "name": "string",
      "email": "string",
      "department": "string",
      "year": 1
    }
  ]
}
```

#### 5. Get Students by Department
Retrieve all students in a specific department.

**Endpoint**: `GET /api/students/department/{department}`

**Path Parameters**:
- `department` (String): Department name

**Response** (200 OK): List of students

#### 6. Get Students by Year
Retrieve all students in a specific year.

**Endpoint**: `GET /api/students/year/{year}`

**Path Parameters**:
- `year` (Integer): Academic year (1-4)

**Response** (200 OK): List of students

#### 7. Update Student
Update student information.

**Endpoint**: `PUT /api/students/{id}`

**Path Parameters**:
- `id` (Long): Student database ID

**Request Body**: Same as Create Student

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Student updated successfully",
  "data": { /* student data */ }
}
```

#### 8. Delete Student
Remove a student from the system.

**Endpoint**: `DELETE /api/students/{id}`

**Path Parameters**:
- `id` (Long): Student database ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Student deleted successfully",
  "data": null
}
```

---

### Course Management

#### 1. Create Course
Register a new course in the system.

**Endpoint**: `POST /api/courses`

**Request Body**:
```json
{
  "courseCode": "string",
  "courseName": "string",
  "credits": 3,
  "department": "string",
  "instructor": "string",
  "capacity": 50,
  "semesterId": 1,
  "yearLevel": 1
}
```

**Response** (201 Created):
```json
{
  "success": true,
  "message": "Course created successfully",
  "data": {
    "id": 1,
    "courseCode": "string",
    "courseName": "string",
    "credits": 3,
    "department": "string",
    "instructor": "string",
    "capacity": 50,
    "enrolledCount": 0,
    "semesterId": 1,
    "yearLevel": 1
  }
}
```

#### 2. Get Course by ID
Retrieve course information by ID.

**Endpoint**: `GET /api/courses/{id}`

**Path Parameters**:
- `id` (Long): Course ID

**Response** (200 OK): Course data

#### 3. Get All Courses
Retrieve a list of all courses.

**Endpoint**: `GET /api/courses`

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Courses retrieved successfully",
  "data": [ /* array of courses */ ]
}
```

#### 4. Get Courses by Semester
Retrieve all courses for a specific semester.

**Endpoint**: `GET /api/courses/semester/{semesterId}`

**Path Parameters**:
- `semesterId` (Long): Semester ID

**Response** (200 OK): List of courses

#### 5. Get Courses by Department
Retrieve all courses in a specific department.

**Endpoint**: `GET /api/courses/department/{department}`

**Path Parameters**:
- `department` (String): Department name

**Response** (200 OK): List of courses

#### 6. Get Courses by Instructor
Retrieve all courses taught by a specific instructor.

**Endpoint**: `GET /api/courses/instructor/{instructor}`

**Path Parameters**:
- `instructor` (String): Instructor name

**Response** (200 OK): List of courses

#### 7. Search Courses by Name
Search for courses by course name.

**Endpoint**: `GET /api/courses/search`

**Query Parameters**:
- `name` (String): Course name to search

**Response** (200 OK): List of matching courses

#### 8. Update Course
Update course information.

**Endpoint**: `PUT /api/courses/{id}`

**Path Parameters**:
- `id` (Long): Course ID

**Request Body**: Same as Create Course

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Course updated successfully",
  "data": { /* course data */ }
}
```

#### 9. Delete Course
Remove a course from the system.

**Endpoint**: `DELETE /api/courses/{id}`

**Path Parameters**:
- `id` (Long): Course ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Course deleted successfully",
  "data": null
}
```

---

### Semester Management

#### 1. Create Semester
Register a new semester in the system.

**Endpoint**: `POST /api/semesters`

**Request Body**:
```json
{
  "name": "string",
  "startDate": "2024-01-01",
  "endDate": "2024-05-31",
  "isActive": true
}
```

**Response** (201 Created):
```json
{
  "success": true,
  "message": "Semester created successfully",
  "data": {
    "id": 1,
    "name": "string",
    "startDate": "2024-01-01",
    "endDate": "2024-05-31",
    "isActive": true
  }
}
```

#### 2. Get Semester by ID
Retrieve semester information by ID.

**Endpoint**: `GET /api/semesters/{id}`

**Path Parameters**:
- `id` (Long): Semester ID

**Response** (200 OK): Semester data

#### 3. Get Active Semester
Retrieve the currently active semester.

**Endpoint**: `GET /api/semesters/active`

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Active semester retrieved successfully",
  "data": { /* semester data */ }
}
```

#### 4. Get All Semesters
Retrieve a list of all semesters.

**Endpoint**: `GET /api/semesters`

**Response** (200 OK): List of semesters

#### 5. Get All Active Semesters
Retrieve a list of all active semesters.

**Endpoint**: `GET /api/semesters/active/list`

**Response** (200 OK): List of active semesters

#### 6. Update Semester
Update semester information.

**Endpoint**: `PUT /api/semesters/{id}`

**Path Parameters**:
- `id` (Long): Semester ID

**Request Body**: Same as Create Semester

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Semester updated successfully",
  "data": { /* semester data */ }
}
```

#### 7. Delete Semester
Remove a semester from the system.

**Endpoint**: `DELETE /api/semesters/{id}`

**Path Parameters**:
- `id` (Long): Semester ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Semester deleted successfully",
  "data": null
}
```

---

### Enrollment Management

#### 1. Create Enrollment
Enroll a student in a course.

**Endpoint**: `POST /api/enrollments`

**Request Body**:
```json
{
  "studentId": 1,
  "courseId": 1,
  "status": "ENROLLED"
}
```

**Response** (201 Created):
```json
{
  "success": true,
  "message": "Student enrolled in course successfully",
  "data": {
    "id": 1,
    "studentId": 1,
    "courseId": 1,
    "status": "ENROLLED",
    "enrollmentDate": "2024-01-15T10:30:00"
  }
}
```

#### 2. Get Enrollment by ID
Retrieve enrollment information by ID.

**Endpoint**: `GET /api/enrollments/{id}`

**Path Parameters**:
- `id` (Long): Enrollment ID

**Response** (200 OK): Enrollment data

#### 3. Get All Enrollments
Retrieve a list of all course enrollments.

**Endpoint**: `GET /api/enrollments`

**Response** (200 OK): List of enrollments

#### 4. Get Enrollments by Student
Retrieve all course enrollments for a specific student.

**Endpoint**: `GET /api/enrollments/student/{studentId}`

**Path Parameters**:
- `studentId` (Long): Student ID

**Response** (200 OK): List of student's enrollments

#### 5. Get Enrollments by Course
Retrieve all student enrollments for a specific course.

**Endpoint**: `GET /api/enrollments/course/{courseId}`

**Path Parameters**:
- `courseId` (Long): Course ID

**Response** (200 OK): List of course enrollments

#### 6. Update Enrollment
Update enrollment status.

**Endpoint**: `PUT /api/enrollments/{id}`

**Path Parameters**:
- `id` (Long): Enrollment ID

**Request Body**: Same as Create Enrollment

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Enrollment updated successfully",
  "data": { /* enrollment data */ }
}
```

#### 7. Delete Enrollment
Remove a student from a course.

**Endpoint**: `DELETE /api/enrollments/{id}`

**Path Parameters**:
- `id` (Long): Enrollment ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Student removed from course successfully",
  "data": null
}
```

---

### Course Selection

#### 1. Get Available Courses for Student
Retrieve courses available for the student based on their program and year level.

**Endpoint**: `GET /api/student/courses/available`

**Query Parameters**:
- `studentId` (Long): Student ID
- `semesterId` (Long): Semester ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Available courses retrieved successfully",
  "data": [
    {
      "id": 1,
      "courseCode": "CS101",
      "courseName": "Introduction to Computer Science",
      "credits": 3,
      "department": "Computer Science",
      "instructor": "Dr. Smith",
      "capacity": 50,
      "enrolledCount": 25,
      "semesterId": 1,
      "yearLevel": 1
    }
  ]
}
```

#### 2. Select a Course
Enroll student in a course with validation for credit and course limits.

**Endpoint**: `POST /api/student/courses/select`

**Query Parameters**:
- `studentId` (Long): Student ID

**Request Body**:
```json
{
  "courseId": 1
}
```

**Response** (201 Created):
```json
{
  "success": true,
  "message": "Course selected successfully",
  "data": {
    "id": 1,
    "studentId": 1,
    "courseId": 1,
    "status": "ENROLLED",
    "enrollmentDate": "2024-01-15T10:30:00"
  }
}
```

**Business Rules**:
- Maximum 6 courses per semester
- Maximum 21 credits per semester
- Course must have available capacity
- Student must be eligible for the course (correct year level)

**Error Responses**:
- `400 Bad Request`: Credit limit exceeded, course limit exceeded, or course full
- `404 Not Found`: Student or course not found

#### 3. Drop a Course
Remove a course from student's enrollment.

**Endpoint**: `DELETE /api/student/courses/drop/{enrollmentId}`

**Path Parameters**:
- `enrollmentId` (Long): Enrollment ID

**Query Parameters**:
- `studentId` (Long): Student ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Course dropped successfully",
  "data": null
}
```

#### 4. Get Student Course Summary
Retrieve student's enrollment summary including total credits and courses.

**Endpoint**: `GET /api/student/courses/summary`

**Query Parameters**:
- `studentId` (Long): Student ID
- `semesterId` (Long): Semester ID

**Response** (200 OK):
```json
{
  "success": true,
  "message": "Course summary retrieved successfully",
  "data": {
    "studentId": 1,
    "studentName": "John Doe",
    "semesterId": 1,
    "semesterName": "Fall 2024",
    "totalCourses": 5,
    "totalCredits": 15,
    "enrolledCourses": [
      {
        "id": 1,
        "courseCode": "CS101",
        "courseName": "Introduction to Computer Science",
        "credits": 3,
        "instructor": "Dr. Smith",
        "enrollmentDate": "2024-01-15T10:30:00"
      }
    ]
  }
}
```

---

## Data Models

### ApiResponse<T>
Generic response wrapper for all API responses.

```json
{
  "success": boolean,
  "message": "string",
  "data": T | null
}
```

### Student
```json
{
  "id": number,
  "studentId": "string",
  "name": "string",
  "email": "string",
  "department": "string",
  "year": number
}
```

### Course
```json
{
  "id": number,
  "courseCode": "string",
  "courseName": "string",
  "credits": number,
  "department": "string",
  "instructor": "string",
  "capacity": number,
  "enrolledCount": number,
  "semesterId": number,
  "yearLevel": number
}
```

### Semester
```json
{
  "id": number,
  "name": "string",
  "startDate": "date",
  "endDate": "date",
  "isActive": boolean
}
```

### Enrollment
```json
{
  "id": number,
  "studentId": number,
  "courseId": number,
  "status": "string",
  "enrollmentDate": "datetime"
}
```

### LoginRequest
```json
{
  "studentId": "string",
  "pin": "string"
}
```

### LoginResponse
```json
{
  "token": "string",
  "studentId": "string",
  "name": "string",
  "department": "string",
  "year": number
}
```

---

## Error Handling

### Standard Error Response
```json
{
  "success": false,
  "message": "Error description",
  "data": null
}
```

### HTTP Status Codes

| Status Code | Description |
|------------|-------------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 400 | Bad Request - Invalid request data or business rule violation |
| 401 | Unauthorized - Authentication required or failed |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource not found |
| 409 | Conflict - Resource already exists |
| 500 | Internal Server Error - Server error |

### Common Error Scenarios

#### 1. Validation Errors
```json
{
  "success": false,
  "message": "Validation failed: Field 'email' is required",
  "data": null
}
```

#### 2. Resource Not Found
```json
{
  "success": false,
  "message": "Student with ID 123 not found",
  "data": null
}
```

#### 3. Business Rule Violations
```json
{
  "success": false,
  "message": "Cannot enroll: Maximum credit limit (21) exceeded",
  "data": null
}
```

#### 4. Authentication Errors
```json
{
  "success": false,
  "message": "Invalid student ID or PIN",
  "data": null
}
```

---

## Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL database
- Maven

### Database Setup
1. Create a PostgreSQL database named `course_allocation`
2. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/course_allocation
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
```bash
# Using Maven
./mvnw spring-boot:run

# Or using Java
./mvnw clean package
java -jar target/course-allocation-0.0.1-SNAPSHOT.jar
```

### Accessing Swagger UI
Once the application is running, navigate to:
```
http://localhost:8080/docs
```

---

## API Testing Examples

### Using cURL

#### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "STU001",
    "pin": "1234"
  }'
```

#### Get Available Courses
```bash
curl -X GET "http://localhost:8080/api/student/courses/available?studentId=1&semesterId=1" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Select a Course
```bash
curl -X POST "http://localhost:8080/api/student/courses/select?studentId=1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "courseId": 1
  }'
```

### Using Postman
1. Import the OpenAPI specification from `/v3/api-docs`
2. Set up environment variables for base URL and JWT token
3. Use the pre-configured requests

---

## Rate Limiting and Best Practices

### Best Practices
1. **Always include proper error handling** in your client applications
2. **Cache responses** where appropriate to reduce server load
3. **Use pagination** for large data sets (to be implemented)
4. **Validate data** on the client side before sending requests
5. **Store JWT tokens securely** (e.g., HttpOnly cookies, secure storage)

### Security Considerations
1. Never expose JWT secret in client-side code
2. Always use HTTPS in production
3. Implement proper CORS policies
4. Validate and sanitize all user inputs
5. Use strong PINs for student authentication

---

## Support and Contact

For issues, questions, or contributions, please contact the development team or create an issue in the project repository.

**Repository**: https://github.com/Kofidell4545/course-selection-server

---

## Version History

### v0.0.1-SNAPSHOT (Current)
- Initial release
- Basic CRUD operations for students, courses, semesters, and enrollments
- JWT authentication
- Course selection with business rule validation
- Swagger/OpenAPI documentation

---

## License

This project is licensed under the terms specified in the project repository.
