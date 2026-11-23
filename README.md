# 🔐 Reactive API with Multi-Factor Authentication

A fully **reactive REST API** built with **Spring WebFlux** and **reactive MongoDB**, protected by **JWT authentication** and **Multi-Factor Authentication (MFA)** using **TOTP** codes (the 6-digit codes from Google Authenticator, Authy, and similar apps).

It is a small but complete example of how to secure a non-blocking Spring application end to end: sign up, log in, verify a one-time password, and then call protected endpoints with a JWT.

## ✨ What it does
- ⚡ **Non-blocking** from the HTTP layer to the database (WebFlux + Reactive MongoDB)
- 🧭 **Functional routing** with `RouterFunction` and handler classes instead of `@RestController`
- 🔑 **JWT authentication** with a custom reactive `AuthenticationManager` and token converter
- 📱 **TOTP-based MFA**: a secret is generated per user and a QR code can be scanned with an authenticator app
- ✉️ **Email service** (Spring Mail) for account verification
- 🎓 A protected **Student** CRUD resource to try the security flow on

## 🛠️ Tech stack
| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.4, Spring WebFlux |
| Security | Spring Security (reactive), JJWT, TOTP library |
| Database | MongoDB (reactive driver) |
| Email | Spring Boot Starter Mail |
| Container | Docker Compose (MongoDB) |
| Testing | JUnit 5, Reactor Test, Spring Security Test |

## 🔄 Authentication flow
~~~text
1. POST /api/v1/auth/signup      -> user is created and gets a TOTP secret (QR code for an authenticator app)
2. POST /api/v1/auth/login       -> username + password are checked
3. POST /api/v1/otp/verify       -> the 6-digit code from the authenticator app is verified, a JWT is issued
4. Authorization: Bearer <jwt>   -> call any protected endpoint
~~~

## 🚀 Run it

### Prerequisites
- Java 17+ and Maven
- Docker (for MongoDB)

### 1. Start MongoDB
~~~bash
docker compose up -d
~~~

### 2. Configure secrets
Set the JWT secret as an environment variable (never commit real secrets):
~~~bash
export JWT_SECRET_KEY=a-long-random-secret-of-at-least-256-bits
~~~
Review `src/main/resources/application.yml` for the MongoDB connection and the SMTP settings used to send emails.

### 3. Start the API
~~~bash
mvn spring-boot:run
~~~

## 📡 API tour

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/auth/signup` | Register a new user |
| `POST` | `/api/v1/auth/login` | Log in with username and password |
| `POST` | `/api/v1/otp/verify` | Verify the TOTP code and receive a JWT |
| `GET` | `/api/v1/protected` | Example endpoint that requires a valid JWT |

### Students (protected)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/student/all` | List all students |
| `POST` | `/api/v1/student/add` | Add a student |
| `PUT` | `/api/v1/student/update` | Update a student |
| `DELETE` | `/api/v1/student/delete` | Delete a student |

## 🏗️ Project structure
~~~text
src/main/java/net/developer/space/reactive_api
├── configuration   # Security, MFA (TOTP + QR) and user details beans
├── dto             # Request / response objects
├── entity          # User, Role, Student, EmailConfirmationToken
├── exception       # Custom security and domain exceptions
├── repository      # Reactive MongoDB repositories
├── router          # Functional routes (AuthRouter, ServiceRouter)
├── services        # Handlers, JWT authentication manager, email service
└── utils           # JWT helpers and token provider
~~~

## ⚙️ Configuration
| Property | Description |
|----------|-------------|
| `jwt.secret-key` | Signing key for JWTs (env: `JWT_SECRET_KEY`) |
| `jwt.token-expiration-seconds` | JWT lifetime |
| `spring.data.mongodb.*` | MongoDB connection |
| `spring.mail.*` | SMTP server used to send emails |
| `server.port` | HTTP port |

## 🗺️ Roadmap
- [ ] Move all credentials to environment variables
- [ ] Add integration tests with Testcontainers (MongoDB)
- [ ] Add OpenAPI documentation
- [ ] Add a GitHub Actions build

## 👤 Author

**Lazaro Noel Guerra Medina**, Java Backend Engineer

> *"Make it work, make it right, make it fast."* ☕
