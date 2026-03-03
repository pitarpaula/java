# 🍰 Cake Shop Management Application (Java)

## 📌 Overview

This project is a Java application for managing cake orders in a cake shop, developed as part of a university assignment and extended incrementally across multiple labs.
The application follows layered architecture principles, supports multiple persistence mechanisms, includes unit testing, and demonstrates good software engineering practices.

It is designed to be easy to extend, configuration-driven, and independent of the persistence layer.

---


## 🧱 Architecture

The application follows a Layered Architecture, clearly separating responsibilities:
```text
├── domain        // Core business entities
├── repository    // Generic and specific data access layers
├── service       // Business logic
├── ui            // User Interface (CLI & JavaFX)
├── config        // Application configuration
├── exceptions    // Custom exception hierarchy
└── tests         // JUnit unit tests
```

### Layers Description

- Domain Layer
  - Contains core entities (Cake, Order)
  - All entities have a unique ID
  - Entities derive from a common abstract base class / interface
- Repository Layer
  - Generic Repository<T> interface
  - Multiple implementations:
    - In-memory
    - Text file
    - Binary file (serialization)
    - SQL database
  - Repository type is selected at runtime via configuration
- Service Layer
  - Implements business logic
  - Validates data
  - Uses Java Streams for reports and aggregations
  - Independent of repository implementation
- UI Layer
  - Console-based menu (CLI)
  - JavaFX graphical interface
  - Application mode selectable via configuration

---

## 📦 Domain Model

### Entities

🧁 Cake

```
id: int
type: String
```

📋 Order

```
id: int
cakes: List<Cake>
date: Date
```

Each order contains at least one cake.
An order can include multiple cakes of different types.

---

## 🔧 Features

### CRUD Operations

- Create, read, update, delete:
  - Cakes
  - Orders

### Validation & Error Handling

- Unique ID validation
- Entity not found checks
- File/database errors
- All errors handled via custom Java exceptions

### Reporting (Java Streams)

Implemented at service level:
- 📅 Number of cakes ordered per day (descending)
- 📆 Number of cakes ordered per month (descending)
- ⭐ Most ordered cakes (by number of orders)

---

## 💾 Persistence Options

The application behavior is fully configurable using a `settings.properties` file:

```properties
repo_type=database
Ingredient=ingredients.bin
Reteta=retete.bin
```

### Supported Repositories

```text
| Type       | Description                 |
| ---------- | --------------------------- |
| memory     | in-memory storage           |
| text       | text file storage           |
| binary     | binary file (serialization) |
| database   | SQL database (JDBC)         |
```

---

## 🖥️ User Interface

### Console UI (CLI)

- Menu-based navigation
- Full CRUD support
- Exception messages displayed to user

### JavaFX GUI

- Graphical interface for managing cakes and orders
- Same service layer as CLI
- Enabled via configuration

---

## 🧪 Testing

- Implemented using JUnit
- Over 90% test coverage (excluding UI layer)
- Tests included for:
  - Repositories
  - Services
  - Validation logic

---

## 🛠 Technologies Used

- Java 8
- Java Streams
- JavaFX
- JDBC
- JUnit
- Properties configuration
- Object Serialization

---

## 🎯 What This Project Demonstrates

- Clean architecture & separation of concerns
- Generic programming in Java
- Multiple persistence strategies
- Configuration-driven behavior
- Unit testing & validation
- Practical use of Java Streams
- Scalable and extensible design

---
