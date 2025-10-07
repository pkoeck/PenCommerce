-- Order Service initial schema (PostgreSQL)
-- Flyway migration V1

CREATE TABLE IF NOT EXISTS orders (
    id            BIGINT PRIMARY KEY,
    customer_id   BIGINT NOT NULL,
    status        VARCHAR(32) NOT NULL,
    total_amount  NUMERIC(12,2) NOT NULL CHECK (total_amount >= 0),
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_item (
    id          BIGINT PRIMARY KEY,
    order_id    BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    sku         VARCHAR(64),
    quantity    INTEGER NOT NULL CHECK (quantity > 0),
    unit_price  NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE INDEX idx_order_item_order_id ON order_item(order_id);
