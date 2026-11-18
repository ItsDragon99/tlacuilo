#!/usr/bin/env bash
set -Eeuo pipefail

psql \
  --username "$POSTGRES_USER" \
  --dbname "$POSTGRES_DB" \
  -v ledger_owner_pw="$LEDGER_OWNER_PW" \
  -v ledger_app_pw="$LEDGER_APP_PW" \
  -v payments_owner_pw="$PAYMENTS_OWNER_PW" \
  -v payments_app_pw="$PAYMENTS_APP_PW" \
  -v accounts_owner_pw="$ACCOUNTS_OWNER_PW" \
  -v accounts_app_pw="$ACCOUNTS_APP_PW" \
  -v risks_owner_pw="$RISKS_OWNER_PW" \
  -v risks_app_pw="$RISKS_APP_PW" \
  -v ON_ERROR_STOP=1 \
  --file /docker-entrypoint-initdb.d/01-init.sql.template