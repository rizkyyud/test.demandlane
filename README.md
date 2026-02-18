# _**Demandlane Library REST API**_

This project is a RESTful API for a small-scale library management system developed as part of a technical assessment for Demandlane.

The latest implementation is available in:
`feature/book-module`

---
### _**Tech Stack**_
* Java 17 
* Spring Boot 3.5.10 
* Spring Web 
* Spring Data JPA 
* Spring Validation 
* Spring Security (Basic Auth)
* Spring Boot Actuator 
* H2 In-Memory Database 
* Lombok 
* Springdoc OpenAPI (Swagger UI)
* Maven
---
### _**Project Overview**_

The system manages:
* Books 
* Members 
* Loans 

It is designed with clean layered architecture, consistent API contracts, traceable logging, and role-based access control.

Although scoped for interview purposes, the structure is intentionally built to reflect production-oriented practices.

---
### **_Architecture & Design Decisions_**

##### **Layered Architecture**

The application follows a clear separation of concerns:

* ###### Controller Layer
  * Handles HTTP requests 
  * Responsible for request validation trigger (`@Valid`)
  * Returns standardized API responses
* ###### Service Layer
  * Contains business logic 
  * Coordinates validation rules 
  * Encapsulates transactional operations
* ###### Validator Layer
  * Handles domain-specific validation rules 
  * Keeps controllers thin 
  * Improves testability and maintainability
* ###### Repository Layer
  * Uses Spring Data JPA 
  * Abstracts persistence logic
* ###### Model Layer
  * Entities: `Book`, `Member`, `Loan`
  * DTOs:
    * `RequestBook`
    * `RequestMember`
    * `RequestLoan`
  * Custom `ApiResponse<T>` for consistent output structure

This separation ensures:
* Scalability 
* Maintainability 
* Easier unit testing 
* Clear responsibility boundaries
---
### **_Security Strategy_**

###### **Authentication**

The system uses **Basic Authentication** with InMemoryUserDetailsManager.

###### **Why In-Memory?**
* Simpler setup 
* No need for external identity provider 
* Focus remains on business logic

---
### **_Authorization (Role-Based Access Control)_**
Two roles are defined:

### **Role**

`ADMIN` Full access (manage entities + actuator)

`USER`	Read-only access to book and member

Access rules are clearly defined in SecurityConfig using endpoint-based authorization.

---
### **_API Response Standardization_**
All responses (except DELETE) use a unified wrapper:

`{
"success": true,
"traceId": "uuid-value",
"message": "Operation successful",
"data": {}
}`

---
### **_Request Tracing & Observability_**
A custom TraceIdFilter generates a unique UUID per request and stores it in MDC.

Example log format:

`Validation Error [trace-id]: message`

---
### **_Validation Strategy_**

Two types of validation are implemented:

### **1. Bean Validation**
Using:
* `@NotNull`
* `@NotBlank`
* `@Valid`

Handled automatically via `MethodArgumentNotValidException.`
### **2. Business Rule Validation**
Custom validator classes ensure:
* Domain consistency
* Clear business error messaging 
* Separation between technical and domain validation
---
### **_Actuator & Monitoring_**
* `/actuator/health` → Public
* `/actuator/**` → ADMIN only
---
### **_API Documentation_**
Swagger UI is available at:
`/swagger-ui/index.html`
---
### **_Assumptions_**
* A loan cannot exist without a valid member and book. 
* A book may have multiple loans. 
* Only ADMIN can modify data. 
* USER role is read-only.

