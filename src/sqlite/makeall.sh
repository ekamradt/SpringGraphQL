
python3 gather_innodata_xml_files.py

sqlite3 SDC.db3  <<EOSQL
.read makeall.sql
.read postmake.sql
.exit
EOSQL
