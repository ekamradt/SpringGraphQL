-- =============================================================================
-- Setup SDC table (sdc)
-- =============================================================================
.read SDC.sql
.mode csv sdc
.import SDC-ORIG.csv sdc

.read baby.sql
.mode csv baby
.import baby.csv baby

.read sdc_main.sql
.mode csv sdc_main
.import sdc_main.csv sdc_main

.mode csv sdc_detail
.import sdc_detail.csv sdc_detail


