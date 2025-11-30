# Rent and Repair Shop

This is a Spring Boot application that manages a surfboard rental and repair shop. It uses PostgreSQL as the database.

## Features

- Manage surfboard rentals and repairs.
- RESTful APIs for managing surfboards, rentals, and repairs.
- PostgreSQL for data persistence.

## Prerequisites

- Java 17 or higher
- Maven
- Docker (for PostgreSQL)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd rent-and-repair-shop
```

### 2. Start PostgreSQL

Use Docker Compose to start the required services:

```bash
docker-compose up -d
```

### 3. Run the Application

Build and run the application:

```bash
./mvnw spring-boot:run
```

### 4. Test the Application

Use curl or a tool like Postman to test the REST APIs. For example:

```bash
curl -X GET http://localhost:8080/api/surfboards/all
```

## Project tree

```sh
.
|-- docker-compose.yml
|-- LICENSE
|-- pom.xml
|-- README.md
`-- src
    `-- main
        |-- java
        |   `-- com
        |       `-- example
        |           `-- shop
        |               |-- config
        |               |   |-- CorsConfig.java
        |               |   `-- SeedData.java
        |               |-- controller
        |               |   |-- RentalController.java
        |               |   |-- RepairController.java
        |               |   `-- SurfboardController.java
        |               |-- dto
        |               |   |-- RentalMessage.java
        |               |   |-- RentalRequest.java
        |               |   |-- RepairMessage.java
        |               |   `-- RepairRequest.java
        |               |-- messaging
        |               |-- model
        |               |   |-- Rental.java
        |               |   |-- Repair.java
        |               |   `-- Surfboard.java
        |               |-- RentAndAndRepairShopApplication.java
        |               |-- repository
        |               |   |-- RentalRepository.java
        |               |   |-- RepairRepository.java
        |               |   `-- SurfboardRepository.java
        |               `-- service
        |                   |-- BillingService.java
        |                   |-- InventoryService.java
        |                   |-- RentalService.java
        |                   `-- RepairService.java
        `-- resources
            |-- application.yml
            `-- data.sql
```

## Configuration

### Database

The application uses PostgreSQL. Update the database connection details in src/main/resources/application.yml if needed.

### API Endpoints

#### Billings `/api/bills`

- `GET /all`

    List all bills
- `GET /customer/{customerId}`

    List bills by customer
- `GET /board/{surfboardId}`

    List bills by surfboard

#### Rentals `/api/rentals`

- `POST /`

    Create new rental
- `POST /{rentalId}/return`

    Return rented board
- `GET /all`

    List all rentals
- `GET /user/{customerId}`

    Filter by customerId
- `GET /status/{status}`

    Filter by status (e.g., REQUESTED, RENTED, RETURNED)

#### Repairs `/api/repairs`

- `POST /`
- `GET /all`
- `GET /customer/{customerId}`
- `POST /{repairId}/complete`
- `POST /{repairId}/cancel`

#### Surfboards `/api/surfboards`

- `GET /all`

## License

This project is licensed under the MIT License.

---
> **Note:** The canonical repository is [**on GitHub**](https://github.com/vr33ni-dev/rent-and-repair-shop-spring) · [Mirror on GitLab →](https://gitlab.com/vr33ni-personal/rent-and-repair-shop-spring) [![Mirror Status](https://github.com/vr33ni-dev/rent-and-repair-shop-spring/actions/workflows/gitlab-mirror.yml/badge.svg)](https://github.com/vr33ni-dev/rent-and-repair-shop-spring/actions/workflows/gitlab-mirror.yml)
