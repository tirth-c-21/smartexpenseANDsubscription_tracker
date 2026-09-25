#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE "SExpenseTrackerDB_Expenseservice";
    CREATE DATABASE "SExpenseTrackerDB_subscriptionservice";
EOSQL