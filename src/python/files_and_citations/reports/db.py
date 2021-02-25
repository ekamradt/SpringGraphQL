import sqlite3 as lite
import sys


def connect():
    return lite.connect('/home/ekamradt/git/SpringGraphQL/src/python/sdc_validate/reports/SDC.db3')

def searchForJurisdiction(jurisdiction):
    value = "%/" + jurisdiction.upper() + "/%"
    return _fetchOneRow(value, 'SELECT * FROM citation_jurisdiction WHERE matching_values LIKE ?')

def searchForLawType(lawType):
    value = "%/" + lawType.upper() + "/%"
    return _fetchOneRow(value, 'SELECT * FROM citation_law_type WHERE doc_type LIKE ?')

def _fetchOneRow(value, sql):
    conn = None
    try:
        conn = connect()
        conn.row_factory = lite.Row
        cur = conn.cursor()
        cur.execute(sql, (value,))
        data = cur.fetchone()
        return data
    except lite.Error as e:
        print( "Error {}:".format(e.args[0]))
        sys.exit(1)

    finally:
        if conn:
            conn.close()
