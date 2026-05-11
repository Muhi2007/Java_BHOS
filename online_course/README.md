# Learnify Online Course Platform

Learnify is a Spring Boot + Thymeleaf web application for managing online courses with separate teacher and learner experiences.

Teachers can create courses, quizzes, and questions. Learners can enroll, take quizzes, and review courses. The app uses a role-based session flow and an in-memory H2 database for fast local development.

## Features

### Authentication and Roles
- Unified signup/login flow for two user types: `Teacher` and `Learner`
- Session-based identity and role tracking (`CurrUserID`, `CurrUserRole`)
- Route-level role guards and redirects

### Teacher Features
- Create courses
- Create quizzes under courses
- Edit and delete quizzes
- Create, edit, and delete quiz questions
- View course quiz lists and quiz question lists
- Teacher dashboard with activity cards and quick actions

### Learner Features
- Browse and enroll in courses
- View enrolled courses
- Open course details and assessments
- Start quiz attempts, submit answers, and view results
- Submit course reviews and ratings

### Quiz and Assessment Logic
- `Quiz` extends abstract `Assessment`
- Question-based scoring with `calculateScore(Map<Long, String> answerMap)`
- Attempt records persisted via `QuizAttempt`

## Tech Stack

- Java 25
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- Thymeleaf
- H2 Database (in-memory)
- Maven (wrapper included)
- Bootstrap 5

## Project Structure

```text
src/main/java/com/platform/online_course/
  controller/
    AuthController.java
    CourseController.java
    DashboardController.java
    TeacherController.java
  model/
    BaseUser.java
    Learner.java
    Teacher.java
    Course.java
    Assessment.java
    Quiz.java
    Question.java
    QuizAttempt.java
    Review.java
  repository/
    *Repository.java
  service/
    MockCourseSeeder.java

src/main/resources/
  templates/
  static/
  application.properties
```

## Prerequisites

- JDK 25+
- Git
- Internet access for first dependency download
- Optional: Maven installed globally (not required because wrapper is included)

## Quick Start

### 1) Clone and open the project

```bash
git clone <your-repo-url>
cd online_course
```

### 2) Run tests

Windows (PowerShell/cmd):
```bash
.\mvnw.cmd test
```

macOS/Linux:
```bash
./mvnw test
```

### 3) Run the application

Windows:
```bash
.\mvnw.cmd spring-boot:run
```

macOS/Linux:
```bash
./mvnw spring-boot:run
```

### 4) Open in browser

- App: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`

H2 settings (from `application.properties`):
- JDBC URL: `jdbc:h2:mem:coursedb`
- Username: `sa`
- Password: (empty)

## Build Commands

Windows:
```bash
.\mvnw.cmd clean package
```

macOS/Linux:
```bash
./mvnw clean package
```

Run jar directly:
```bash
java -jar target/online_course-0.0.1-SNAPSHOT.jar
```

## Route Overview

### Public/Auth
- `GET /signup`
- `POST /signup`
- `GET /login`
- `POST /login`
- `GET /logout`

### Learner Flow
- `GET /learner/dashboard`
- `GET /learner/my-courses`
- `POST /enroll/{courseID}`
- `GET /course/{id}`
- `GET /course/{courseId}/add-review`
- `POST /course/{courseId}/add-review`
- `GET /quiz/{quizId}/start`
- `POST /quiz/{attemptId}/submit`
- `GET /quiz/{attemptId}/result`

### Teacher Flow
- `GET /teacher/dashboard`
- `GET /teacher/create-course`
- `POST /teacher/create-course`
- `GET /course/{courseId}/create-quiz`
- `POST /course/{courseId}/create-quiz`
- `GET /course/{courseId}/quizzes`
- `GET /course/{courseId}/quiz/{quizId}/edit`
- `POST /course/{courseId}/quiz/{quizId}/edit`
- `POST /course/{courseId}/quiz/{quizId}/delete`
- `GET /course/{courseId}/quiz/{quizId}/questions`
- `GET /course/{courseId}/quiz/{quizId}/create-question`
- `POST /course/{courseId}/quiz/{quizId}/create-question`
- `GET /course/{courseId}/quiz/{quizId}/question/{questionId}/edit`
- `POST /course/{courseId}/quiz/{quizId}/question/{questionId}/edit`
- `POST /course/{courseId}/quiz/{quizId}/question/{questionId}/delete`

## Data Model Summary

### Core Relationships
- Teacher to Course: many courses per teacher (`Course.instructor`)
- Course to Assessment/Quiz: one-to-many (`Course.assessments`)
- Quiz to Question: one-to-many (`Quiz.questions`)
- Learner to Course: many-to-many enrollment (`course_enrollment` join table)
- Learner to QuizAttempt: one-to-many via attempt records
- QuizAttempt to Quiz: many attempts can reference one quiz
- Course to Review: one-to-many, review aggregation for ratings

### Association-Class Behavior
`QuizAttempt` acts like a relationship record between learner and quiz, storing:
- submitted answer map
- score
- completion state

## Architecture Notes

- MVC pattern:
  - Models: JPA entities and domain logic
  - Views: Thymeleaf templates
  - Controllers: routing, role checks, session handling, and form binding
- Persistence:
  - Spring Data JPA repositories with constructor injection
  - No raw SQL required for core CRUD flows

## Current Limitations / Security Notes

- Passwords are currently compared in plaintext (no hashing yet)
- RBAC is manually enforced in controllers (not centralized with Spring Security)
- In-memory H2 DB resets on restart
- Limited quiz type support (question-answer map grading flow)
- Minimal request validation annotations on DTO/entity input paths

## Development Notes

- `MockCourseSeeder` seeds demo data at startup for local testing.
- The project is configured for quick iterative development using embedded H2 and Thymeleaf templates.
