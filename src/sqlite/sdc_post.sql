-- =============================================================================
-- Post-Setup SDC table (sdc)
-- =============================================================================
ALTER TABLE sdc_main ADD column id integer;
UPDATE sdc_main SET id = ROWID;

ALTER TABLE sdc_detail ADD column id integer;
UPDATE sdc_detail SET id = ROWID;
