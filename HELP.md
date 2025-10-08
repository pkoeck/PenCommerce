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
* [Liquibase Migration](https://docs.spring.io/spring-boot/3.5.6/how-to/data-initialization.html#howto.data-initialization.migration-tool.liquibase)
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
- spring.liquibase.enabled=true

Database initialization and creating the 'pencommerce' DB:
- The postgres service now mounts init scripts from ./docker/initdb into /docker-entrypoint-initdb.d.
- On the first run (new volume), an init script will create a second database named pencommerce owned by myuser, in addition to the default database (mydatabase).
- This allows services that point to jdbc:postgresql://localhost:5432/pencommerce to connect without error, while services still using mydatabase continue to work.
- If your Postgres data volume was initialized before this change, the init scripts will not re-run automatically. To recreate from scratch (DEV ONLY):
  - docker compose down -v
  - docker compose up -d

Typical local URLs:
- pencommerce DB: jdbc:postgresql://localhost:5432/pencommerce
- mydatabase DB:  jdbc:postgresql://localhost:5432/mydatabase

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


## Database & Liquibase migrations

Each microservice uses Liquibase with a master changelog that includes an initial changelog file:
- Master: src/main/resources/db/changelog/db.changelog-master.xml
- Included initial changelog: src/main/resources/db/changelog/changelog-initial.xml

Current state:
- catalog-service: changelog-initial.xml contains tables derived from JPA entities (products, materials, product_materials)
- order-service, customer-service, inventory-service, invoice-payment-service: changelog-initial.xml exists as an empty skeleton (no entities yet)

How to enable migrations locally with PostgreSQL (example):

1) Start infra with Docker Compose (from project root):
   - docker compose up -d

2) Configure a service to use the database (application-postgres.properties already contains these):
   - spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
   - spring.datasource.username=myuser
   - spring.datasource.password=secret
   - spring.liquibase.enabled=true
   - spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

3) Dependencies in each service POM (already added):
   - org.liquibase:liquibase-core
   - org.postgresql:postgresql

Notes:
- Tests are unaffected because no DataSource is configured by default; migrations won’t run unless you enable the postgres profile (see application-postgres.properties).
- We’re following the “Option C” approach: generate/author initial changelogs from entities. As more entities are added, create new Liquibase changesets to evolve the schema.



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



## Using Spring Data JPA with jOOQ (per service)

All microservices are pre-wired to use Spring Data JPA and jOOQ against PostgreSQL, with Liquibase migrations.

What’s included in each service:
- Dependencies: spring-boot-starter-data-jpa, org.jooq:jooq, org.postgresql:postgresql (runtime), liquibase-core
- Default profile (no DB): database auto-configurations are excluded by default so tests and boot run without a database
- Postgres profile: application-postgres.properties enables DataSource, JPA, jOOQ (dialect=POSTGRES), and Liquibase

How to run locally:
1) Start infra
   - docker compose up -d
2) Run a service with the postgres profile (examples):
   - Catalog:              ./mvnw -q -pl services/catalog-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Order:                ./mvnw -q -pl services/order-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Customer:             ./mvnw -q -pl services/customer-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Inventory:            ./mvnw -q -pl services/inventory-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Invoice Payment:      ./mvnw -q -pl services/invoice-payment-service -am spring-boot:run -Dspring-boot.run.profiles=postgres

Connection settings (shared by all services via application-postgres.properties):
- spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
- spring.datasource.username=myuser
- spring.datasource.password=secret
- spring.liquibase.enabled=true
- spring.jooq.sql-dialect=POSTGRES

Notes:
- Default behavior (no profile): DB auto-config is excluded to keep tests/boot fast and isolated. Use the postgres profile when you want DB access.
- Liquibase changelog lives under src/main/resources/db/changelog and runs when the postgres profile is active (db.changelog-master.xml includes changelog-initial.xml).
- Transactions are shared across JPA and jOOQ when using the same DataSource and @Transactional boundaries.



