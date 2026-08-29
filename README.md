# Hermesafe - Backend Logistics & Distribution Microservice

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/PostgreSQL-16--alpine-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker%20Compose-Enabled-2496ED.svg)](https://www.docker.com/)
[![OpenAPI](https://img.shields.io/badge/Swagger-OpenAPI%203.0-green.svg)](https://swagger.io/)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2F%20DDD-blue.svg)]()
[![Build & Test](https://img.shields.io/badge/Tests-Passing-brightgreen.svg)]()

**Hermesafe** is a robust, production-ready backend microservice designed for logistics, warehouse inventory, freight rate calculations, and automated route optimization. Built with **Java 21** and **Spring Boot 3.3.4**, the system strictly adheres to **Domain-Driven Design (DDD)**, **Clean Architecture**, and **Test-Driven Development (TDD)** principles.

---

## 1. System Architecture & Tech Stack

### Core Technologies
* **Language & Runtime:** Java 21 (LTS)
* **Framework:** Spring Boot 3.3.4 (Spring Web, Spring Data JPA)
* **ORM & Database:** Hibernate 6 / PostgreSQL 16 (Alpine Container)
* **API Documentation:** Springdoc OpenAPI 2.6.0 (Swagger UI)
* **Virtualization:** Docker & Docker Compose
* **Testing:** JUnit 5, Mockito, Spring Test, MockMvc, JaCoCo

### Architectural Layers
* **Domain Layer (Pure Java):** Aggregates (`Order`, `Warehouse`, `Package`), Entities (`Route`, `InventoryItem`), Self-Validating Value Objects (`ProductId`, `Weight`, `Dimensions`, `Distance`, `ShippingRate`, `PostalCode`), and Domain Services (`RateCalculator`, `RouteOptimizer`). **100% framework-agnostic.**
* **Application Layer (Use Cases):** `ProcessOrderUseCase`, `CalculateShippingRateUseCase`, `OptimizeRouteUseCase`, `OrderService`.
* **Infrastructure Layer:**
  * **Persistence:** `InventoryItemEntity` (JPA Entity), `SpringDataInventoryRepository` (Spring Data JPA), `InventoryRepositoryAdapter`, `InventoryItemMapper`.
  * **Web (REST API):** Semantic controllers (`OrderController`, `ShippingRateController`, `RouteController`), DTOs with OpenAPI `@Schema` annotations, and perimetral exception handling (`GlobalExceptionHandler`).
  * **Security & Profiles:** `OpenApiConfig` isolated to development profile (`@Profile("dev")`), completely blocked in production for cybersecurity hardening.

---

## 2. Quick Start: Virtualized Environment with Docker

### Step 1: Start PostgreSQL Container
Hermesafe utilizes an ultra-lightweight PostgreSQL 16 Alpine container with data volume persistence (`postgres_data`).

```bash
docker compose up -d
```

Verify that the container is running and healthy:
```bash
docker compose ps
```

### Step 2: Run the Microservice (Development Mode)
Execute the Spring Boot application with the default `dev` profile:

```bash
mvn spring-boot:run
```
*(Or on Linux/macOS with wrapper: `./mvnw spring-boot:run`)*

The server will initialize at `http://localhost:8080/api` and Hibernate will automatically synchronize relational schemas (`ddl-auto: update`).

---

## 3. Interactive API Documentation (OpenAPI / Swagger UI)

Hermesafe provides a documented and interactive Swagger UI console for exploring contracts and testing requests with one click ("Try it out"):

* **Swagger UI Console:** [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)
* **OpenAPI 3.0 JSON Specification:** [http://localhost:8080/api/api-docs](http://localhost:8080/api/api-docs)

> **Cybersecurity Note (Environment Isolation):** Swagger UI and OpenAPI technical definitions are enabled exclusively in the development profile (`dev`). In production (`application-prod.yml`), access is strictly locked down (`springdoc.swagger-ui.enabled: false`, `springdoc.api-docs.enabled: false`) to eliminate attack surface.

---

## 4. REST API Endpoints Overview

| Tag | HTTP Method | Endpoint Path | Description | Expected Status Codes |
| :--- | :---: | :--- | :--- | :---: |
| **Orders & Stock** | `POST` | `/api/orders/process` | Process order and deduct warehouse stock | `200 OK`, `400 Bad Request`, `409 Conflict`, `422 Unprocessable` |
| **Orders & Stock** | `GET` | `/api/orders/stock/{productId}` | Retrieve current stock level for a product | `200 OK`, `400 Bad Request` |
| **Orders & Stock** | `POST` | `/api/orders/stock` | Add / replenish inventory catalog stock | `200 OK`, `400 Bad Request` |
| **Shipping Rates** | `POST` | `/api/shipping-rates/calculate` | Calculate shipping rate via JSON body | `200 OK`, `400 Bad Request`, `422 Unprocessable` |
| **Shipping Rates** | `GET` | `/api/shipping-rates/calculate` | Calculate shipping rate via query parameters | `200 OK`, `400 Bad Request`, `422 Unprocessable` |
| **Routes & Coverage**| `GET` | `/api/routes/closest-warehouses` | Get prioritized closest dispatch warehouses | `200 OK` |
| **Routes & Coverage**| `GET` | `/api/routes/coverage/{city}` | Check delivery coverage for a city | `200 OK` |

---

## 5. Contract Testing with Postman / Bruno

An integration test collection is included in the repository for auditing API contracts and status codes:

* **Collection File:** `docs/collections/hermesafe-postman-collection.json`

### Import into Bruno or Postman:
1. Open Bruno / Postman.
2. Click **Import** and select `docs/collections/hermesafe-postman-collection.json`.
3. Set the `baseUrl` variable to `http://localhost:8080/api`.
4. Execute test requests covering both success scenarios (200, 201) and domain business rule violations (400, 422).

---

## 6. Running Tests & Quality Verification

Run the complete automated test suite (Unit tests, `@DataJpaTest`, and MockMvc integration tests):

```bash
mvn clean test
```

Generate the JaCoCo code coverage report:
```bash
mvn jacoco:report
```
*Coverage report available at: `target/site/jacoco/index.html`*
