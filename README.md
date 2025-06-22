[Frontend GitHub Repository with React](https://github.com/BintangAull/siji-usu-frontend)

# Setup

## Requirements

- **Java Development Kit (JDK) 21 or later**

  [Download here](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or IntelliJ will download it automatically

- **Gradle 8.14 or later**

  [Download here](https://gradle.org/releases/) or IntelliJ will download it automatically


## How To Use

### 1. IntelliJ IDEA

1. **Modify Database:**
  - Create a new empty database in PostgreSQL
      ```postgresql
      CREATE DATABASE db_name;
      ```
  - Open [**application.example.yaml**](src/main/resources/application.example.yaml) in `/src/main/resources/com/jawa/utsposclient/`
  - Change `DATABASE_NAME`, `USERNAME`, `PASSWORD` with your own database configuration
2. **Modify JWT Secret Key:**
  - Open [**application.example.yaml**](src/main/resources/application.example.yaml) in `/src/main/resources/com/jawa/utsposclient/`
  - Change `BASE64_JWT_SECRET` as you want. You can decode plain text to Base64 using [this online tool](https://www.base64decode.org/).
3. Rename the file name `application.example.yaml` to `application.yaml`
4. **Run application**


## Libraries and Technologies Used

The following libraries are used in this project:

- **Spring Boot**  
  Main framework providing auto-configuration and application infrastructure for Spring applications.

- **Spring Web (Spring MVC)**  
  Provides web features and REST API capabilities with Model-View-Controller architecture.

- **Spring Security**  
  Security framework that handles authentication and authorization with JWT support.

- **Spring Data JPA**  
  Simplifies data access and entity management using JPA (Java Persistence API).

- **PostgreSQL JDBC Driver**  
  A JDBC driver for connecting the Java application to PostgreSQL databases.

- **JBCrypt**  
  A library for securely hashing passwords using the bcrypt algorithm.

- **Gradle**  
  Build system and dependency management tool for the project.

These libraries are essential to the functionality and user interface of the application. For more details on how to configure and use them, refer to the [`build.gradle.kts`](build.gradle.kts) file in the project.


# Endpoints

## Table of Contents

_* Click the endpoint to navigate to endpoint detail!_

### Authentication

| Category            | Method | Endpoint*                                      | Description                                |
|---------------------|--------|------------------------------------------------|--------------------------------------------|
| **Authentication**  | POST   | [`/api/auth/login`](#post-apiauthlogin)        | Login and obtain access & refresh tokens   |
| **Authentication**  | POST   | [`/api/auth/refresh` ](#post-apiauthrefresh)   | Refresh access token using a refresh token |
| **Authentication**  | POST   | [`/api/auth/logout`](#post-apiauthlogout)      | Logout and invalidate the refresh token    |
| **Change Password** | PATCH  | [`/api/auth/password`](#patch-apiauthpassword) | Change password                            |

### Admin - User Management

| Category             | Method | Endpoint*                                                              | Description                          |
|----------------------|--------|------------------------------------------------------------------------|--------------------------------------|
| **Admin - Current**  | GET    | [`/api/admins`](#get-apiadmins)                                        | Get current admin                    |
| **Admin - Lecturer** | POST   | [`/api/admins/users/lecturers`](#post-apiadminsuserslecturers)         | Create a new lecturer                |
| **Admin - Lecturer** | GET    | [`/api/admins/users/lecturers`](#get-apiadminsuserslecturers)          | Get all lecturers                    |
| **Admin - Lecturer** | GET    | [`/api/admins/users/lecturers/{id}`](#get-apiadminsuserslecturersid)   | Get lecturer with certain id         |
| **Admin - Lecturer** | PATCH  | [`/api/admins/users/lecturers/{id}`](#patch-apiadminsuserslecturersid) | Update lecturer info with certain id |
| **Admin - Student**  | POST   | [`/api/admins/users/students`](#post-apiadminsusersstudents)           | Create a new student                 |
| **Admin - Student**  | GET    | [`/api/admins/users/students`](#get-apiadminsusersstudents)            | Get all students                     |
| **Admin - Student**  | GET    | [`/api/admins/users/students/{id}`](#get-apiadminsusersstudentsid)     | Get student with certain id          |
| **Admin - Student**  | PATCH  | [`/api/admins/users/students/{id}`](#patch-apiadminsusersstudentsid)   | Update student info with certain id  |
| **Admin - User**     | GET    | [`/api/admins/users`](#get-apiadminsusers)                             | Get all users                        |
| **Admin - User**     | GET    | [`/api/admins/users/{id}`](#get-apiadminsusersid)                      | Get user with certain id             |

### Admin - Academic Management

| Category            | Method | Endpoint*                                                                                                                          | Description                                 |
|---------------------|--------|------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------|
| **Admin - Faculty** | POST   | [`/api/admins/academic/faculties`](#post-apiadminsacademicfaculties)                                                               | Create a new faculty                        |
| **Admin - Faculty** | GET    | [`/api/admins/academic/faculties`](#get-apiadminsacademicfaculties)                                                                | Get all faculties                           |
| **Admin - Faculty** | GET    | [`/api/admins/academic/faculties/{faculty_id}`](#get-apiadminsacademicfacultiesfaculty_id)                                         | Get faculty with certain id                 |
| **Admin - Faculty** | PATCH  | [`/api/admins/academic/faculties/{faculty_id}`](#patch-apiadminsacademicfacultiesfaculty_id)                                       | Update faculty info with certain id         |
| **Admin - Major**   | POST   | [`/api/admins/academic/faculties/{faculty_id}/majors`](#post-apiadminsacademicfacultiesfaculty_idmajors)                           | Create a new major within a faculty         |
| **Admin - Major**   | GET    | [`/api/admins/academic/faculties/majors?name=string`](#get-apiadminsacademicfacultiesmajorsnamestring)                             | Get all majors (name like)                  |
| **Admin - Major**   | GET    | [`/api/admins/academic/faculties/majors/{major_id}`](#get-apiadminsacademicfacultiesmajorsmajor_id)                                | Get major with certain id                   |
| **Admin - Major**   | PATCH  | [`/api/admins/academic/faculties/majors/{major_id}`](#patch-apiadminsacademicfacultiesmajorsmajor_id)                              | Update major info with certain id           |
| **Admin - Room**    | POST   | [`/api/admins/academic/departments/{department_id}/rooms`](#post-apiadminsacademicdepartmentsdepartment_idrooms)                   | Create a new room within a department       |
| **Admin - Room**    | GET    | [`/api/admins/academic/departments/{department_id}/rooms`](#get-apiadminsacademicdepartmentsdepartment_idrooms)                    | Get all rooms in a department               |
| **Admin - Room**    | PATCH  | [`/api/admins/academic/departments/{department_id}/rooms/{room_id}`](#patch-apiadminsacademicdepartmentsdepartment_idroomsroom_id) | Update room info with certain id            |
| **Admin - Course**  | POST   | [`/api/admins/academic/majors/{major_id}/courses`](#post-apiadminsacademicmajorsmajor_idcourses)                                   | Create a new course within a major          |
| **Admin - Course**  | GET    | [`/api/admins/academic/majors/{major_id}/courses?name=string`](#get-apiadminsacademicmajorsmajor_idcoursesnamestring)              | Get all courses in a major                  |
| **Admin - Course**  | GET    | [`/api/admins/academic/majors/courses/{course_id}`](#get-apiadminsacademicmajorscoursescourse_id)                                  | Get a major with a certain id               |
| **Admin - Section** | POST   | [`/api/admins/academic/courses/{course_id}/sections`](#post-apiadminsacademiccoursescourse_idsections)                             | Create a new course section within a course |
| **Admin - Section** | GET    | [`/api/admins/academic/courses/sections/{section_id}`](#get-apiadminsacademiccoursessectionssection_id)                            | Get section with certain id                 |

### Student

| Category              | Method | Endpoint*                                                                            | Description                |
|-----------------------|--------|--------------------------------------------------------------------------------------|----------------------------|
| **Student - Current** | GET    | [`/api/students`](#get-apistudents)                                                  | Get current student        |
| **Student - Section** | POST   | [`/api/students/courses/sections`](#post-apistudentssections)                        | Enroll to a course section |
| **Student - Section** | GET    | [`/api/students/courses/sections?query=string`](#get-apistudentssectionsquerystring) | Get Available Section      |

### Lecturer

| Category               | Method | Endpoint*                                                                                     | Description                |
|------------------------|--------|-----------------------------------------------------------------------------------------------|----------------------------|
| **Lecturer - Current** | GET    | [`/api/lecturers`](#get-apilecturers)                                                         | Get current lecturer       |
| **Lecturer - Section** | POST   | [`/api/lecturers/courses/sections`](#post-apilecturerscoursessections)                        | Enroll to a course section |
| **Lecturer - Section** | GET    | [`/api/lecturers/courses/sections?query=string`](#get-apilecturerscoursessectionsquerystring) | Get Available Section      |

## Authentication

### [`POST /api/auth/login`](#endpoints)

This endpoint is used to authenticate a user and get an JWT access token and refresh token.
The access token is used to authenticate requests to protected resources, while the refresh token can be used to get a new access token when the current one expires.

#### Payload:

```json
{
  "identifier": "string",
  "password": "string"
}
```

- `identifier`type is String, can be either an email, NIM, NIP, or NIDN.
- `password` type is String, the password of the user.

#### Response:

```json
{
  "access_token": "string",
  "refresh_token": "string"
}
```

- `access_token` type is String, the JWT access token. Valid for 15 minutes.
- `refresh_token` type is String, the JWT refresh token. Valid for 7 days.

***

### [`POST /api/auth/refresh`](#endpoints)

This endpoint is used to refresh the access token using the refresh token.

#### Payload:

```json
{
  "refresh_token": "string"
}
```

- `refresh_token` type is String, the JWT refresh token.

#### Response:

```json
{
  "access_token": "string",
  "refresh_token": "string"
}
```

- `access_token` type is String, the JWT access token. Valid for 15 minutes.
- `refresh_token` type is String, the JWT refresh token. Valid for 7 days.

***

### [`POST /api/auth/logout`](#endpoints)

`Authorization: Bearer {access_token}`

This endpoint is used to log out the user by invalidating the refresh token.

#### Payload:

```json
{
  "refresh_token": "string"
}
```

- `refresh_token` type is String, the JWT refresh token.

#### Response:

empty response with status code 204 (No Content).

***

### [`PATCH /api/auth/password`](#endpoints)

`Authorization: Bearer {access_token}`

This endpoint is used to change the password of the current user.

#### Payload:

```json
{
  "old_password": "string",
  "new_password": "string"
}
```

- `old_password` type is String, the current password of the user.
- `new_password` type is String, the new password of the user. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one number.

#### Response:

empty response with status code 204 (No Content).

***

## Admin

`Authorization: Bearer {access_token}`

### [`GET /api/admins`](#endpoints)

This endpoint is used to get the current admin's information.

#### Payload:

none

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "email": "string",
  "nip": "string"
}
```

***

### [`POST /api/admins/users/lecturers`](#endpoints)

This endpoint is used to create a new lecturer.

#### Payload:

```json
{
  "name": "string",
  "email": "string",
  "password": "string",
  "nip": "string",
  "nidn": "string",
  "department_id": 23
}
```

- `name` type is String, the name of the lecturer.
- `email` type is String, the email of the lecturer. Must be unique.
- `password` type is String, the password of the lecturer.
- `nip` type is String, the NIP of the lecturer. Must be 18 digits and unique.
- `nidn` type is String, the NIDN/Lecturer ID of the lecturer. Must be 10 digits and unique.
- `department_id` type is Long, the ID of the department.

#### Response:

empty response with status code 201 (Created).

***

### [`GET /api/admins/users/lecturers`](#endpoints)

This endpoint is used to get all lecturers.

#### Payload:

none

#### Response:

```json
[
  {
    "id": 23,
    "name": "string",
    "email": "string",
    "nip": "string",
    "nidn": "string",
    "faculty": "string",
    "department": "string",
    "advised_students": [
      {
        "id": 23,
        "name": "string",
        "nim": "string"
      }
    ],
    "courses_taught": [
      {
        "id": 23,
        "course_name": "string",
        "section_name": "string",
        "room": "string",
        "lecturer": "string"
      }
    ]
  }
]
```

***

### [`GET /api/admins/users/lecturers/{id}`](#endpoints)

This endpoint is used to get a lecturer with a certain ID.

#### Payload:

none

- `{id}` type is Long, the ID of the lecturer.

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "email": "string",
  "nip": "string",
  "nidn": "string",
  "faculty": "string",
  "department": "string",
  "advised_students": [
    {
      "id": 23,
      "name": "string",
      "nim": "string"
    }
  ],
  "courses_taught": [
    {
      "id": 23,
      "course_name": "string",
      "section_name": "string",
      "room": "string",
      "lecturer": "string"
    }
  ]
}
```

***

### [`PATCH /api/admins/users/lecturers/{id}`](#endpoints)

This endpoint is used to update information of a lecturer with a certain ID.

#### Payload:

```json
{
  "name": "string",
  "email": "string",
  "nip": "string",
  "nidn": "string"
}
```

- `{id}` type is Long, the ID of the lecturer.
- `name` type is String (NULLABLE), the name of the lecturer.
- `email` type is String (NULLABLE), the email of the lecturer. Must be unique.
- `nip` type is String (NULLABLE), the NIP of the lecturer. Must be 18 digits and unique.
- `nidn` type is String (NULLABLE), the NIDN/Lecturer ID of the lecturer. Must be 10 digits and unique.

### [`POST /api/admins/users/students`](#endpoints)

This endpoint is used to create a new student.

#### Payload:

```json
{
  "name": "string",
  "email": "string",
  "password": "string",
  "nim": "string",
  "major_id": 23,
  "academic_advisor_id": 23
}
```

- `name` type is String, the name of the student.
- `email` type is String, the email of the student. Must be unique.
- `password` type is String, the password of the student.
- `nim` type is String, the NIM/student ID of the student. Must be 9 digits and unique.
- `major_id` type is Long, the ID of the major/department.
- `academic_advisor_id` type is Long, the ID of the academic advisor.

#### Response:

empty response with status code 201 (Created).

***

### [`GET /api/admins/users/students`](#endpoints)

This endpoint is used to get all students.

#### Payload:

none

### Response:

```json
[
  {
    "id": 23,
    "name": "string",
    "email": "string",
    "nim": "string",
    "faculty": "string",
    "major": "string",
    "academic_advisor": {
      "id": 23,
      "name": "string"
    },
    "courses_taken": [
      {
        "id": 23,
        "course_name": "string",
        "section_name": "string",
        "room": "string",
        "lecturer": "string"
      }
    ]
  }
]
```

***

### [`GET /api/admins/users/students/{id}`](#endpoints)

This endpoint is used to get a student with a certain ID.

#### Payload:

none

- `{id}` type is Long, the ID of the student.

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "email": "string",
  "nim": "string",
  "faculty": "string",
  "major": "string",
  "academic_advisor": {
    "id": 23,
    "name": "string"
  },
  "courses_taken": [
    {
      "id": 23,
      "course_name": "string",
      "section_name": "string",
      "room": "string",
      "lecturer": "string"
    }
  ]
}
```

***

### [`PATCH /api/admins/users/students/{id}`](#endpoints)

This endpoint is used to update information of a student with a certain ID.

#### Payload:

```json
{
  "name": "string",
  "email": "string",
  "nim": "string",
  "academic_advisor_id": 23
}
```

- `{id}` type is Long, the ID of the student.
- `name` type is String (NULLABLE), the name of the student.
- `email` type is String (NULLABLE), the email of the student. Must be unique.
- `nim` type is String (NULLABLE), the NIM/student ID of the student. Must be 9 digits and unique.
- `academic_advisor_id` type is Long (NULLABLE), the ID of the academic advisor.

### [`GET /api/admins/users`](#endpoints)

This endpoint is used to get all users (admins, lecturers, and students).

#### Payload:

none

#### Response:

```json
[
  {
    "id": 23,
    "name": "string",
    "email": "string",
    "role": "string"
  }
]
```

***

### [`GET /api/admins/users/{id}`](#endpoints)

This endpoint is used to get a user with a certain ID.

#### Payload:

none

- `{id}` type is Long, the ID of the user.

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "email": "string",
  "role": "string"
}
```

***

### [`POST /api/admins/academic/faculties`](#endpoints)

This endpoint is used to create a new faculty.

#### Payload:

```json
{
  "name": "string",
  "faculty_code": "string"
}
```

- `name` type is String, the name of the faculty.
- `faculty_code` type is String, the code of the faculty. Must be 2 digits and unique.

#### Response:

empty response with status code 201 (Created).

***

### [`GET /api/admins/academic/faculties`](#endpoints)

This endpoint is used to get all faculties.

#### Payload:

none

#### Response:

```json
[
  {
    "id": 23,
    "name": "string",
    "code": "string",
    "departments": [
      {
        "id": 23,
        "name": "string",
        "code": "string"
      }
    ]
  }
]
```

***

### [`GET /api/admins/academic/faculties/{faculty_id}`](#endpoints)

This endpoint is used to get a faculty with a certain ID.

#### Payload:

none

- `{faculty_id}` type is Long, the ID of the faculty.

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "code": "string",
  "departments": [
    {
      "id": 23,
      "name": "string",
      "code": "string"
    }
  ]
}
```

***

### [`PATCH /api/admins/academic/faculties/{faculty_id}`](#endpoints)

This endpoint is used to update information of a faculty with a certain ID.

#### Payload:

```json
{
  "name": "string",
  "faculty_code": "string"
}
```

- `{faculty_id}` type is Long, the ID of the faculty.
- `name` type is String (NULLABLE), the name of the faculty.
- `faculty_code` type is String (NULLABLE), the code of the faculty. Must be 2 digits and unique.

#### Response:

empty response with status code 204 (No Content).

***

### [`POST /api/admins/academic/faculties/{faculty_id}/majors`](#endpoints)

This endpoint is used to create a new major/department.

#### Payload:

```json
{
  "name": "string",
  "major_code": "string"
}
```

- `{faculty_id}` type is Long, the ID of the faculty.
- `name` type is String, the name of the major.
- `major_code` type is String, the code of the major. Must be unique.

#### Response:

empty response with status code 201 (Created).

***

### [`GET /api/admins/academic/faculties/majors?name=string`](#endpoints)

This endpoint is used to get all majors/departments.

#### Payload:

none

- `name` type is String (OPTIONAL), the name of the major/department to filter by.
- If `name` is not provided, all majors/departments will be returned.

#### Response:

```json
[
  {
    "id": 23,
    "name": "string",
    "code": "string",
    "faculty": {
      "id": 23,
      "name": "string",
      "code": "string"
    },
    "rooms": [
      {
        "id": 23,
        "name": "string"
      }
    ]
  }
]
```

***

### [`GET /api/admins/academic/faculties/majors/{major_id}`](#endpoints)

This endpoint is used to get a major/department with a certain ID.

#### Payload:

none

- `{major_id}` type is Long, the ID of the major/department.

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "code": "string",
  "faculty": {
    "id": 23,
    "name": "string",
    "code": "string"
  },
  "rooms": [
    {
      "id": 23,
      "name": "string"
    }
  ]
}
```

***

### [`PATCH /api/admins/academic/faculties/majors/{major_id}`](#endpoints)

This endpoint is used to update information of a major/department with a certain ID.

#### Payload:

```json
{
  "name": "string",
  "major_code": "string"
}
```

- `{major_id}` type is Long, the ID of the major/department.
- `name` type is String (NULLABLE), the name of the major/department.
- `major_code` type is String (NULLABLE), the code of the major/department. Must be 2 digits. Unique per faculty.

#### Response:

empty response with status code 204 (No Content).

***

### [`POST /api/admins/academic/departments/{department_id}/rooms`](#endpoints)

This endpoint is used to create a new room in a department.

#### Payload:

```json
{
  "name": "string"
}
```

- `{department_id}` type is Long, the ID of the department.
- `name` type is String, the name of the room. Must be unique.

#### Response:

empty response with status code 201 (Created).

***

### [`GET /api/admins/academic/departments/{department_id}/rooms`](#endpoints)

This endpoint is used to get all rooms in a department.

#### Payload:

none

- `{department_id}` type is Long, the ID of the department.

#### Response:

```json
[
  {
    "id": 23,
    "name": "string"
  }
]
```

***

### [`PATCH /api/admins/academic/departments/{department_id}/rooms/{room_id}`](#endpoints)

This endpoint is used to update information of a room in a department.

#### Payload:

```json
{
  "name": "string"
}
```

- `{department_id}` type is Long, the ID of the department.
- `{room_id}` type is Long, the ID of the room.
- `name` type is String (NULLABLE), the name of the room. Must be unique.

#### Response:

empty response with status code 204 (No Content).

***

### [`POST /api/admins/academic/majors/{major_id}/courses`](#endpoints)

This endpoint is used to create a new course.

#### Payload:

```json
{
  "name": "string"
}
```

- `{major_id}` type is Long, the ID of the major/department.
- `name` type is String, the name of the course.

#### Response:

empty response with status code 201 (Created).

***

### [`GET /api/admins/academic/majors/{major_id}/courses?name=string`](#endpoints)

This endpoint is used to get all courses in a major/department.

#### Payload:

none

- `{major_id}` type is Long, the ID of the major/department.
- `name` type is String (OPTIONAL), the name of the course to filter by.
- If `name` is not provided, all courses in the major/department will be returned.

#### Response:

```json
[
  {
    "id": 23,
    "name": "string",
    "course_sections": [
      {
        "id": 23,
        "name": "string",
        "lecturer": "string",
        "room": "string"
      }
    ]
  }
]
```

***

### [`GET /api/admins/academic/majors/courses/{course_id}`](#endpoints)

This endpoint is used to get a course with a certain ID.

#### Payload:

none

- `{course_id}` type is Long, the ID of the course.

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "course_sections": [
    {
      "id": 23,
      "name": "string",
      "lecturer": "string",
      "room": "string"
    }
  ]
}
```

***

### [`POST /api/admins/academic/courses/{course_id}/sections`](#endpoints)

This endpoint is used to create a new course section.

#### Payload:

```json
{
  "name": "string",
  "lecturer_id": 23, 
  "room_id": 23,
  "course_id": 23
}
```

- `{course_id}` type is Long, the ID of the course.
- `name` type is String, the name of the course section.
- `lecturer_id` type is Long, the ID of the lecturer.
- `room_id` type is Long, the ID of the room.
- `course_id` type is Long, the ID of the course.

#### Response:

empty response with status code 201 (Created).

***

### [`GET /api/admins/academic/courses/sections/{section_id}`](#endpoints)

This endpoint is used to get a course section with a certain ID.

#### Payload:

none

- `{section_id}` type is Long, the ID of the course section.

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "lecturer": {
    "id": 23,
    "name": "string"
  },
  "room": {
    "id": 23,
    "name": "string"
  },
  "course": {
    "id": 23,
    "name": "string"
  }
}
```

## Student

`Authorization: Bearer {access_token}`

### [`GET /api/students`](#endpoints)

This endpoint is used to get the current student's information.

#### Payload:

none

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "email": "string",
  "nim": "string",
  "faculty": "string",
  "major": "string",
  "academic_advisor": {
    "id": 23,
    "name": "string"
  },
  "courses_taken": [
    {
      "id": 23,
      "course_name": "string",
      "section_name": "string",
      "room": "string",
      "lecturer": "string"
    }
  ]
}
```

***

### [`POST /api/students/sections`](#endpoints)

This endpoint is used to enroll a student to a course section.

#### Payload:

```json
{
  "section_id": 23
}
```

- `section_id` type is Long, the ID of the course section.

#### Response:

empty response with status code 204 (No Content).

***

### [`GET /api/students/sections?query=string`](#endpoints)

This endpoint is used to get all available course sections for the student.

#### Payload:

none

- `query` type is String (OPTIONAL), the name of the course to filter by.
- If `query` is not provided, all available course sections will be returned.

#### Response:

```json
[
  {
    "id": 23,
    "course_name": "string",
    "section_name": "string",
    "room": "string",
    "lecturer": "string"
  }
]
```

## Lecturer

`Authorization: Bearer {access_token}`

### [`GET /api/lecturers`](#endpoints)

This endpoint is used to get the current lecturer's information.

#### Payload:

none

#### Response:

```json
{
  "id": 23,
  "name": "string",
  "email": "string",
  "nip": "string",
  "nidn": "string",
  "faculty": "string",
  "department": "string",
  "advised_students": [
    {
      "id": 23,
      "name": "string",
      "nim": "string"
    }
  ],
  "courses_taught": [
    {
      "id": 23,
      "course_name": "string",
      "section_name": "string",
      "room": "string",
      "lecturer": "string"
    }
  ]
}
```

***

### [`POST /api/lecturers/courses/sections`](#endpoints)

This endpoint is used to enroll a lecturer to a course section.

#### Payload:

```json
{
  "section_id": 23
}
```

- `section_id` type is Long, the ID of the course section.

#### Response:

empty response with status code 204 (No Content).

***

### [`GET /api/lecturers/courses/sections?query=string`](#endpoints)

This endpoint is used to get all available course sections for the lecturer.

#### Payload:

none

- `query` type is String (OPTIONAL), the name of the course to filter by.
- If `query` is not provided, all available course sections will be returned.

#### Response:

```json
[
  {
    "id": 23,
    "course_name": "string",
    "section_name": "string",
    "room": "string",
    "lecturer": "string"
  }
]
```