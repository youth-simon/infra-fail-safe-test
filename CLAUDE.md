# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

```bash
# Build all modules
./gradlew build

# Build a specific module
./gradlew :apps:commerce-api:build
./gradlew :apps:pg-simulator:build

# Run tests (all)
./gradlew test

# Run tests for a specific module
./gradlew :apps:commerce-api:test

# Run a single test class
./gradlew :apps:commerce-api:test --tests "com.failsafe.domain.example.ExampleModelTest"

# Lint (ktlint)
./gradlew ktlintCheck

# Auto-fix lint issues
./gradlew ktlintFormat

# Run application (requires Docker infra running)
./gradlew :apps:commerce-api:bootRun
./gradlew :apps:pg-simulator:bootRun
```

## Local Infrastructure

```bash
# MySQL 8.0 (port 3306) + Redis master/replica (ports 6379/6380)
docker-compose -f ./docker/infra-compose.yml up

# Prometheus (port 9090) + Grafana (port 3000, admin/admin)
docker-compose -f ./docker/monitoring-compose.yml up
```

Tests use Testcontainers (MySQL, Redis) — no Docker infra needed for `./gradlew test`.

## Architecture

**Multi-module Gradle project** — Kotlin 2.0.20, Spring Boot 3.4.4, Java 21.

### Module Layout

- **apps/** — Bootable Spring Boot applications (produce fat JARs)
  - `commerce-api` — Main commerce service (port 8080, actuator 8081)
  - `pg-simulator` — Payment Gateway simulator with async payment processing (port 8082, actuator 8083)
- **modules/** — Shared infrastructure modules (library JARs)
  - `jpa` — HikariCP datasource, JPA/QueryDSL config, BaseEntity with audit columns
  - `redis` — Master/replica LettuceConnectionFactory, two templates (default=REPLICA_PREFERRED, master=MASTER)
- **supports/** — Cross-cutting concern modules (library JARs)
  - `jackson` — Shared ObjectMapper configuration
  - `logging` — Profile-based logback (plain console for local/test, JSON for dev/qa/prd)
  - `monitoring` — Micrometer Prometheus metrics, health probes

### Layered Architecture (per app)

Each app follows a strict 4-layer structure under `com.failsafe`:

```
interfaces/        → REST controllers, DTOs, API specs (Swagger), event listeners, argument resolvers
application/       → Facades/services that orchestrate domain logic, Info/Command objects
domain/            → Entities, domain services, repository interfaces, events, value objects
infrastructure/    → Repository implementations, JPA repositories, external HTTP relays, event publishers
```

Dependencies flow inward: interfaces → application → domain ← infrastructure.

### Key Conventions

- **Package base:** `com.failsafe`
- **Repository pattern:** Domain defines interface (`PaymentRepository`), infrastructure implements it (`PaymentCoreRepository`)
- **DTO mapping:** `companion object { fun from(source) }` pattern at every layer boundary
- **Error handling:** `CoreException(ErrorType)` with `ErrorType` enum mapping to HTTP status codes. `ApiControllerAdvice` handles all exceptions uniformly via `ApiResponse` wrapper
- **Validation:** Init blocks in entities, `validate()` methods in DTOs and Commands
- **Events:** Domain events (`PaymentEvent.PaymentCreated`) published via `ApplicationEventPublisher`, consumed by `@Async @TransactionalEventListener(phase = AFTER_COMMIT)` listeners
- **Transactions:** `@Transactional` for writes, `@Transactional(readOnly = true)` for reads
- **API versioning:** Controller names include version (`ExampleV1Controller`), API specs as interfaces (`ExampleV1ApiSpec`)
- **Tests run with:** `spring.profiles.active=test`, timezone `Asia/Seoul`, `maxParallelForks = 1`

### Test Patterns

- **Unit tests:** Domain entity/value object tests with `@Nested` and `@DisplayName`
- **Integration tests:** `@SpringBootTest` with `DatabaseCleanUp` (TRUNCATE all tables in `@AfterEach`) and `RedisCleanUp`
- **E2E tests:** `@SpringBootTest(webEnvironment = RANDOM_PORT)` with `TestRestTemplate`
- **Test fixtures:** `modules/jpa` and `modules/redis` provide `testFixtures` with Testcontainers configs and cleanup utilities

### PG Simulator Async Payment Flow

1. Client POST → `PaymentApi` (simulated 100-500ms delay, 40% request failure rate)
2. `Payment` entity created as PENDING → `PaymentCreated` event published
3. Async listener delays 1-5s, then processes (70% success, 20% limit exceeded, 10% invalid card)
4. `PaymentHandled` event → HTTP callback to `callbackUrl`
