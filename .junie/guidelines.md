# Project Guidelines

Project type: Maven multi-module monorepo with multiple Spring Boot microservices.

Project structure:
- Root aggregator POM: pom.xml (packaging=pom)
- Microservices under services/:
  - services/catalog-service
  - services/order-service
- Infra compose file: compose.yaml (PostgreSQL, Pulsar)

Build instructions (Windows PowerShell):
- Build everything (skip tests): ./mvnw -q -DskipTests package
- Build and test all: ./mvnw -q verify
- Build/test a single service:
  - Catalog: ./mvnw -q -pl services/catalog-service -am test
  - Order:   ./mvnw -q -pl services/order-service -am test
- Run a single service locally:
  - Catalog (port 8081): ./mvnw -q -pl services/catalog-service -am spring-boot:run
  - Order (port 8082):   ./mvnw -q -pl services/order-service -am spring-boot:run

Testing policy:
- Always run tests for affected modules before submitting.
- Prefer module-scoped runs using -pl to minimize time and scope.

Code style:
- Java 21
- Use standard Spring Boot conventions and prefer constructor injection.
- Keep each microservice independent; do not create compile-time dependencies between services.

Notes:
- Legacy source files under the repository root are not part of the build; new code should live in services/* modules.
