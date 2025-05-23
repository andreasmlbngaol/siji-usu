# Endpoints

| Category             | Method | Endpoint*                                                     | Description                                 |
|----------------------|--------|---------------------------------------------------------------|---------------------------------------------|
| **Authentication**   | POST   | [`/auth/login`](#post-authlogin)                              | Login and obtain access & refresh tokens    |
| **Authentication**   | POST   | [`/auth/refresh` ](#post-authrefresh)                         | Refresh access token using a refresh token  |
| **Authentication**   | POST   | [`/auth/logout`](#post-authlogout)                            | Logout and invalidate the refresh token     |
| **Admin - Lecturer** | POST   | [`/admin/lecturer`](#post-adminlecturer)                      | Create a new lecturer                       |
| **Admin - Student**  | POST   | [`/admin/student`](#post-adminstudent)                        | Create a new student                        |
| **Admin - Faculty**  | POST   | [`/admin/faculty`](#post-adminfaculty)                        | Create a new faculty                        |
| **Admin - Major**    | POST   | [`/admin/{faculty_id}/major`](#post-adminfaculty_idmajor)     | Create a new major within a faculty         |
| **Admin - Room**     | POST   | [`/admin/{department_id}/room`](#post-admindepartment_idroom) | Create a new room within a department       |
| **Admin - Course**   | POST   | [`/admin/{major_id}/course`](#post-adminmajor_idcourse)       | Create a new course within a major          |
| **Admin - Section**  | POST   | [`/admin/{course_id}/section`](#post-admincourse_idsection)   | Create a new course section within a course |

_*  Click the endpoint to navigate to endpoint detail!_

## Authentication

### [`POST /auth/login`](#endpoints)

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

### [`POST /auth/refresh`](#endpoints)

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

### [`POST /auth/logout`](#endpoints)

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

### [`POST /admin/lecturer`](#endpoints)

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

### [`POST /admin/student`](#endpoints)

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

### [`POST /admin/faculty`](#endpoints)

This endpoint is used to create a new faculty.

#### Payload:

```json
{
  "name": "string",
  "faculty_code": "string"
}
```

- `name` type is String, the name of the faculty.
- `faculty_code` type is String, the code of the faculty. Must be unique.

#### Response:

empty response with status code 201 (Created).

***

### [`POST /admin/{faculty_id}/major`](#endpoints)

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

### [`POST /admin/{department_id}/room`](#endpoints)

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

### [`POST /admin/{major_id}/course`](#endpoints)

This endpoint is used to create a new course.

#### Payload:

```json
{
  "name": "string",
  "course_code": "string"
}
```

- `{major_id}` type is Long, the ID of the major/department.
- `name` type is String, the name of the course.
- `course_code` type is String, the code of the course. Must be unique.

#### Response:

empty response with status code 201 (Created).

***

### [`POST /admin/{course_id}/section`](#endpoints)

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