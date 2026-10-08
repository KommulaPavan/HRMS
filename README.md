# 🏢 HRMS – Human Resource Management System

A full-stack **Human Resource Management System (HRMS)** built with **Java 21 and Spring Boot**, designed to manage employee lifecycle, authentication, attendance, leave, payroll, training, performance reviews, announcements, recruitment/job openings and HR operations.

The application also demonstrates enterprise backend concepts including **JWT authentication, role-based authorization, REST APIs, OpenFeign, RestTemplate, Apache Kafka, Oracle Database, email notifications, PDF generation, Docker, Docker Compose, Kubernetes and SonarQube**.

---

## 📌 Project Overview

The HRMS platform centralizes major HR activities into a single application.

### 👨‍💼 Admin

- Manage employees
- Manage users and roles
- Monitor attendance
- Manage announcements
- Manage job openings
- Manage holidays
- Manage payroll
- Manage training
- View employee reviews

### 🧑‍💼 HR

- View employees
- Manage leave requests
- Approve/reject leave
- Manage job openings
- Manage training
- Manage employee information
- Manage employee reviews

### 👨‍💻 Employee

- Login securely
- Manage profile
- Check in / check out
- View attendance
- Apply for leave
- View leave history
- View announcements
- View job openings
- Participate in training
- Track training progress
- View/update performance reviews
- View payroll
- Download payslips

---

# 🏗️ System Architecture

```text
                           ┌──────────────────────┐
                           │    HRMS Frontend     │
                           │    Web Application    │
                           └──────────┬───────────┘
                                      │
                                      ▼
                           ┌──────────────────────┐
                           │   Spring Boot API    │
                           │      Port 8080       │
                           └──────────┬───────────┘
                                      │
          ┌───────────────────────────┼────────────────────────────┐
          │                           │                            │
          ▼                           ▼                            ▼
 ┌─────────────────┐       ┌─────────────────┐          ┌─────────────────┐
 │ Authentication  │       │ Employee Module │          │ HR/Admin Module │
 │     + JWT       │       │                 │          │                 │
 └─────────────────┘       └─────────────────┘          └─────────────────┘
          │                         │                            │
          │                         │                            │
          ▼                         ▼                            ▼
 ┌─────────────────┐       ┌─────────────────┐          ┌─────────────────┐
 │ Spring Security │       │ Employee Profile│          │ Leave / Jobs /  │
 │ Role Authorization│     │ Attendance      │          │ Training / etc. │
 └─────────────────┘       └─────────────────┘          └─────────────────┘
                                      │
                                      ▼
                           ┌──────────────────────┐
                           │    Oracle Database   │
                           └──────────┬───────────┘
                                      │
                                      │
                   ┌──────────────────┴──────────────────┐
                   │                                     │
                   ▼                                     ▼
          ┌──────────────────┐                  ┌──────────────────┐
          │ Apache Kafka     │                  │ Email / PDF      │
          │ Event Processing │                  │ Notifications    │
          └──────────────────┘                  └──────────────────┘
```

---

# 🧱 Backend Architecture

The backend follows a layered architecture:

```text
┌─────────────────────────────────────┐
│             Controller              │
│              REST API               │
└──────────────────┬──────────────────┘
                   │
                   ▼
┌─────────────────────────────────────┐
│              Service                │
│          Business Logic             │
└──────────────────┬──────────────────┘
                   │
                   ▼
┌─────────────────────────────────────┐
│             Repository              │
│         Data Access Layer           │
└──────────────────┬──────────────────┘
                   │
                   ▼
┌─────────────────────────────────────┐
│          Oracle Database            │
└─────────────────────────────────────┘
```

Additional integration components:

```text
Service
   │
   ├── RestTemplate
   │
   ├── OpenFeign
   │
   ├── Kafka Producer
   │
   ├── Kafka Consumer
   │
   └── JavaMailSender
```

---

# 🛠️ Technology Stack

