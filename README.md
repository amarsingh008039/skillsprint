# SkillSprintLite

SkillSprintLite is a lightweight, secure backend service built with Spring Boot that serves coding challenges to users. It supports role-based access control, JWT-based authentication, Redis API key security, PostgreSQL for persistent storage, and follows a modular architecture using design patterns like Strategy and Layered Architecture.

---

## Features

- Fetch topic-based coding challenges from PostgreSQL
- Secure authentication with JWT tokens
- Redis-based API key check for select endpoints
- Role-based authorization (`ADMIN`, `USER`)
- Strategy Pattern used for topic-specific logic (e.g., templates, hints, test cases)
- Global exception handling (except Redis interceptor by design)
- Automatic schema and table creation via JPA (`ddl-auto: update`)

---

## Tech Stack

| Layer            | Tools/Tech                          |
|------------------|-------------------------------------|
| Backend          | Java 17, Spring Boot                |
| Database         | PostgreSQL                         |
| Caching/Auth Key | Redis                              |
| Auth             | JWT, Spring Security                |
| Design Pattern   | Strategy Pattern, Layered Architecture |
| Dev Tools        | Lombok, IntelliJ, Postman           |

---

## 🧱 Architecture

Client
↓
[Controller Layer]
↓
[Service Layer]
↓
[Repository Layer → PostgreSQL]

[Security Layer]
↳ JWT filter for protected APIs
↳ RedisAuthInterceptor for select API key access

[Strategy Layer]
↳ DSAChallengeStrategyImpl
↳ SQLChallengeStrategyImpl
↳ OSChallengeStrategyImpl

---

## Design Pattern: Strategy

Each topic (e.g., DSA, SQL, OS) implements a specific strategy class.  
These are used to **enrich challenge responses** by attaching:
- Code templates
- Hints
- Sample test cases  
...all dynamically fetched from a **metadata table in PostgreSQL**.

Example:
```java
strategy.enrichChallengeDetails(response, "EASY");

# Folder Structure
src/main/java/
├── controller/
├── service/
├── strategy/
├── repository/
├── security/
├── model/
├── exception/
└── config/


# Future Enhancements
Admin dashboard UI for challenge management

LLM integration to generate challenges dynamically

Swagger/OpenAPI documentation

Rate-limiting via Redis
