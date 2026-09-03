# 📦 Hermesafe - Full-Stack Integration (Backend Microservice)

Comprehensive logistics management, shipment tracking, freight rate calculation, and dispatch route optimization platform developed following **Clean Architecture**, **Domain-Driven Design (DDD)**, and **Test-Driven Development (TDD)** principles with relational persistence in **PostgreSQL 16**.

---

## 🛠️ Tech Stack
* **Backend:** Java 21, Spring Boot 3 (3.3.4), Spring Data JPA, Hibernate 6, OpenAPI 3.0 / Springdoc Swagger UI.
* **Frontend:** TypeScript Vanilla (Zero `any`), Vite, Native ESM, Tailwind CSS v4, Semantic HTML5/CSS3.
* **Infrastructure:** Docker Compose, PostgreSQL 16 Alpine with persistent volumes (`postgres_data`).
* **Quality & Testing:** JUnit 5, Mockito, Spring Test, MockMvc, JaCoCo, H2 In-Memory DB (Hermetic testing suite).

---

## 🔗 Reference Repositories
* Core Domain / Milestone 1: https://github.com/sebavidal10/neonpulse-ticketera
* Backend Spring Boot / Milestone 4: https://github.com/sebavidal10/neonpulse-api-springboot
* Frontend Vite + TS / Milestone 2: https://github.com/sebavidal10/neonpulse-frontend

---

## 🚀 Local Quick Start Guide

### 1. Start the Relational Database
```bash
cd backend
docker compose up -d
```
*Verify container status with: `docker compose ps` (must be running on port `5432:5432`).*

### 2. Run Automated Tests
```bash
./mvnw clean test
```
*(On Windows PowerShell / CMD: `.\mvnw.cmd clean test`)*
* 100% of unit and integration test suite passing (90/90 tests).
* JaCoCo coverage report generated via: `./mvnw jacoco:report` at `target/site/jacoco/index.html`.

### 3. Start Backend Microservice
```bash
./mvnw spring-boot:run
```
*(On Windows PowerShell / CMD: `.\mvnw.cmd spring-boot:run`)*
* **Base REST API:** `http://localhost:8080/api/shipments`
* **Swagger UI (Dev Profile):** `http://localhost:8080/swagger-ui.html`
* **OpenAPI 3.0 JSON Spec:** `http://localhost:8080/api-docs`

### 4. Start Frontend Web Interface
```bash
cd ../frontend
npm install
npm run dev
```
* **Web App:** `http://localhost:5173`

---

## 🏛️ Clean Architecture & Layered Design (DDD)

1. **Domain Layer (`domain/` - Pure Java):**
   * Zero Spring or JPA annotations (framework-agnostic).
   * Pure entities: `Shipment`, `Order`, `Package`, `Route`, `Warehouse`, `InventoryItem`.
   * Self-validating Value Objects: `Weight`, `Dimensions`, `Distance`, `ShippingRate`, `PostalCode`, `ProductId`.
   * Pure repository interfaces: `ShipmentRepository`, `InventoryRepository`, `OrderRepository`.
2. **Application Layer (`application/`):**
   * Decoupled use cases: `CreateShipmentUseCase`, `ListShipmentsUseCase`, `GetShipmentUseCase`, `UpdateShipmentStatusUseCase`, `ProcessOrderUseCase`, `CalculateShippingRateUseCase`.
3. **Infrastructure Layer (`infrastructure/`):**
   * Relational persistence: `ShipmentJpaEntity` (`@Entity`, `@Table(name = "shipments")`), `SpringDataShipmentRepository`, `ShipmentRepositoryAdapter`.
   * REST controllers with `@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})`:
     * `/api/shipments` - Shipment parcel lifecycle and tracking management.
     * `/api/orders` - Transactional customer order processing and warehouse stock inventory.
     * `/api/shipping-rates` - Dynamic freight tariff calculations.
     * `/api/routes` - Delivery coverage checking and optimal warehouse dispatching.
   * Centralized exception handling: `GlobalExceptionHandler` mapping business domain errors (`422 Unprocessable Entity`), client validation issues (`400 Bad Request`), and unhandled server errors (`500 Internal Server Error`).

---

## 🔒 Cybersecurity & Profile Isolation
* **Isolated Swagger UI:** Exclusively enabled under the development profile (`dev`). In production (`application-prod.yml`), Swagger UI and OpenAPI documentation endpoints are strictly locked down (`springdoc.swagger-ui.enabled: false`, `springdoc.api-docs.enabled: false`) to minimize attack surface.
* **Secret Exclusion:** Strict `.gitignore` configured to exclude `.env`, `.env.*`, `target/`, and `application-prod.yml`.