| Technology | Usage |
|---|---|
| Java 21 | Backend development |
| Spring Boot 3.3.5 | Application framework |
| Spring MVC | REST APIs |
| Spring Security | Authentication & authorization |
| JWT | Token-based authentication |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| Oracle Database | Relational database |
| Apache Kafka | Event-driven communication |
| Spring Kafka | Kafka integration |
| OpenFeign | Service-to-service communication |
| RestTemplate | HTTP service communication |
| JavaMailSender | Email notifications |
| OpenPDF | Payslip PDF generation |
| Apache POI | Excel processing |
| Lombok | Boilerplate reduction |
| Maven | Build and dependency management |
| Docker | Containerization |
| Docker Compose | Local multi-container deployment |
| Kubernetes | Container orchestration |
| SonarQube | Code quality analysis |
| Git | Version control |
| GitHub | Source code management |

The project configuration explicitly includes Spring Web, JPA, Security, Kafka, Mail, OpenFeign, Oracle JDBC, JWT, Apache POI and OpenPDF dependencies.

---

# 🔐 1. Authentication & Authorization

The application uses:

- Spring Security
- JWT
- Password encoding
- Role-based access
- Account activation
- Authenticated API requests

## Login Flow

```text
Employee ID
     +
 Password
     │
     ▼
Employee Repository
     │
     ▼
Check Account Status
     │
     ▼
Password Verification
     │
     ▼
JWT Generation
     │
     ▼
Client
```

The login service verifies the employee ID, checks that the account is active and validates the password before generating a JWT.

---

# 🎟️ 2. Account Activation

New employees receive an account activation token.

```text
Create Employee
      │
      ▼
Generate Employee ID
      │
      ▼
Create Activation Token
      │
      ▼
Send Email
      │
      ▼
Activation Link
      │
      ▼
Employee Activates Account
```

The activation service generates a UUID token, stores the employee association and validates that the token has not expired.

---

# 🆔 3. Employee ID Generation

The project includes a dedicated `SequenceService` for generating employee IDs.

Example format:

```text
CT-091525001
```

The ID contains:

```text
CT + Date + Sequence Number
```

The sequence service uses a database row lock to atomically increment the counter and handles concurrent first-insert situations.

### Why this approach?

It helps prevent duplicate IDs when multiple employee registrations occur concurrently.

---

# 👨‍💼 4. Employee Management

Employee management includes:

- Create employee
- Assign role
- Assign department
- Assign designation
- Generate employee ID
- Account status
- Employee listing
- Employee details
- Activation email

Employee creation assigns a normalized role and generates the next employee ID before saving the employee.

---

# 👤 5. Employee Profile

Employees can manage:

- Name
- Email
- Department
- Phone
- About
- Location
- Role
- Avatar/profile image

The profile service supports creating/updating the employee profile and storing an avatar file.

---

# ⏰ 6. Attendance Management

Attendance functionality includes:

- Check-in
- Check-out
- Today's attendance
- Attendance history
- Multi-day attendance
- Monthly attendance
- Date-range attendance
- Work-minute calculation
- Payroll attendance data

## Check-in

```text
Employee
   │
   ▼
Check In
   │
   ▼
Find Today's Record
   │
   ▼
Set Check-In Time
   │
   ▼
PRESENT
```

## Check-out

```text
Employee
   │
   ▼
Check Out
   │
   ▼
Set Check-Out Time
   │
   ▼
Calculate Duration
   │
   ▼
Store Work Minutes
```

The attendance service calculates work duration using the difference between check-in and check-out timestamps.

---

# 🏖️ 7. Leave Management

Employees can apply for leave with:

- Leave type
- From date
- To date
- Number of days
- Reason

New leave requests are stored as `PENDING`.

## Leave Workflow

```text
Employee
   │
   ▼
Apply Leave
   │
   ▼
PENDING
   │
   ▼
HR Review
   │
   ├──────────────┐
   ▼              ▼
APPROVED       REJECTED
```

HR approval records:

- Approver
- Remark
- Approval timestamp
- Status



---

# 📅 8. Leave / Holiday Calendar

The calendar module manages:

- Company holidays
- Holiday type
- Holiday date
- Employee/calendar identifier
- Status

It supports:

- Bulk calendar replacement
- Individual row upsert
- Automatic ID generation
- Duplicate-date validation



---

# 📢 9. Announcement Management

HR/Admin can create announcements with:

- Title
- Message
- Priority
- Audience
- Publish time
- Expiration time
- Creator
- Creator role
- Attachment

Announcement status is calculated as:

```text
             Future Publish
                  │
                  ▼
              SCHEDULED
                  │
                  ▼
                LIVE
                  │
                  ▼
               EXPIRED
```



