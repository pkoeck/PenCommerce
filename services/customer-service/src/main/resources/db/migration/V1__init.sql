-- Customer Service initial schema (PostgreSQL)
-- Flyway migration V1

CREATE TABLE IF NOT EXISTS customer (
    id          BIGINT PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
