# ECA Gym Member Service

## 👤 Student Information
- **Name**: R.K. Sachintha Prabashana
- **Student ID**: 241722032
- **GCP Project ID**: `fitbuddy-505618`

## ## Project Description
The Member Service manages user accounts, roles, gym registrations, trainer assignments, and profile picture media uploads to Google Cloud Storage.

## ## Technology Stack
- **Language**: Java 25
- **Framework**: Spring Boot 4.0.1 / 4.1.0
- **Database**: PostgreSQL
- **Cloud Storage**: Google Cloud Storage (GCS) SDK
- **Build Tool**: Maven

## ## Project Structure
```
member-service/
├── src/
│   ├── main/
│   │   ├── java/lk/ijse/eca/memberservice/
│   │   │   ├── MemberServiceApplication.java # Entry point
│   │   │   ├── config/      # GCS configurations, Security beans
│   │   │   ├── controller/  # REST Endpoints for users & members
│   │   │   ├── dto/         # Request & Response model contracts
│   │   │   ├── entity/      # JPA entities (User, Member, Trainer, Role)
│   │   │   ├── exception/   # Custom domain exceptions
│   │   │   ├── handler/     # Exception interceptors
│   │   │   ├── mapper/      # MapStruct mapping classes
│   │   │   ├── repository/  # Spring Data JPA Repositories
│   │   │   ├── security/    # JWT filters & encryption
│   │   │   ├── seeder/      # Initial database data seeds (admin, roles)
│   │   │   ├── service/     # Core Business logic & GCS upload client
│   │   │   ├── util/        # Constants and helpers
│   │   │   └── validation/  # Custom annotation validators
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── application-dev.yaml
│   └── test/
├── pom.xml
└── README.md
```

## ## Setup / Getting Started Instructions
1. Navigate to the `member-service` directory.
2. Build the Maven package:
   ```bash
   ./mvnw clean install
   ```
3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. By default, the service starts on a dynamic port and registers itself automatically with Eureka Service Discovery.