Attachments are supported and the service currently limits an attachment to 5 MB.

---

# 💼 10. Job Opening Management

The job opening module manages:

- Job title
- Department
- Job type
- Location
- Number of openings
- Minimum salary
- Maximum salary
- Salary range
- Job status
- Creator/employee

The service validates salary boundaries before creating the job.

Authentication is also checked before a job opening is created.

---

# 🎓 11. Training Management

The project includes a training management module.

Training functionality includes:

- Create training
- Generate training UID
- List training
- Update training
- Delete training

Training records receive a UUID when a training ID is not supplied.

---

# 👨‍🎓 12. Training Enrollment

Employees can be enrolled into training programs.

### Enrollment Flow

```text
Training
   │
   ▼
Select Employees
   │
   ▼
Validate Employee IDs
   │
   ▼
Check Existing Enrollment
   │
   ├──────────────┐
   ▼              ▼
Already        New Enrollment
Enrolled            │
                    ▼
                ENROLLED
                    │
                    ▼
               Progress = 0%
```

The enrollment service checks invalid IDs, employees that do not exist and employees who are already enrolled before creating new enrollment records.

### Training Progress

Training progress is maintained between:

```text
0% ─────────────────────── 100%
```

The service limits progress to the range 0–100.

---

# ⭐ 13. Employee Performance Reviews

The review module supports employee performance reviews.

### Review Data

- Employee ID
- Rating
- Goals
- Comments
- Status

### Operations

```text
Create Review
     │
     ▼
Update Review
     │
     ▼
Publish Review
```

The review service supports creating, listing, employee-specific retrieval, updating and publishing reviews.

---

# 💰 14. Payroll Management

Payroll processing combines:

- Employee data
- Attendance data
- Base salary
- Paid days
- Working days
- Gross salary
- PF
- Tax
- Total deductions
- Net salary

The payroll service retrieves employees and attendance data through dedicated clients.

---

# 🧮 Payroll Calculation

### Gross Salary

```text
Gross Salary =
Base Salary × Paid Days / Working Days
```

### PF

```text
PF = Gross Salary × 12%
```

### Tax

```text
If Gross Salary > ₹25,000

Tax = Gross Salary × 10%

Otherwise

Tax = ₹0
```

### Total Deductions

```text
Total Deductions = PF + Tax
```

### Net Salary

```text
Net Salary = Gross Salary - Total Deductions
```

These rules are implemented in the current payroll service.

---

# 📄 15. Payslip Generation

The application generates PDF payslips.

```text
Employee + Month
       │
       ▼
Find Payroll
       │
       ▼
Read PF / Tax / Deductions / Net
       │
       ▼
Generate PDF
       │
       ▼
Return PDF
```

The current implementation uses OpenPDF to generate the document.

---

# 🔄 16. Service-to-Service Communication

The project demonstrates two approaches:

## RestTemplate

`EmployeeClient` communicates with the employee API using `RestTemplate`.

`AttendanceClient` similarly retrieves attendance data using `RestTemplate`.

## OpenFeign

The project also contains an OpenFeign client:

```text
EmployeeFeginClient
        │
        ▼
GET /api/employees/{employeeId}
        │
        ▼
Employee Service
```



---

# 🔐 JWT Propagation Between Services

The service clients retrieve the JWT credentials from Spring Security's `SecurityContext`.

```text
Authenticated Request
        │
        ▼
SecurityContext
        │
        ▼
Extract JWT
        │
        ▼
Authorization: Bearer <token>
        │
        ▼
Call Another API
```

The employee and attendance clients explicitly construct a Bearer authorization header from the security context.

---

# 📨 17. Apache Kafka

Kafka is used for asynchronous event processing.

### Payroll Event Flow

```text
              Payroll Service
                    │
                    ▼
             Save Payroll
                    │
                    ▼
             Kafka Producer
                    │
                    ▼
             Kafka Topic
                    │
                    ▼
          Notification Consumer
                    │
                    ▼
               Email/Event
```

After payroll is saved, the payroll service publishes a payroll-generation event.

---

# 📧 18. Email Notifications

The project uses Spring Boot Mail / `JavaMailSender`.

Email functionality includes:

- Employee activation emails
- Payroll notifications
- Custom email messages

