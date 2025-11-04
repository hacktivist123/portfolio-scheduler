# INITS Portfolio Interest Update Scheduler

A Spring Boot application that automatically updates the interest on user portfolios every day based on a fixed annual interest rate of 15% per annum. It demonstrates clean, layered architecture (Controller → Service → Repository → Entity), scheduling using`@Scheduled`, JPA persistence with H2, and RESTful APIs.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [Scheduled Task Behavior](#scheduled-task-behavior)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Configuration](#configuration)

## Overview

This project simulates a daily interest accrual system for user portfolios. It uses a Spring Boot Scheduler to automatically calculate and update interest every midnight (configurable via CRON).

Each portfolio record stores:

- Owner email
- Portfolio name (e.g., Savings, Real Estate)
- Total value
- Interest rate (default 15%)
- Last updated timestamp

The scheduler fetches all portfolios from the database, applies simple interest, updates their total value, and logs the result.

## Features

- Fetch all portfolios
- Fetch portfolio by ID
- Create a new portfolio
- Automatically update daily interest
- Configurable interest rate and schedule via application.properties
- Centralized exception handling
- Preloaded sample portfolios via data.sql
- H2 in-memory database
- Integration tests using H2 (Repository, Service, Controller)

## Architecture

This follows a clean layered architecture:

```txt
Controller → Service (Interface + Impl) → Repository → Entity
```

Each layer has a specific role:

| Layer | Responsibility |
|-------|----------------|
| Controller | Exposes REST APIs |
| Service | Business logic and interest calculation |
| Repository | JPA interface to the H2 database |
| Entity | Maps database table structure |
| Scheduler | Background job that updates interest daily |
| DTOs | Data transfer for requests and responses |
| Mapper | Converts between Entity and DTO |
| Exception | Handles custom and global errors |

## Technologies Used

- Java 21
- Spring Boot 3.5.7
- Spring Data JPA
- Spring Scheduling
- H2 Database
- Lombok
- JUnit 5 / Spring Boot Test
- MockMvc (for integration testing)
- Maven

## Project Structure

```asciidoc
src/
└── main/
    ├── java/com/example/portfolio/
    │   ├── controller/
    │   │   └── PortfolioController.java
    │   ├── dto/
    │   │   ├── PortfolioRequestDTO.java
    │   │   └── PortfolioResponseDTO.java
    │   ├── mapper/
    │   │   └── PortfolioMapper.java
    │   ├── model/
    │   │   └── Portfolio.java
    │   ├── repository/
    │   │   └── PortfolioRepository.java
    │   ├── scheduler/
    │   │   └── PortfolioInterestScheduler.java
    │   ├── service/
    │   │   ├── PortfolioService.java
    │   │   └── impl/PortfolioServiceImpl.java
    │   ├── exception/
    │   │   ├── PortfolioNotFoundException.java
    │   │   ├── ApiErrorResponse.java
    │   │   └── GlobalExceptionHandler.java
    │   └── InitsPortfolioSchedulerApplication.java
    └── resources/
        ├── application.properties
        ├── data.sql
        └── schema.sql
```

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/inits-portfolio-scheduler.git
cd inits-portfolio-scheduler
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

## Database Configuration

The application uses H2 in-memory database with the following configuration:

- **Console URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:portfolio_db`
- **Username**: `sa`
- **Password**: (empty)

The database automatically loads with two sample portfolios from `data.sql`:

```sql
INSERT INTO portfolios (id, owner_email, total_value, portfolio_name, interest_rate, last_updated)
VALUES
(RANDOM_UUID(), 'john.doe@example.com', 100000.00, 'Savings', 0.15, CURRENT_TIMESTAMP),
(RANDOM_UUID(), 'jane.smith@example.com', 250000.00, 'Real Estate', 0.15, CURRENT_TIMESTAMP);
```

## API Endpoints

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | `/api/portfolios` | Get all portfolios |
| GET | `/api/portfolios/{id}` | Get a single portfolio by ID |
| POST | `/api/portfolios` | Create a new portfolio |

### Example Request (POST)

```json
{
  "ownerEmail": "john.doe@example.com",
  "portfolioName": "Crypto",
  "totalValue": 50000.00
}
```

### Example Response

```json
{
  "id": "2f9f6c7d-934a-45df-b091-7f3b530f3f30",
  "ownerEmail": "john.doe@example.com",
  "portfolioName": "Crypto",
  "totalValue": 50000.00,
  "interestRate": 0.15,
  "lastUpdated": "2025-11-04T12:00:00"
}
```

## Scheduled Task Behavior

The scheduler runs every midnight by default (configurable in `application.properties`).

### Configuration

```properties
# Cron schedule for daily job (every 1 minute for testing)
portfolio.interest.schedule=0 */1 * * * *
portfolio.interest.rate=0.15
```

### Process

1. Fetch all portfolios
2. Calculate daily interest: `interest = totalValue × (annualRate / 365)`
3. Add interest to totalValue
4. Update lastUpdated timestamp
5. Save all portfolios and log updates

Example console log:

```text
[INFO] Daily interest updated for 2 portfolios
```

## Error Handling

The application uses centralized exception handling to provide clean API responses.

Example error response:

```json
{
  "timestamp": "2025-11-04T12:45:30",
  "message": "Portfolio not found with ID: 1234",
  "details": "uri=/api/portfolios/1234"
}
```

Handled globally by:

```java
@RestControllerAdvice
public class GlobalExceptionHandler { ... }
```

## Testing

All layers are tested using H2 in-memory database:

- **PortfolioRepositoryH2Test** - validates JPA mappings
- **PortfolioServiceH2Test** - checks logic and persistence
- **PortfolioControllerH2Test** - integration tests via MockMvc

To run all tests:

```bash
mvn test
```

Each test profile uses its own H2 database (`jdbc:h2:mem:testdb`) and loads schema dynamically.

## Configuration

| Property | Description | Default |
|----------|-------------|---------|
| `spring.datasource.url` | H2 database URL | `jdbc:h2:mem:portfolio_db` |
| `spring.jpa.hibernate.ddl-auto` | Schema creation strategy | `update` |
| `portfolio.interest.rate` | Default annual interest rate | `0.15` |
| `portfolio.interest.schedule` | Cron expression for scheduler | `0 0 0 * * *` |
| `spring.h2.console.enabled` | Enable H2 web console | `true` |