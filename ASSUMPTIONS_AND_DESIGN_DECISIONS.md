# Assumptions & Design Decisions

## Project

**Warehouse Allocation System**

**Author:** Harshitha NM

---

# Assumptions

### 1. Warehouse Capacity

* Each warehouse has a fixed storage capacity.
* Capacity is provided during warehouse creation.
* A warehouse cannot exceed its defined capacity.

---

### 2. Product Availability

* A product may exist in multiple warehouses.
* Inventory is maintained separately for each warehouse.

---

### 3. Stock Allocation

* Allocation is performed only if sufficient stock is available.
* The system automatically selects the warehouse with the highest available quantity.
* Warehouses marked as inactive or soft-deleted are ignored during allocation.

---

### 4. Inventory

* Available quantity can never become negative.
* Reserved quantity increases whenever stock is allocated.
* Optimistic locking (`@Version`) is used to prevent concurrent inventory updates.

---

### 5. Stock Transfer

* Stock transfer is allowed only if the source warehouse has sufficient available stock.
* Both source and destination inventories are updated within a single transaction.
* Every transfer is recorded in the `StockTransfer` table.

---

### 6. Warehouse Deletion

* Warehouses are soft deleted.
* Soft-deleted warehouses remain in the database for audit purposes.
* Soft-deleted warehouses cannot receive allocations or stock transfers.

---

### 7. Audit Logging

* Every allocation operation creates an audit log.
* Every stock transfer creates an audit log.
* Audit records store operation type, entity, user, timestamp, and remarks.

---

# Design Decisions

## Layered Architecture

The project follows a clean layered architecture:

* Controller Layer
* Service Layer
* Repository Layer
* Entity Layer
* DTO Layer
* Mapper Layer

This separation improves maintainability and scalability.

---

## Spring Boot

Spring Boot was selected to reduce configuration and simplify REST API development.

---

## Spring Data JPA

Spring Data JPA is used for database operations to reduce boilerplate CRUD code.

---

## MySQL

MySQL is used as the relational database because it is reliable, widely used, and integrates well with Spring Boot.

---

## DTO Pattern

DTOs are used to separate API requests/responses from database entities, improving security and flexibility.

---

## Optimistic Locking

`@Version` is used in the `WarehouseInventory` entity to prevent lost updates during concurrent stock allocation.

---

## Global Exception Handling

A centralized `GlobalExceptionHandler` provides consistent API error responses.

---

## Swagger/OpenAPI

Swagger is integrated to automatically generate interactive API documentation.

---

## Pagination & Sorting

Pagination and sorting are implemented to improve performance when handling large datasets.

---

## Audit Logging

Audit logs provide traceability for inventory allocation and stock transfer operations.

---

## Validation

Bean Validation (`jakarta.validation`) ensures that invalid data is rejected before reaching the business layer.

---

# Future Improvements

* JWT Authentication
* Role-Based Access Control (RBAC)
* Docker Deployment
* Redis Caching
* Kafka Event Streaming
* Email Notifications
* Dashboard & Analytics
* Cloud Deployment (AWS/Azure)

---

# Conclusion

The Warehouse Allocation System is designed using enterprise-level best practices. The application supports warehouse management, product allocation, stock transfers, audit logging, optimistic locking, and RESTful APIs while maintaining a clean layered architecture suitable for future enhancements.
