import sqlite3

conn = sqlite3.connect("SDC.db3")
conn.row_factory = sqlite3.Row

cur = conn.cursor()
cur.execute("SELECT * FROM baby")
baby_recs = cur.fetchall()
cur.close()
#
# setup baby dicts
#
babystates = {}
babylewtypes = {}
for rec in baby_recs:
    # Store by State for State Abbrev.
    if rec['level_1'] == 'Geographical Terms':
        babystates[rec['name']] = rec

    # Store by state an array of law types and their templates
    if rec['level_1'] == 'U.S. States and Other Jurisdictions':
        state = rec['level_2']
        nam = rec['name']
        orig = rec['blue_original']
        if (nam and len(nam.strip()) > 0) and (orig and len(orig.strip()) > 0):
            if state not in babylewtypes:
                babylewtypes[state] = []
            type_array = babylewtypes[state]
            type_array.append(rec)

cur = conn.cursor()
cur.execute("SELECT * FROM sdc")
sdc_recs = cur.fetchall()
cur.close()
sdcstates = {}
for rec in sdc_recs:
    if rec['geo_category'] == 'US State':
        state = rec['local_juris']
        if state not in sdcstates:
            sdcstates[state] = []

        sdcstates[state].append(rec)

convertdict = {}
convertdict['bills'] = 'H.B. {{BILL!}} § {{LEG_SESSION!}}[, {{SESSION_TYPE}}] [{{STATE YYYY}}]'
convertdict['revised statutes'] = 'STATE Rev. Stat. § {{TITLE!}}-{{CHAPTER!}}-{{SECTION!}} [{{(YYYY)}}]'
convertdict['statutes'] = 'STATE Stat. § {{TITLE!}}-{{CHAPTER!}}-{{SECTION!}} [{{(YYYY)}}]'
convertdict['admin code'] = 'STATE Admin. Code <agency abbreviation> § x-x (<year>)'
convertdict['administrative code'] = 'STATE Admin. Code <agency abbreviation> § x-x (<year>)'
convertdict['administrative register'] = '<iss. no.> STATE Admin. Reg. <page no.> (<month day, year>)'
convertdict['register'] = '<iss. no.> STATE Reg. <page no.> (<month day, year>)'

sortdictkeys = sorted(convertdict, key=lambda k: len(k), reverse=True)


def bills():
    outs = []
    for state in sdcstates:
        sdcstate = sdcstates[state]
        baby = babystates[state]
        if sdcstate and baby:
            for sdc in sdcstate:
                out = {}
                out['unique_id'] = sdc['unique_id']
                out['id'] = sdc['id']
                out['local_juris'] = state
                abbrev = baby["blue_original"]
                out["blue_original"] = abbrev
                out["blue_normalized"] = baby["blue_normalized"]
                out["issuance_type"] = sdc["issuance_type"]
                out['template'] = None
                for sortkey in sortdictkeys:
                    ### print ("Key : " + sortkey)
                    if not out['template']:
                        if sortkey in sdc['issuance_type'].lower():
                            template = convertdict[sortkey]
                            out['template'] = template.replace("STATE", abbrev)
                            break
                #
                # Figure template2
                #
                save_words = []
                if state in babylewtypes:
                    issuance_type = sdc['issuance_type']
                    words = issuance_type.split(" ")
                    countdict = {}
                    for word in words:
                        if state == word:
                            continue
                        # Loop through baby state array
                        word = word.lower()
                        for lawtype in babylewtypes[state]:
                            nam = lawtype['name'].lower()
                            if word in nam:
                                if lawtype not in countdict:
                                    countdict[lawtype] = 0
                                save_words.append(word)
                                countdict[lawtype] += 1
                    templawtype = None
                    max = 0
                    for lawtype in countdict:
                        num = countdict[lawtype]
                        if max < num:
                            max = num
                            templawtype = lawtype

                print("----------------------------------------------------")
                print(str(save_words))
                print("----------------------------------------------------")
                i = 99



                outs.append(out)
                ### print(str(out))

    print("================================================================")
    # for out in outs:
    #     if not out['template']:
    #         print (out['issuance_type'])


# ##############################################################################
# Main
#
bills()

# SELECT S.unique_id, S.local_juris, B.value_original , B.value_normalized,
# TRIM(REPLACE(REPLACE(S.issuance_type, S.local_juris, '')
# 			 , '-', '')) AS iss_typ, S.issuance_type,
# CASE WHEN S.geo_category = 'US State'
# THEN
# CASE
# WHEN S.issuance_type LIKE '%Bills%' THEN  'H.B. {{BILL!}} § {{LEG_SESSION!}}[, {{SESSION_TYPE}}] [{{' || B.value_original || ' YYYY}}]'
# WHEN S.issuance_type LIKE '%Register%' THEN
# CASE
# WHEN B.name = 'Alabama' THEN '<vol. no.> Ala. Admin. Monthly <page no.> (<month day, year>)'
# ELSE NULL
# END
# ELSE NULL
# END
# ELSE NULL
# END AS template
# FROM sdc S
# INNER JOIN baby B
# ON  1=1 -- B.level_3 = 'States'
# AND B.name = S.local_juris
# ;
#
