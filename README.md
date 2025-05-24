# Endpoints


## Table of Contents

_* Click the endpoint to navigate to endpoint detail!_

### Authentication

| Category              | Method | Endpoint*                                                                                                       | Description                                 |
|-----------------------|--------|-----------------------------------------------------------------------------------------------------------------|---------------------------------------------|
| **Authentication**    | POST   | [`/api/auth/login`](#post-apiauthlogin)                                                                         | Login and obtain access & refresh tokens    |
| **Authentication**    | POST   | [`/api/auth/refresh` ](#post-apiauthrefresh)                                                                    | Refresh access token using a refresh token  |
| **Authentication**    | POST   | [`/api/auth/logout`](#post-apiauthlogout)                                                                       | Logout and invalidate the refresh token     |

### Admin

| Category              | Method | Endpoint*                                                                                                       | Description                                 |
|-----------------------|--------|-----------------------------------------------------------------------------------------------------------------|---------------------------------------------|
| **Admin - Current**   | GET    | [`/api/admins`](#get-apiadmins)                                                                                 | Get current admin                           |
| **Admin - Lecturer**  | POST   | [`/api/admins/users/lecturers`](#post-apiadminsuserslecturers)                                                  | Create a new lecturer                       |
| **Admin - Lecturer**  | GET    | [`/api/admins/users/lecturers`](#get-apiadminsuserslecturers)                                                   | Get all lecturers                           |
| **Admin - Lecturer**  | GET    | [`/api/admins/users/lecturers/{id}`](#get-apiadminsuserslecturersid)                                            | Get lecturer with certain id                |
| **Admin - Student**   | POST   | [`/api/admins/users/students`](#post-apiadminsusersstudents)                                                    | Create a new student                        |
| **Admin - Student**   | GET    | [`/api/admins/users/students`](#get-apiadminsusersstudents)                                                     | Get all students                            |
| **Admin - Student**   | GET    | [`/api/admins/users/students/{id}`](#get-apiadminsusersstudentsid)                                              | Get student with certain id                 |
| **Admin - User**      | GET    | [`/api/admins/users`](#get-apiadminsusers)                                                                      | Get all users                               |
| **Admin - User**      | GET    | [`/api/admins/users/{id}`](#get-apiadminsusersid)                                                               | Get user with certain id                    |
| **Admin - Faculty**   | POST   | [`/api/admins/academic/faculties`](#post-apiadminsacademicfaculties)                                            | Create a new faculty                        |
| **Admin - Major**     | POST   | [`/api/admins/academic/faculties/{faculty_id}/majors`](#post-apiadminsacademicfacultiesfaculty_idmajors)        | Create a new major within a faculty         |
| **Admin - Room**      | POST   | [`/api/admins/academic/deparments/{department_id}/rooms`](#post-apiadminsacademicdepartmentsdepartment_idrooms) | Create a new room within a department       |
| **Admin - Course**    | POST   | [`/api/admins/academic/majors/{major_id}/courses`](#post-apiadminsacademicmajorsmajor_idcourses)                | Create a new course within a major          |
| **Admin - Section**   | POST   | [`/api/admins/academic/courses/{course_id}/sections`](#post-apiadminsacademiccoursescourse_idsections)          | Create a new course section within a course |

### Student

| Category              | Method | Endpoint*                                             | Description                |
|-----------------------|--------|-------------------------------------------------------|----------------------------|
| **Student - Current** | GET    | [`/api/students`](#get-apistudents)                   | Get current student        |
| **Student - Section** | POST   | [`/api/students/sections`](#post-apistudentssections) | Enroll to a course section |

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