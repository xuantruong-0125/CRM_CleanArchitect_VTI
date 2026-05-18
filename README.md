# NAMING CONVENTION – CLEAN ARCHITECTURE (CRM PROJECT)

## 1. Quy tắc chung

* Sử dụng **PascalCase** cho class: `UserService`, `UserController`
* Sử dụng **camelCase** cho biến, method: `getUserById()`
* Tên phải **rõ nghĩa + theo domain**, tránh viết tắt
* Mỗi class chỉ đại diện **1 responsibility**
* Luôn đặt tên theo format:
  **[Entity] + [Purpose]**

Ví dụ:

* `UserService`
* `UserRepository`
* `UserController`

---

## 2. Domain Layer (Core Business)

### 2.1 Entity

* Format: `[EntityName]`
* Ví dụ:

  * `User`
  * `Order`
  * `Customer`

---

### 2.2 Repository (interface)

* Format: `[EntityName]Repository`
* Ví dụ:

  * `UserRepository`
  * `OrderRepository`

---

### 2.3 Exception

* Format: `[EntityName][Meaning]Exception`
* Ví dụ:

  * `UserNotFoundException`
  * `InvalidUserException`

---

### 2.4 Constant / Enum

* Format:

  * Enum: `[EntityName][Type]`
  * Constant class: `[EntityName]Constants`
* Ví dụ:

  * `UserRole`
  * `UserStatus`

---

## 3. Application Layer (Use Case)

### 3.1 Service

* Interface:

  * `[EntityName]Service`
* Implementation:

  * `[EntityName]ServiceImpl`

Ví dụ:

* `UserService`
* `UserServiceImpl`

---

### 3.2 DTO

* Format:

  * Request: `[EntityName]RequestDTO`
  * Response: `[EntityName]ResponseDTO`
  * Create: `Create[EntityName]DTO`
  * Update: `Update[EntityName]DTO`

Ví dụ:

* `UserRequestDTO`
* `UserResponseDTO`
* `CreateUserDTO`
* `UpdateUserDTO`

---

### 3.3 Mapper

* Format: `[EntityName]Mapper`
* Ví dụ:

  * `UserMapper`

---

## 4. Infrastructure Layer (DB, External)

### 4.1 JPA Repository

* Format: `[EntityName]JpaRepository`
* Ví dụ:

  * `UserJpaRepository`

---

### 4.2 Repository Implementation

* Format: `[EntityName]RepositoryImpl`
* Ví dụ:

  * `UserRepositoryImpl`

---

### 4.3 Persistence Entity (nếu tách riêng)

* Format: `[EntityName]Entity`
* Ví dụ:

  * `UserEntity`

---

### 4.4 Config

* Format: `[Purpose]Config`
* Ví dụ:

  * `JpaConfig`
  * `DatabaseConfig`

---

### 4.5 External Client

* Format: `[EntityName]Client` hoặc `[ServiceName]Client`
* Ví dụ:

  * `UserClient`
  * `AuthClient`

---

## 5. Presentation Layer (Controller)

### 5.1 Controller

* Format: `[EntityName]Controller`
* Ví dụ:

  * `UserController`

---

### 5.2 Request / Response (API layer)

* Format:

  * `[EntityName]Request`
  * `[EntityName]Response`

Ví dụ:

* `UserRequest`
* `UserResponse`

---

### 5.3 Exception Handler

* Format:

  * Global: `GlobalExceptionHandler`
  * Specific: `[EntityName]ExceptionHandler`

Ví dụ:

* `UserExceptionHandler`

---

## 6. Quy tắc Mapper giữa các Layer

* Domain ↔ Infrastructure:

  * `User ↔ UserEntity`

* Domain ↔ DTO:

  * `User ↔ UserResponseDTO`

* Tất cả xử lý mapping đặt trong:

  * `UserMapper`

---

## 7. Quy tắc đặt tên Method

### Service

* `createUser()`
* `getUserById()`
* `updateUser()`
* `deleteUser()`

---

### Repository

* `findById()`
* `save()`
* `deleteById()`
* `findByEmail()`

---

### Controller

* `createUser()`
* `getUser()`
* `updateUser()`
* `deleteUser()`

---

## 8. Anti-pattern (Cần tránh)

* ❌ `UserService2`, `UserServiceNew`
* ❌ `HandleUser`, `ProcessData`
* ❌ `UserUtils` (quá chung chung)
* ❌ Viết tắt: `UsrSvc`, `UsrRepo`

---

## 9. Ví dụ hoàn chỉnh (User)

* `User`
* `UserRepository`
* `UserService`
* `UserServiceImpl`
* `UserJpaRepository`
* `UserRepositoryImpl`
* `UserController`
* `UserMapper`
* `UserRequestDTO`
* `UserResponseDTO`

---

## 10. Nguyên tắc quan trọng

* Domain không phụ thuộc layer khác
* Infrastructure implement Domain
* Controller chỉ gọi Service
* Tên file phải phản ánh đúng vai trò trong kiến trúc

---
