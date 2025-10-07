-- Invoice Payment Service initial schema (PostgreSQL)
-- Flyway migration V1

CREATE TABLE IF NOT EXISTS invoice (
    id         BIGINT PRIMARY KEY,
    order_id   BIGINT NOT NULL,
    amount     NUMERIC(12,2) NOT NULL CHECK (amount >= 0),
    status     VARCHAR(32) NOT NULL,
    issued_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payment (
    id          BIGINT PRIMARY KEY,
    invoice_id  BIGINT NOT NULL,
    amount      NUMERIC(12,2) NOT NULL CHECK (amount >= 0),
    method      VARCHAR(32),
    paid_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_invoice FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE CASCADE
);
