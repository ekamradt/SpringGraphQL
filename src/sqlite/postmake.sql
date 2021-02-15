-- =============================================================================
-- Post-Setup SDC table (sdc)
-- =============================================================================
ALTER TABLE sdc ADD column id integer;
UPDATE sdc SET id = ROWID;

ALTER TABLE baby ADD column id integer;
UPDATE baby SET id = ROWID;
