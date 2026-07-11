# SQL Scripts

Use `schema.sql` as the main setup script. It recreates the Oracle schema, sequences, triggers, demo data and default login accounts required by the web application.

## Main Setup

| Script | Purpose | When to run |
| --- | --- | --- |
| `schema.sql` | Full database bootstrap for a clean local environment. | First setup, or whenever you want to recreate the demo database from scratch. |

## Maintenance

The scripts in `maintenance/` are optional repair helpers. They are not required for a fresh import if `schema.sql` was executed successfully.

| Script | Purpose | When to run |
| --- | --- | --- |
| `maintenance/sync_sequences.sql` | Recreates sequences so the next generated ID is `MAX(id) + 1` for each table. | After manual database edits/imports if generated IDs become too high or out of sync. |
| `maintenance/cleanup_angajati.sql` | Cleans invalid employee demo rows and resets the employee sequence. | Only if the local `Angajati` table contains test/header rows from imports. |
| `maintenance/fix_furnizori_trigger.sql` | Recreates the supplier trigger used by older local databases. | Only if Oracle reports `ORA-04098` for `FURNIZORI_BEFORE_INSERT`. |
