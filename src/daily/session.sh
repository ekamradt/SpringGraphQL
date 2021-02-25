
clear

export TMP_PG_HOST=35.232.252.122
export TMP_PG_DBNAME=postgres
export TMP_PG_PORT=5432

export TMP_PG_DCL_USER=segment-dcl
export TMP_PG_DCL_PASS=brop3ber*fist0GENT

export TMP_PG_INNODATA_USER=segment-innodata
export TMP_PG_INNODATA_PASS=taw-flou-FLAC8aub




psql --host=${TMP_PG_HOST} --username=${TMP_PG_INNODATA_USER} --port=${TMP_PG_PORT} --dbname=${TMP_PG_DBNAME} -c

# postgresql://segment-dcl:brop3ber*fist0GENT@35.232.252.122/postgres
# postgresql://segment-innodata:taw-flou-FLAC8aub@35.232.252.122/postgres


# psql is the PostgreSQL interactive terminal.
#
# Usage:
#   psql [OPTION]... [DBNAME [USERNAME]]
#
# General options:
#   -c, --command=COMMAND    run only single command (SQL or internal) and exit
#   -d, --dbname=DBNAME      database name to connect to (default: "ekamradt")
#   -f, --file=FILENAME      execute commands from file, then exit
#   -l, --list               list available databases, then exit
#   -v, --set=, --variable=NAME=VALUE
#                            set psql variable NAME to VALUE
#                            (e.g., -v ON_ERROR_STOP=1)
#   -V, --version            output version information, then exit
#   -X, --no-psqlrc          do not read startup file (~/.psqlrc)
#   -1 ("one"), --single-transaction
#                            execute as a single transaction (if non-interactive)
#   -?, --help[=options]     show this help, then exit
#       --help=commands      list backslash commands, then exit
#       --help=variables     list special variables, then exit
#
# Input and output options:
#   -a, --echo-all           echo all input from script
#   -b, --echo-errors        echo failed commands
#   -e, --echo-queries       echo commands sent to server
#   -E, --echo-hidden        display queries that internal commands generate
#   -L, --log-file=FILENAME  send session log to file
#   -n, --no-readline        disable enhanced command line editing (readline)
#   -o, --output=FILENAME    send query results to file (or |pipe)
#   -q, --quiet              run quietly (no messages, only query output)
#   -s, --single-step        single-step mode (confirm each query)
#   -S, --single-line        single-line mode (end of line terminates SQL command)
#
# Output format options:
#   -A, --no-align           unaligned table output mode
#       --csv                CSV (Comma-Separated Values) table output mode
#   -F, --field-separator=STRING
#                            field separator for unaligned output (default: "|")
#   -H, --html               HTML table output mode
#   -P, --pset=VAR[=ARG]     set printing option VAR to ARG (see \pset command)
#   -R, --record-separator=STRING
#                            record separator for unaligned output (default: newline)
#   -t, --tuples-only        print rows only
#   -T, --table-attr=TEXT    set HTML table tag attributes (e.g., width, border)
#   -x, --expanded           turn on expanded table output
#   -z, --field-separator-zero
#                            set field separator for unaligned output to zero byte
#   -0, --record-separator-zero
#                            set record separator for unaligned output to zero byte
#
# Connection options:
#   -h, --host=HOSTNAME      database server host or socket directory (default: "local socket")
#   -p, --port=PORT          database server port (default: "5432")
#   -U, --username=USERNAME  database user name (default: "ekamradt")
#   -w, --no-password        never prompt for password
#   -W, --password           force password prompt (should happen automatically)
#
# For more information, type "\?" (for internal commands) or "\help" (for SQL
# commands) from within psql, or consult the psql section in the PostgreSQL
# documentation.
#
# Report bugs to <pgsql-bugs@lists.postgresql.org>.
# PostgreSQL home page: <https://www.postgresql.org/>
