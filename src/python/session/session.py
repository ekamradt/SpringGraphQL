from datetime import date, timedelta

import psycopg2
import psycopg2.extras
import sys


def spacerStart():
    print("-- ---------------------")


def spacerEnd():
    print("--")


def run_reports(dict):
    print("---------------")
    database_queries = dict["database_queries"]
    days_ago = dict["days_ago"]

    for db_query in database_queries:
        db_user = db_query["db_user"]
        db_pass = db_query["db_pass"]

        conn = create_connection(dict, db_user, db_pass)
        infos = db_query["infos"]
        for info in infos:
            dbname = info["dbname"]
            date_field = info["date_field"]
            tables = info["tables"]
            for tablename in tables:
                spacerStart()
                sql = f"""
                SELECT  '{tablename}' as tablename, 
                        count(*) as the_count
                FROM    {dbname}.{tablename}
                WHERE   {date_field} >= '{days_ago}'
                """
                cur = conn.cursor(cursor_factory=psycopg2.extras.DictCursor)
                cur.execute(sql)
                row = cur.fetchone()
                the_count = row['the_count']
                the_count = '{:,}'.format(the_count)
                rec_out(dbname, str(row["tablename"]), the_count, "Record count since " + days_ago)

                sql = f"""
                SELECT  '{tablename}' as tablename,
                        count(*) as the_count,
                        date_trunc('day', {date_field}) AS rec_day
                FROM    {dbname}.{tablename}
                WHERE   {date_field} >= '{days_ago}'
                GROUP BY 1, 3
                ORDER BY 3 desc
                """
                cur = conn.cursor(cursor_factory=psycopg2.extras.DictCursor)
                cur.execute(sql)
                rows = cur.fetchall()
                for row in rows:
                    the_count = row['the_count']
                    the_count = '{:,}'.format(the_count)
                    rec_day = str(row["rec_day"])
                    rec_day = rec_day.replace("00:00:00+00:00", "")
                    rec_day = rec_day.strip()
                    rec_out(dbname, str(row["tablename"]), the_count, rec_day)

                    if tablename == "error" and int(the_count) > 0:
                        sql = f"""
                        SELECT  source_url,
                                document_address,
                                date_trunc('day', {date_field}) AS rec_day,
                                session_id
                        FROM    {dbname}.{tablename}
                        WHERE   date_trunc('day', {date_field}) = '{rec_day}'
                        """
                        cur = conn.cursor(cursor_factory=psycopg2.extras.DictCursor)
                        cur.execute(sql)
                        rows = cur.fetchall()
                        for row in rows:
                            source_url = row["source_url"]
                            document_address = row["document_address"]
                            session_id = row["session_id"]
                            rec_day = row["rec_day"]
                            print("  Error: %s : %s : %s : %s" % (rec_day, source_url, document_address, session_id,))


def rec_out(dbname, tablename, the_count, msg):
    print(dbname + " : " + str(tablename) + " : " + str(the_count).ljust(6) + " " + str(msg))


def create_connection(dict, db_user, db_pass):
    db_name = dict["db_name"]
    db_port = dict["db_port"]
    db_host = dict["db_host"]
    connection = None
    try:
        connection = psycopg2.connect(
            database=db_name,
            user=db_user,
            password=db_pass,
            host=db_host,
            port=db_port,
        )
        print("Connection to PostgreSQL DB successful")
    except:
        print("Unexpected error:", sys.exc_info()[0])
        raise
    return connection


# export TMP_PG_HOST=35.232.252.122
# export TMP_PG_DBNAME=postgres
# export TMP_PG_PORT=5432
# export TMP_PG_DCL_USER=segment-dcl
# export TMP_PG_DCL_PASS=brop3ber*fist0GENT
# export TMP_PG_INNODATA_USER=segment-innodata
# export TMP_PG_INNODATA_PASS=taw-flou-FLAC8aub

# ##########################################################
# Two weeks ago
# ##########################################################
current_date = date.today().isoformat()
days_ago = (date.today() - timedelta(days=14)).isoformat()
date_field = "received_at"

dict = {
    "db_name": "postgres",
    "db_port": "5432",
    "db_host": "35.232.252.122",
    "date_field": "received_at",
    "days_ago": days_ago,
    "database_queries": [
        {
            "db_user": "segment-dcl",
            "db_pass": "brop3ber*fist0GENT",
            "infos": [
                {
                    "dbname": "data_events_dcl_staging",
                    "date_field": date_field,
                    "tables": [
                        "source_checked",
                        "tracks",
                    ]
                },
                {
                    "dbname": "data_events_dcl_prod",
                    "date_field": date_field,
                    "tables": [
                        "source_changed",
                        "source_checked",
                        "source_delivered",
                        "tracks",
                    ]
                },
            ]
        },
        {
            "db_user": "segment-innodata",
            "db_pass": "taw-flou-FLAC8aub",
            "infos": [
                {
                    "dbname": "data_events_innodata_staging",
                    "date_field": date_field,
                    "tables": [
                        "check_source",
                        "error",
                        "source_canceled",
                        "source_changed",
                        "source_checked",
                        "source_delivered",
                        "source_moved",
                        "tracks",
                    ]
                },
                {
                    "dbname": "data_events_innodata_prod",
                    "date_field": date_field,
                    "tables": [
                        "source_checked",
                        "tracks",
                    ]
                },
            ]
        },
    ]
}

# dbname = "data_events_innodata_staging"
# tablename = "source_checked"
# date_field = "received_at"
# days_ago = "2020-12-31"

run_reports(dict)
