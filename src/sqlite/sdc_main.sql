
drop table sdc_main;
create table sdc_main (
  main_id             integer
, state               text
, statecode           text
, county              text
, issuance_type       text
, leg_type            text
, leg_subtype         text
, leg_subsubtype      text
, leg_date            text
, fullfilename        text
);

drop table sdc_detail;
create table sdc_detail (
  main_id             integer
, rank                text
, name                text
, citationFormat      text
, required            text
);