## IntelliJ Services tool window: shared Spring Boot run configurations

If the Services tool window only shows Docker/Kubernetes and not your microservices, use the shared run configurations we committed under .run/.

What we added:
- Shared Spring Boot Run/Debug configurations for each service:
  - catalog-service, order-service, customer-service, inventory-service, invoice-payment-service
  - Each also has a [postgres] variant that runs with -Dspring.profiles.active=postgres

How to use in IntelliJ IDEA:
1) Import the project via the root pom.xml (File > New > Project from Existing Sources > Maven).
2) Ensure the Spring Boot plugin is enabled (Settings > Plugins > Spring > Spring Boot).
3) Show the Services tool window (View > Tool Windows > Services).
4) Enable showing run configs in Services (Settings > Advanced Settings > Run/Debug > "Show run configurations in Services tool window").
5) Pick a config from the Run/Debug dropdown (top-right) and click Run. It will appear in Services automatically; you can pin it.
6) For database-backed runs, start infra first: docker compose up -d, then run the [postgres] configuration.

Notes:
- Default configurations run without any DB profile (fast startup). The [postgres] ones use application-postgres.properties.
- You can duplicate/modify these shared configs directly in .run/ if you need custom ports or JVM args.



## Data isolation policy: dev per-schema, prod per-database

We follow a clear isolation strategy across environments:

- Development/local (postgres profile): Each service uses its own PostgreSQL schema in a single database (mydatabase).
  - Schemas per service:
    - catalog-service → catalog
    - order-service → orders
    - customer-service → customers
    - inventory-service → inventory
    - invoice-payment-service → invoice_payment
  - Configuration: set via application-postgres.properties
    - spring.jpa.properties.hibernate.default_schema=<service_schema>
    - spring.liquibase.default-schema=<service_schema>
  - Schema creation: Each service’s Liquibase changelog-initial.xml includes a changeSet that runs:
    - CREATE SCHEMA IF NOT EXISTS <service_schema>;

How to run locally with per-schema isolation:
1) Start infra: docker compose up -d
2) Run a service with postgres profile (examples):
   - Catalog:              ./mvnw -q -pl services/catalog-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Order:                ./mvnw -q -pl services/order-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Customer:             ./mvnw -q -pl services/customer-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Inventory:            ./mvnw -q -pl services/inventory-service -am spring-boot:run -Dspring-boot.run.profiles=postgres
   - Invoice Payment:      ./mvnw -q -pl services/invoice-payment-service -am spring-boot:run -Dspring-boot.run.profiles=postgres

Notes:
- All services connect to jdbc:postgresql://localhost:5432/mydatabase with shared credentials from compose.yaml.
- Liquibase applies changes only within the service’s schema to keep data isolated during development.

- Production (prod profile): Each service uses its own database for stronger isolation.
  - Databases per service (defaults; adjust per environment):
    - catalog-service → catalogdb
    - order-service → orderdb
    - customer-service → customerdb
    - inventory-service → inventorydb
    - invoice-payment-service → invoicedb
  - Configuration files: application-prod.properties in each service
    - spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/<service_db>}
    - spring.datasource.username=${DB_USERNAME:myuser}
    - spring.datasource.password=${DB_PASSWORD:secret}
    - spring.liquibase.enabled=true
    - No default schema is set; Liquibase and JPA use the database’s default schema (typically public).

How to run with prod profile (example):
- Catalog: ./mvnw -q -pl services/catalog-service -am spring-boot:run -Dspring-boot.run.profiles=prod

Creating databases for production-like environments (run once as an admin):
- CREATE DATABASE catalogdb;
- CREATE DATABASE orderdb;
- CREATE DATABASE customerdb;
- CREATE DATABASE inventorydb;
- CREATE DATABASE invoicedb;

Tips:
- Do not combine postgres and prod profiles; choose one per run.
- If you later adopt jOOQ code generation, set inputSchema to the per-service schema for dev, and point to the per-service database for prod codegen.
