-- Inventory Service initial schema (PostgreSQL)
-- Flyway migration V1

CREATE TABLE IF NOT EXISTS inventory (
    id          BIGINT PRIMARY KEY,
    product_id  BIGINT NOT NULL,
    sku         VARCHAR(64),
    quantity    INTEGER NOT NULL DEFAULT 0,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_inventory_product UNIQUE (product_id)
);
