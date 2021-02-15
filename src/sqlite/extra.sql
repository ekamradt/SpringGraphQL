

SELECT S.unique_id, S.local_juris, B.value_original , B.value_normalized,
TRIM(REPLACE(REPLACE(S.issuance_type, S.local_juris, '')
, '-', '')) AS iss_typ, S.issuance_type,
CASE WHEN S.geo_category = 'US State'
THEN
	CASE
	WHEN S.issuance_type LIKE '%Bills%' THEN  'H.B. {{BILL!}} § {{LEG_SESSION!}}[, {{SESSION_TYPE}}] [{{' || B.value_original || ' YYYY}}]'
	WHEN S.issuance_type LIKE '%Register%' THEN
	    CASE
	        WHEN B.name = 'Alabama' THEN '<vol. no.> Ala. Admin. Monthly <page no.> (<month day, year>)'
	        ELSE NULL
	    END
	ELSE NULL
	END
ELSE NULL
END AS template
FROM sdc S
INNER JOIN baby B
ON  1=1 -- B.level_3 = 'States'
AND B.name = S.local_juris
;