# syntax=docker/dockerfile:1
# Local development Postgres image for PenCommerce
# This image is intended for running the database locally and can be extended later
# if you want to add other lightweight dev tools. For multiple services, prefer
# using docker compose (see compose.yaml).

FROM postgres:16-alpine

LABEL org.opencontainers.image.title="PenCommerce Local Postgres" \
      org.opencontainers.image.description="PostgreSQL for local development (PenCommerce)" \
      org.opencontainers.image.source="https://github.com/YOUR-ACCOUNT/PenCommerce" \
      org.opencontainers.image.licenses="MIT"

# Default credentials for local usage (override at runtime as needed)
ENV POSTGRES_DB=mydatabase \
    POSTGRES_USER=myuser \
    POSTGRES_PASSWORD=secret

# Placeholder for optional init scripts (you can mount your own at runtime)
# See: https://hub.docker.com/_/postgres (docker-entrypoint-initdb.d)
RUN mkdir -p /docker-entrypoint-initdb.d

# Expose the default Postgres port
EXPOSE 5432

# Basic healthcheck using pg_isready
HEALTHCHECK --interval=5s --timeout=3s --start-period=10s --retries=10 \
  CMD pg_isready -U "$POSTGRES_USER" -d "$POSTGRES_DB" || exit 1
