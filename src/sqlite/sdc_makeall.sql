-- =============================================================================
-- Setup SDC table (sdc)
-- =============================================================================
.read sdc_main.sql
.mode csv sdc
.import SDC-ORIG.csv sdc

.read baby.sql
.mode csv baby
.import baby.csv baby
