# Warehouse Allocation System

## Project Overview

The Warehouse Allocation System is an enterprise-level Spring Boot application that manages product inventory across multiple warehouses. It automatically allocates products based on available stock, supports stock transfers between warehouses, maintains audit logs, and provides REST APIs for warehouse and inventory management.

---

## Technologies Used

* Java 19
* Spring Boot 3.5.6
* Spring Data JPA
* MySQL
* Maven
* Lombok
* Swagger/OpenAPI
* JUnit 5
* Mockito

---

## Features

* Warehouse CRUD Operations
* Soft Delete for Warehouses
* Product Management
* Warehouse Inventory Management
* Automatic Stock Allocation
* Stock Transfer Between Warehouses
* Allocation History
* Audit Logging
* Pagination and Sorting
* Search Allocation by Product, Warehouse and Date
* Global Exception Handling
* Swagger API Documentation
* Optimistic Locking using `@Version`

---

## Project Structure

```
src
 ├── controller
 ├── dto
 │    ├── request
 │    └── response
 ├── entity
 ├── enums
 ├── exception
 ├── mapper
 ├── repository
 ├── service
 ├── service.impl
 ├── config
 └── resources
```

---

## Database Configuration

Database Name:

```
warehouse_allocation_system
```

Update `application.properties` if required.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/warehouse_allocation_system?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC

spring.datasource.username=root

spring.datasource.password=YOUR_PASSWORD
```

---

## How to Run the Project

### Step 1

Clone the project.

```
git clone <repository-url>
```

### Step 2

Open the project in Spring Tool Suite (STS) or IntelliJ IDEA.

### Step 3

Update MySQL username and password in `application.properties`.

### Step 4

Run MySQL Server.

### Step 5

Run the Spring Boot application.

The database will be created automatically.

---

## Swagger Documentation

Open the following URL after starting the application.

```
http://localhost:8080/swagger-ui/index.html
```

or

```
http://localhost:8080/swagger-ui.html
```

---

## REST APIs

### Warehouse

* POST /api/warehouses
* GET /api/warehouses
* GET /api/warehouses/{id}
* PUT /api/warehouses/{id}
* DELETE /api/warehouses/{id}
* PATCH /api/warehouses/{id}/activate
* PATCH /api/warehouses/{id}/deactivate

### Product

* POST /api/products
* GET /api/products
* GET /api/products/{id}
* PUT /api/products/{id}
* DELETE /api/products/{id}

### Inventory

* POST /api/inventory
* GET /api/inventory
* GET /api/inventory/{id}
* PUT /api/inventory/{id}
* DELETE /api/inventory/{id}
* GET /api/inventory/warehouse/{warehouseId}
* GET /api/inventory/product/{productId}

### Allocation

* POST /api/allocations
* GET /api/allocations/search

### Stock Transfer

* POST /api/transfers
* GET /api/transfers
* GET /api/transfers/{id}

---

## Testing

Run all JUnit test cases using Maven.

```
mvn test
```

---

## Assumptions

* Each warehouse can store multiple products.
* A product can exist in multiple warehouses.
* Allocation always selects the warehouse with the highest available stock.
* Warehouses marked as deleted are excluded from allocations.
* Inventory quantity cannot become negative.
* Stock transfer is allowed only when sufficient stock exists.
* Audit logs are generated for allocation and stock transfer operations.

---

## Future Enhancements

* JWT Authentication
* Role-Based Authorization
* Email Notifications
* Dashboard and Analytics
* Docker Support
* Redis Caching
* Kafka Event Streaming

---

## Author

Harshitha
