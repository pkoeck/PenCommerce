#!/usr/bin/env sh
set -e

# This script runs on first container initialization when mounted to
# /docker-entrypoint-initdb.d. It ensures that the 'pencommerce' database
# exists, creating it if necessary and owned by POSTGRES_USER.

DB_TO_CREATE="pencommerce"

echo "[initdb] Ensuring database '$DB_TO_CREATE' exists..."
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<'EOSQL'
SELECT format('CREATE DATABASE %I OWNER %I', 'pencommerce', current_user)
WHERE NOT EXISTS (
  SELECT 1 FROM pg_database WHERE datname = 'pencommerce'
)\gexec
EOSQL

echo "[initdb] Done."
