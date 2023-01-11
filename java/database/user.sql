-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER plant_db_owner
WITH PASSWORD 'finalcapstone';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO plant_db_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO plant_db_owner;

CREATE USER plant_db_appuser
WITH PASSWORD 'finalcapstone';

GRANT SELECT, INSERT, UPDATE, DELETE, ALTER
ON ALL TABLES IN SCHEMA public
TO plant_db_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO plant_db_appuser;
