--
DROP TABLE IF EXISTS name CASCADE;

DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START WITH 1000;

CREATE TABLE name (
    name_id     bigserial
,   first_name  varchar(64)
,   last_name   varchar(64)
--
,   CONSTRAINT name_pk PRIMARY KEY(name_id)
);

INSERT INTO name ( name_id, first_name, last_name ) VALUES
 (1, 'Bobbie', 'Tables')
,(2, 'Betty', 'Tables')
;

