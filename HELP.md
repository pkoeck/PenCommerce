# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.6/maven-plugin/build-image.html)
* [Spring Boot Testcontainers support](https://docs.spring.io/spring-boot/3.5.6/reference/testing/testcontainers.html#testing.testcontainers)
* [Testcontainers Postgres Module Reference Guide](https://java.testcontainers.org/modules/databases/postgres/)
* [Testcontainers Pulsar Module Reference Guide](https://java.testcontainers.org/modules/pulsar/)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.5.6/reference/actuator/index.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.5.6/specification/configuration-metadata/annotation-processor.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.6/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.6/reference/using/devtools.html)
* [Docker Compose Support](https://docs.spring.io/spring-boot/3.5.6/reference/features/dev-services.html#features.dev-services.docker-compose)
* [Flyway Migration](https://docs.spring.io/spring-boot/3.5.6/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [Spring Modulith](https://docs.spring.io/spring-modulith/reference/)
* [Spring for Apache Pulsar](https://docs.spring.io/spring-boot/3.5.6/reference/messaging/pulsar.html)
* [Testcontainers](https://java.testcontainers.org/)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.6/reference/web/servlet.html)
* [WebSocket](https://docs.spring.io/spring-boot/3.5.6/reference/messaging/websockets.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml` and a root-level `Dockerfile` used to build a local Postgres image for development.
In this file, the following services have been defined:

* postgres: built from the local `Dockerfile` (which is based on `postgres:16-alpine`) with defaults (db `mydatabase`, user `myuser`, password `secret`), port `5432:5432` published
* pulsar: [`apachepulsar/pulsar:latest`](https://hub.docker.com/r/apachepulsar/pulsar) with ports `6650:6650` and `8080:8080` published; command `bin/pulsar standalone`

Please review the tags of the used images and set them to the same as you're running in production.

Quick start (from project root):
- docker compose up -d
- docker compose logs -f postgres

Connect from Spring Boot:
- spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
- spring.datasource.username=myuser
- spring.datasource.password=secret
- spring.flyway.enabled=true

### Testcontainers support

This project
uses [Testcontainers at development time](https://docs.spring.io/spring-boot/3.5.6/reference/features/dev-services.html#features.dev-services.testcontainers).

Testcontainers has been configured to use the following Docker images:

* [`postgres:latest`](https://hub.docker.com/_/postgres)
* [`apachepulsar/pulsar:latest`](https://hub.docker.com/r/apachepulsar/pulsar)

Please review the tags of the used images and set them to the same as you're running in production.

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


## Monorepo structure and usage

This repository is configured as a Maven multi-module monorepo. Each microservice is independently buildable and deployable.

Structure:
- Parent/aggregator: pom.xml (packaging=pom)
- Services:
  - services/catalog-service
  - services/order-service
  - services/customer-service
  - services/inventory-service
  - services/invoice-payment-service

Build everything:
- Windows PowerShell: ./mvnw -q -DskipTests package

Build and test a single service:
- Catalog Service:          ./mvnw -q -pl services/catalog-service -am test
- Order Service:            ./mvnw -q -pl services/order-service -am test
- Customer Service:         ./mvnw -q -pl services/customer-service -am test
- Inventory Service:        ./mvnw -q -pl services/inventory-service -am test
- Invoice Payment Service:  ./mvnw -q -pl services/invoice-payment-service -am test

Run locally:
- Catalog Service (port 8081):          ./mvnw -q -pl services/catalog-service -am spring-boot:run
- Order Service (port 8082):            ./mvnw -q -pl services/order-service -am spring-boot:run
- Customer Service (port 8083):         ./mvnw -q -pl services/customer-service -am spring-boot:run
- Inventory Service (port 8084):        ./mvnw -q -pl services/inventory-service -am spring-boot:run
- Invoice Payment Service (port 8085):  ./mvnw -q -pl services/invoice-payment-service -am spring-boot:run

Container images:
- Each service includes the Spring Boot Maven Plugin. You can build an OCI image per service:
  - Catalog:               ./mvnw -q -pl services/catalog-service -am spring-boot:build-image
  - Order:                 ./mvnw -q -pl services/order-service -am spring-boot:build-image
  - Customer:              ./mvnw -q -pl services/customer-service -am spring-boot:build-image
  - Inventory:             ./mvnw -q -pl services/inventory-service -am spring-boot:build-image
  - Invoice Payment:       ./mvnw -q -pl services/invoice-payment-service -am spring-boot:build-image

Notes:
- The compose.yaml currently defines infra dependencies (PostgreSQL, Pulsar). You can extend it to add app services if desired.
- Legacy source files at the repository root are no longer part of the build; new development should happen inside services/* modules.



## GitHub Integration

This repository is preconfigured to work well on GitHub.

- Continuous Integration: `.github/workflows/ci.yml` builds and tests the monorepo on every push and pull request across Ubuntu and Windows runners using Java 21.
- Dependency Updates: `.github/dependabot.yml` enables weekly updates for Maven dependencies and GitHub Actions.
- Contribution Hygiene: `.github/pull_request_template.md` standardizes PRs; `.github/CODEOWNERS` is provided as a placeholder for ownership rules.

### Connect your local repo to GitHub

1) Create a new repository on GitHub (e.g., `PenCommerce`).
2) From the project root in Windows PowerShell, run:

```
# If not already a git repo
git init

# Commit your work (if not committed yet)
git add .
git commit -m "Initial monorepo setup"

# Use main as default branch
git branch -M main

# Point to your GitHub repo (replace YOUR-ACCOUNT)
git remote add origin https://github.com/YOUR-ACCOUNT/PenCommerce.git

# Push the branch and set upstream
git push -u origin main
```

If the repository is already initialized and committed, you typically only need:

```
git remote add origin https://github.com/YOUR-ACCOUNT/PenCommerce.git
git push -u origin main
```

### CI Badge (optional for README)

You can add this badge to a `README.md` to show CI status:

```
[![CI](https://github.com/YOUR-ACCOUNT/PenCommerce/actions/workflows/ci.yml/badge.svg)](https://github.com/YOUR-ACCOUNT/PenCommerce/actions/workflows/ci.yml)
```


## Database & Flyway migrations

Each microservice now includes an initial Flyway SQL migration under:
- src/main/resources/db/migration/V1__init.sql

These migrations define a minimal, Postgres‑compatible starting schema for each bounded context:
- catalog-service: product table and index
- order-service: orders and order_item tables (with FK)
- customer-service: customer table
- inventory-service: inventory table (unique by product)
- invoice-payment-service: invoice and payment tables (with FK)

How to enable migrations locally with PostgreSQL (example):

1) Start infra with Docker Compose (from project root):
   - docker compose up -d

2) Configure a service to use the database (application.properties):
   - spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
   - spring.datasource.username=myuser
   - spring.datasource.password=secret
   - spring.flyway.enabled=true

3) Optionally add dependencies in the service POM when you’re ready to run migrations at startup:
   - org.flywaydb:flyway-core
   - org.postgresql:postgresql

Notes:
- Tests are unaffected because no DataSource is configured by default; migrations won’t run unless you add the datasource and Flyway dependency.
- See the Flyway docs for naming/versioning conventions.



## Sharing code among services

In this monorepo, the recommended way to share code is to extract it into dedicated library modules under libs/ and have services depend on those libraries. Do not create compile‑time dependencies between microservices themselves.

- Create a library module (example already included): libs/common-lib
- Keep shared code service‑agnostic (e.g., common DTOs, validation, utilities, error models)
- Avoid placing service‑specific domain logic in shared libs
- Prefer small, focused libraries over one giant shared module

How to depend on a shared library from a service (example for common-lib):

```
<!-- In services/<service>/pom.xml -->
<dependency>
  <groupId>com.pkswoodhouse</groupId>
  <artifactId>common-lib</artifactId>
  <version>${project.version}</version>
</dependency>
```

Notes:
- Version alignment: since all modules share the same parent version, ${project.version} keeps everything in sync.
- Testing: each library module should include its own unit tests. Services using the library should have integration tests covering the combined behavior.
- Independence: never add a dependency from one service module to another service module. Only depend on shared libraries under libs/.
