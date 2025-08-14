# BillFella – Technical Requirements Document (TRD v1)

## 1. System Overview
BillFella is a **full-stack subscription and recurring payment management service**.  
The backend is built with **Spring Boot (Java)**, the frontend with **React**, and **PostgreSQL** serves as the primary database.  
The application uses **JWT authentication** to secure user data and **Docker Compose** for consistent local and production environments.

## 2. Architecture
**Type:** Client–Server, RESTful API  

**Components:**
- **Frontend:** React (Vite) Single-Page Application (SPA) calling backend APIs.
- **Backend:** Spring Boot 3.5.x (Java 21), running REST endpoints.
- **Database:** PostgreSQL 16 (Dockerized).
- **Database Migration:** Flyway for schema versioning.
- **Authentication:** JWT with Spring Security.
- **Background Jobs:** Spring Scheduler for email reminders.

**High-Level Diagram:**
```
[React Client] <----HTTP JSON----> [Spring Boot API] <----JDBC----> [PostgreSQL]
```

## 3. Modules & Responsibilities
| Module | Package | Description |
|--------|---------|-------------|
| Health Check | `com.billfella.billfella_api` | Verifies API uptime (`/api/health`). |
| DB Check | `com.billfella.billfella_api` | Verifies DB connectivity and schema. |
| Auth | `com.billfella.billfella_api.auth` | Handles registration, login, JWT issuance. |
| User | `com.billfella.billfella_api.user` | User entity, repository, and profile endpoints. |
| Subscription | `com.billfella.billfella_api.subscription` | CRUD operations for subscriptions. |
| Notification | `com.billfella.billfella_api.notification` | Scheduling and sending email reminders. |

## 4. API Endpoints (v1 Scope)
| Method | URL | Description | Auth |
|--------|-----|-------------|------|
| GET | `/api/health` | Server status check | ❌ |
| GET | `/api/db-check` | DB connectivity check | ❌ |
| POST | `/api/auth/register` | Create a new account | ❌ |
| POST | `/api/auth/login` | Login and receive JWT | ❌ |
| GET | `/api/subscriptions` | List user’s subscriptions | ✅ |
| POST | `/api/subscriptions` | Create new subscription | ✅ |
| PUT | `/api/subscriptions/{id}` | Update subscription | ✅ |
| DELETE | `/api/subscriptions/{id}` | Delete subscription | ✅ |

## 5. Database Schema (v1 Scope)
**users**
| Column | Type | Description |
|--------|------|-------------|
| id | UUID (PK) | Unique user ID |
| email | varchar(255), unique | Login email |
| password_hash | varchar(255) | Hashed password |
| display_name | varchar(255) | Display name |
| created_at | timestamptz | Creation timestamp |

**subscriptions**
| Column | Type | Description |
|--------|------|-------------|
| id | UUID (PK) | Unique subscription ID |
| user_id | UUID (FK) | Owner ID |
| name | varchar(255) | Service name |
| amount | numeric(10,2) | Subscription cost |
| cycle | varchar(50) | Billing cycle (monthly, yearly, etc.) |
| start_date | date | Subscription start date |
| notes | text | Notes or remarks |
| created_at | timestamptz | Creation timestamp |

## 6. Security Requirements
- All API communication over HTTPS.
- JWT tokens (HS256, 32-byte secret key).
- Passwords hashed with BCrypt.
- Database credentials stored in environment variables, not in code.
- Separate DB users for development and production with minimal privileges.

## 7. Performance & Scalability
**Initial Target:**  
- < 1,000 users in MVP stage.
- Single-instance backend deployment.

**Future Scaling:**
- Move DB to AWS RDS (PostgreSQL).
- Introduce Redis for caching and session storage.
- Deploy on Kubernetes for auto-scaling.

## 8. Deployment Process (v1)
1. Merge code to `main` branch.
2. GitHub Actions build → Create Docker image.
3. Deploy via `docker-compose pull && docker-compose up -d`.
4. Flyway runs DB migrations automatically.

## 9. Monitoring & Logging
- **Monitoring:** Spring Boot Actuator endpoints (`/actuator/health`, `/actuator/info`).
- **Logging:** JSON structured logs via Logback.
- **Error Tracking:** Optional Sentry integration for production.

## 10. Risks & Mitigation
| Risk | Mitigation |
|------|------------|
| JWT theft | Short TTL, use refresh tokens. |
| Data loss | Regular DB backups. |
| Missed reminders | Retry logic + failure logging. |
| Performance degradation | Avoid N+1 queries, add DB indexes. |

## 11. Out of Scope (v1)
- Automatic subscription detection from external services (planned for v2).
- Push notifications (planned for v2).
- Multi-currency support.