```text
Business Event
      │
      ▼
Mail Service
      │
      ▼
JavaMailSender
      │
      ▼
SMTP
      │
      ▼
Employee Email
```

---

# 📤 19. Outbox Pattern

The project includes an Outbox module for managing outbound event records.

```text
Business Operation
       │
       ├──────────────► Business Data
       │
       └──────────────► Outbox Event
                              │
                              ▼
                           Kafka
                              │
                              ▼
                          Consumer
```

The Outbox service retrieves persisted outbound event records through the repository.

---

# 🗄️ 20. Oracle Database

Oracle is the primary relational database.

The application uses:

```text
Spring Data JPA
       │
       ▼
Hibernate
       │
       ▼
Oracle JDBC
       │
       ▼
Oracle Database
```

The project uses Oracle JDBC `ojdbc11` and Hibernate/JPA configuration.

---

# 🧩 21. Main Domain Areas

The application contains data for areas including:

```text
Employee
Employee Profile
Role
Activation Token
Attendance
Leave
Leave Calendar
Job Opening
Announcement
Announcement Attachment
Payroll
Training
Training Enrollment
Performance Review
Sequence Counter
Outbox
```

---

# 🐳 22. Docker Architecture

Docker Compose defines an application container and Oracle database container.

```text
                 Docker Compose
                       │
             ┌─────────┴─────────┐
             │                   │
             ▼                   ▼
       ┌───────────┐       ┌─────────────┐
       │ HRMS App  │──────►│ Oracle XE   │
       │   :8080   │       │    :1521    │
       └───────────┘       └─────────────┘
```

The Compose configuration connects the application to the Oracle container using the Docker service name `oracle-db`.

---

# ☸️ 23. Kubernetes Architecture

The project includes Kubernetes deployment and service definitions.

```text
                 Kubernetes Cluster
                         │
              ┌──────────┴──────────┐
              │                     │
              ▼                     ▼
       ┌─────────────┐       ┌─────────────┐
       │  HCM App    │       │  Oracle DB   │
       │ Deployment  │       │ Deployment   │
       └──────┬──────┘       └──────┬──────┘
              │                     │
              ▼                     ▼
       ┌─────────────┐       ┌─────────────┐
       │ HCM Service │       │ Oracle      │
       │  NodePort   │       │ Service     │
       └─────────────┘       └─────────────┘
```

The application deployment exposes port 8080 and is configured with an Oracle database service connection.

The application is exposed through a Kubernetes `NodePort` on port `30007`.

Oracle runs using the `gvenzl/oracle-xe:21-slim` image and exposes port 1521.

---

# 📦 24. Docker vs Kubernetes

### Docker

Used for:

- Application containerization
- Local development
- Running application + Oracle together

### Kubernetes

Used for:

- Deployment management
- Service exposure
- Container orchestration
- Application/database separation

---

# 🧪 25. SonarQube

The Maven project includes the Sonar Maven plugin and project configuration for SonarQube analysis.

Typical workflow:

```text
Developer
    │
    ▼
Git
    │
    ▼
Maven Build
    │
    ▼
Tests
    │
    ▼
SonarQube Analysis
    │
    ▼
Code Quality Report
```

---

# 📂 Project Structure

```text
HRMS/
│
├── Portal/
│   │
│   ├── src/
│   │   │
│   │   ├── main/
│   │   │   │
│   │   │   ├── java/
│   │   │   │   └── com/example/Portal/
│   │   │   │       │
│   │   │   │       ├── Client/
│   │   │   │       ├── Config/
│   │   │   │       ├── Controller/
│   │   │   │       ├── Dto/
│   │   │   │       ├── Entity/
│   │   │   │       ├── Kafka/
│   │   │   │       ├── Repository/
│   │   │   │       ├── Service/
│   │   │   │       └── Utils/
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   │
│   │   └── test/
│   │
│   └── pom.xml
│
├── Dockerfile
├── docker-compose.yml
│
├── app-deployment.yaml
├── app-service.yaml
│
├── oracle-deployment.yaml
├── oracle-service.yaml
│
├── logs/
│
└── README.md
```

---

# 🔄 Complete HRMS Business Flow

