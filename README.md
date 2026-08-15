# Hermesafe - Backend Logistics & Distribution System

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2F%20DDD-blue.svg)]()


**Hermesafe** is a backend solution designed for logistics, inventory, and shipment management. Built with **Java 21**, the system strictly adheres to **Domain-Driven Design (DDD)**, **Clean Architecture**, and **Test-Driven Development (TDD)** principles.

---

## Architectural Pillars

- **Clean Architecture**: Clear layer boundaries between `domain`, `application`, and `infrastructure`. The core domain is 100% pure Java with zero framework or library dependencies.
- **Tactical DDD Patterns**:
  - **Aggregates & Entities**: `Order`, `Warehouse`, `Package` (Shipment), `Route`, and `InventoryItem` with strongly typed IDs (`OrderId`, `WarehouseId`, `PackageId`, `RouteId`, `ProductId`, `CustomerId`).
  - **Self-Validating Value Objects**: `Dimensions` (with volumetric weight calculation), `ShippingRate` (with immutable arithmetic and surcharges), `Weight`, `Distance`, `Location`, `EstimatedTime`, and `PostalCode` (regex validated).
  - **Domain Services**: Encapsulated business algorithms for rate calculations (`RateCalculator`) and route optimization (`RouteOptimizer`).
- **Decoupled Persistence**: Repository interface contracts defined in the domain layer, with thread-safe in-memory implementations provided in the infrastructure layer.

---

## Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── hermesafe/
                ├── domain/                                 # DOMAIN LAYER (Pure Java, 0 Frameworks)
                │   ├── entity/                             # Entities & Aggregate Roots
                │   │   ├── InventoryItem.java              # Inventory stock entity
                │   │   ├── Order.java                      # Order aggregate root
                │   │   ├── Package.java                    # Physical package shipment entity
                │   │   ├── Warehouse.java                  # Warehouse aggregate root with stock management
                │   │   ├── Route.java                      # Warehouse-to-warehouse route entity
                │   │   ├── OrderStatus.java                # Enum (CREATED, PROCESSED, CANCELLED)
                │   │   └── ShipmentStatus.java             # Enum (PENDING, IN_TRANSIT, DELIVERED)
                │   ├── valueobject/                        # Self-Validating & Rich Value Objects
                │   │   ├── ProductId.java                  # Strongly typed Product ID
                │   │   ├── OrderId.java                    # Strongly typed Order ID
                │   │   ├── PackageId.java                  # Strongly typed Package ID
                │   │   ├── CustomerId.java                 # Strongly typed Customer ID
                │   │   ├── WarehouseId.java                # Strongly typed Warehouse ID
                │   │   ├── RouteId.java                    # Strongly typed Route ID
                │   │   ├── Dimensions.java                 # Dimensions (L x W x H) & volumetric weight
                │   │   ├── Location.java                   # Location (City, Address)
                │   │   ├── EstimatedTime.java              # Estimated transit time in hours
                │   │   ├── PostalCode.java                 # Regex validated postal code
                │   │   ├── Weight.java                     # Weight VO (in kg)
                │   │   ├── Distance.java                   # Distance VO (in km)
                │   │   └── ShippingRate.java               # Immutable monetary rate with operations
                │   ├── exception/                          # Domain Exceptions
                │   │   ├── InsufficientStockException.java
                │   │   ├── InvalidPostalCodeException.java
                │   │   ├── InvalidWeightException.java
                │   │   ├── InvalidDimensionsException.java
                │   │   └── InvalidOrderStatusException.java
                │   ├── repository/                         # Domain Repository Contracts (Interfaces)
                │   │   ├── InventoryRepository.java
                │   │   ├── OrderRepository.java
                │   │   ├── WarehouseRepository.java
                │   │   ├── RouteRepository.java
                │   │   └── PackageRepository.java
                │   └── service/                            # Domain Services
                │       ├── InventoryManager.java
                │       ├── PostalCodeValidator.java
                │       ├── RateCalculator.java
                │       └── RouteOptimizer.java
                │
                ├── application/                            # APPLICATION LAYER (Use Cases)
                │   ├── usecase/                            # Isolated Use Cases
                │   │   ├── ProcessOrderUseCase.java
                │   │   ├── CalculateShippingRateUseCase.java
                │   │   └── OptimizeRouteUseCase.java
                │   ├── service/                            # Application Services
                │   │   └── OrderService.java
                │   └── port/                               # Output Ports
                │       └── NotificationPort.java
                │
                └── infrastructure/                         # INFRASTRUCTURE LAYER
                    └── persistence/                        # Concrete Repository Implementations
                        ├── InMemoryInventoryRepository.java
                        ├── InMemoryOrderRepository.java
                        ├── InMemoryWarehouseRepository.java
                        └── InMemoryRouteRepository.java
```

---

## Getting Started

### Prerequisites
- **Java**: JDK 21 or higher
- **Build Tool**: Apache Maven 3.8+

### Build & Compilation
To compile the project and verify all classes:
```bash
mvn clean compile
```

### Run Tests
To run the complete suite of unit tests:
```bash
mvn test
```

### Code Coverage Report
To generate the JaCoCo code coverage report:
```bash
mvn jacoco:report
```
The HTML coverage report will be generated at `target/site/jacoco/index.html`.