```text
                         USER
                           │
                           ▼
                    ┌──────────────┐
                    │    LOGIN     │
                    │    + JWT     │
                    └──────┬───────┘
                           │
                           ▼
                    ROLE AUTHORIZATION
                           │
            ┌──────────────┼───────────────┐
            │              │               │
            ▼              ▼               ▼
        EMPLOYEE        HR / ADMIN      EMPLOYEE
        SERVICES         SERVICES       SERVICES
            │              │               │
            ▼              ▼               ▼
       ┌────────┐     ┌──────────┐    ┌──────────┐
       │Profile │     │Leave     │    │Attendance│
       │        │     │Jobs      │    │          │
       └────────┘     │Training  │    └────┬─────┘
                      │Reviews   │         │
                      │Announce  │         │
                      └────┬─────┘         │
                           │               │
                           └───────┬───────┘
                                   ▼
                              ┌─────────┐
                              │ Payroll │
                              └────┬────┘
                                   │
                    ┌──────────────┴─────────────┐
                    │                            │
                    ▼                            ▼
             Oracle Database                 Kafka Event
                                                 │
                                                 ▼
                                           Notifications
                                                 │
                                                 ▼
                                              Email
```

---

# 🚀 Running the Application

## Prerequisites

Install:

```text
Java 21
Maven
Oracle Database
Docker
Docker Compose
Git
```

For Kubernetes deployment:

```text
Minikube / Kubernetes
kubectl
```

---

## 1. Clone Repository

```bash
git clone https://github.com/KommulaPavan/HRMS.git
```

```bash
cd HRMS
```

---

## 2. Build Application

```bash
mvn clean install
```

---

## 3. Run Spring Boot

```bash
mvn spring-boot:run
```

Application:

```text
http://localhost:8080
```

---

# 🐳 Run With Docker Compose

```bash
docker-compose up --build
```

Stop containers:

```bash
docker-compose down
```

The Compose setup runs the HRMS application and Oracle database as separate containers.

---

# ☸️ Kubernetes Deployment

Apply Oracle:

```bash
kubectl apply -f oracle-deployment.yaml
kubectl apply -f oracle-service.yaml
```

Apply HRMS:

```bash
kubectl apply -f app-deployment.yaml
kubectl apply -f app-service.yaml
```

Check pods:

```bash
kubectl get pods
```

Check deployments:

```bash
kubectl get deployments
```

Check services:

```bash
kubectl get services
```

The application service is configured as a NodePort service using port `30007`.

---

# 📡 API Documentation

Swagger/OpenAPI support is enabled in the application configuration.

After starting the application, the configured Swagger path is:

```text
http://localhost:8080/swagger-ui.html
```

Use Swagger to explore and test available REST APIs.

---

# 🔑 Configuration

Application configuration includes:

```text
Server
Oracle Database
JPA/Hibernate
JWT
Email
Kafka
File Upload
Swagger/OpenAPI
Logging
```

For production, configuration should be provided through environment variables or a secrets-management system rather than hard-coded credentials.

---

# ⚠️ Security Notice

**Never commit production credentials to GitHub.**

Use environment variables for:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
MAIL_USERNAME
MAIL_PASSWORD
KAFKA_BOOTSTRAP_SERVERS
SONAR_TOKEN
```

Recommended approach:

```text
application.properties
        │
        └── Environment Variables
                    │
                    ▼
              Secret Values
```

Do not upload:

```text
Passwords
JWT secrets
SMTP passwords
API keys
Sonar tokens
Private certificates
Production credentials
```

---

# 📊 Key Features Summary

```text
✅ Employee Management
✅ User Management
✅ Role Management
✅ JWT Authentication
✅ Spring Security
✅ Account Activation
✅ Employee Profiles
✅ Profile Image Upload
✅ Attendance
✅ Check-In / Check-Out
✅ Leave Management
✅ Leave Approval
✅ Holiday Calendar
✅ Announcements
✅ Announcement Attachments
✅ Job Openings
✅ Payroll
✅ Payroll Calculation
✅ PDF Payslip
✅ Training Management
✅ Training Enrollment
✅ Training Progress
✅ Employee Performance Reviews
✅ Email Notifications
✅ Apache Kafka
✅ Outbox
✅ RestTemplate
✅ OpenFeign
✅ Oracle Database
✅ Docker
✅ Docker Compose
✅ Kubernetes
✅ Swagger/OpenAPI Configuration
✅ SonarQube Integration
```

---

# 🎯 Architecture Highlights

This project demonstrates the following enterprise development concepts:

### Backend

```text
Java 21
Spring Boot
Spring MVC
REST APIs
JPA / Hibernate
Oracle
```

### Security

```text
Spring Security
JWT
Password Encryption
Role-Based Authorization
Activation Tokens
```

### Integration

```text
RestTemplate
OpenFeign
Kafka
JavaMailSender
```

### Business Modules

```text
Employee
Attendance
Leave
Payroll
Training
Reviews
Announcements
Jobs
```

### DevOps

```text
Maven
Docker
Docker Compose
Kubernetes
SonarQube
Git/GitHub
```

---

# 💼 Resume Description

### HRMS – Human Resource Management System

Developed an enterprise-style **Human Resource Management System using Java 21 and Spring Boot**, implementing employee lifecycle management, JWT-based authentication, role-based authorization, employee profiles, attendance, leave management, payroll, training, performance reviews, announcements and job openings. Implemented **RestTemplate and OpenFeign** for service communication and **Apache Kafka** for asynchronous payroll events and notifications. Integrated Oracle Database using JPA/Hibernate, email notifications using JavaMailSender, PDF payslip generation, and containerized deployment using **Docker, Docker Compose and Kubernetes**. Added **SonarQube** integration for code quality analysis.

---

# 🗣️ Interview Project Explanation

> "I developed an HRMS application using Java 21 and Spring Boot. The system provides different functionality for employees, HR and administrators. I implemented JWT-based authentication and role-based authorization using Spring Security.
>
> The major modules include employee management, employee profiles, attendance, leave management, announcements, job openings, training, employee reviews and payroll.
>
> For payroll processing, the application retrieves employee and attendance information through service clients, calculates paid days, gross salary, PF, tax and net salary, saves the payroll record and publishes a payroll event through Kafka.
>
> I used RestTemplate and OpenFeign for service-to-service communication, Oracle with JPA/Hibernate for persistence, JavaMailSender for email notifications and OpenPDF for payslip generation.
>
> For deployment, I containerized the application using Docker and Docker Compose and also created Kubernetes deployment and service configurations. I also integrated SonarQube for code quality analysis."

---

# 👨‍💻 Author

**Pawan**

Java Developer | Spring Boot | REST APIs | Kafka | Oracle | Docker | Kubernetes

---

# ⭐ Project Architecture at a Glance

```text
                           ┌───────────────┐
                           │   FRONTEND    │
                           └───────┬───────┘
                                   │
                                   ▼
                     ┌─────────────────────────┐
                     │     SPRING BOOT API     │
                     └────────────┬────────────┘
                                  │
             ┌────────────────────┼────────────────────┐
             │                    │                    │
             ▼                    ▼                    ▼
       AUTH / JWT            HR MODULES          EMPLOYEE MODULES
             │                    │                    │
             │          ┌─────────┼─────────┐          │
             │          │         │         │          │
             │          ▼         ▼         ▼          │
             │       Leave     Training   Reviews      │
             │       Jobs      Announce                 │
             │                                           │
             └─────────────────┬─────────────────────────┘
                               │
                               ▼
                        ┌──────────────┐
                        │    ORACLE    │
                        │   DATABASE   │
                        └──────┬───────┘
                               │
                 ┌─────────────┴──────────────┐
                 │                            │
                 ▼                            ▼
             REST/FEIGN                     KAFKA
           SERVICE CALLS                     │
                 │                            ▼
                 │                       NOTIFICATION
                 │                            │
                 └────────────────────────────┤
                                              ▼
                                            EMAIL

                       DEPLOYMENT
                           │
                 ┌─────────┴──────────┐
                 ▼                    ▼
              DOCKER              KUBERNETES
                 │                    │
                 └─────────┬──────────┘
                           ▼
                    PRODUCTION READY
                     ARCHITECTURE
```

---

## 🔥 What this project demonstrates

This is more than a basic CRUD project. The strongest parts to highlight in interviews are:

**JWT + Spring Security → service communication with RestTemplate/OpenFeign → Oracle/JPA → Kafka event processing → payroll business logic → email notification → PDF generation → Docker → Kubernetes → SonarQube.**