import os

directories = ['/home/ekamradt/git/data-innodata-sdc/', '/home/ekamradt/git/data-dcl-sdc/']
#directories = ['/home/ekamradt/git/data-dcl-sdc/US']

def doStart():
    wrt_file = open('KEYS.info', 'w')
    file1 = open('UniqueIdSdc2.csv', 'r')
    lines = file1.readlines()
    id_map = {}
    url_map = {}
    src_map = {}
    for line in lines:
        parts = line.split(",")
        #print(line)
        id = parts[0].strip()
        src = parts[1].strip()
        key = parts[2].strip()
        url = parts[3].strip()
        key = key.upper()
        key = key.replace(" - ", "_")
        key = key.replace("  ", " ")
        key = key.replace(" ", "_")

        if key == "VERMONT_CODE_OF_VERMONT":
            key = "CODE_OF_VERMONT_RULES"
        if key == "MUNICIPAL_CODE_OF_CHICAGO":
            key = "CHICAGO_MUNICIPAL_CODE"
        if key == "OFFICE_OF_THE_REVENUE_COMMISSIONERS_GUIDANCE":
            key = "REVENUE_COMMISSIONERS_GUIDANCE"
        if key == "TREATY_ON_THE_FUNCTIONING_OF_THE_EUROPEAN_UNION_(TFEU)":
            key = "TFEU"

        id_map[key] = id
        if "?" in url:
            parts = url.split("?")
            url = parts[0]
        url_map[url] = id
        if src:
            src_map[src] = id
        wrt_file.write(key + "    : " + str(id))
        wrt_file.write("\n")

    wrt_file.close()
    file1.close()

    for directory in directories:
        source = 'INNODATA' if 'innodata' in directory else 'DCL'
        for dir_root, dirs, files in os.walk(directory):

            dict = {}
            dict["dir_root"] = dir_root
            dict["dirs"] = dirs
            dict["source"] = source

            count = len(files)
            if count >= 1:
                for in_file in files:
                    lowerName = str(in_file).lower()
                    fullFilename = str(dir_root) + "/" + str(in_file)
                    if not lowerName.endswith("xml") or not lowerName.startswith("sdc"):
                        ee = 9
                        # print("Bad File : " + fullFilename)
                    else:
                        i = 9
                        #print(fullFilename)
                        sdc_file = open(fullFilename, 'r')
                        lines = sdc_file.readlines()
                        # <sourceOfLaw>
                        #   <displayName>District of Columbia Code</displayName>
                        #   <description>The code authored by the State of District of Columbia</description>
                        #   <identification>DC_CODE</identification>
                        #   <issuingAuthority>District of Columbia Legislature</issuingAuthority>
                        #   <rootUri>https://code.dccouncil.us/dc/council/code/</rootUri>
                        # </sourceOfLaw>
                        unique_id = None
                        identification, rootUri = getKeys(lines)
                        if identification not in id_map and rootUri not in url_map and identification not in src_map:
                            if "_ADMINISTRATIVE_" in identification:
                                identification = identification.replace("_ADMINISTRATIVE_", "_ADMIN_")
                            if identification not in id_map and rootUri not in url_map:
                                print("No Match : " + identification + "  : " + fullFilename)
                                continue
                                # raise Exception("In file '%s' the Ident: '%s' and url '%s' has no match" %
                                #                (in_file, identification, rootUri,))
                        else:
                            if identification in src_map:
                                unique_id = src_map[identification]
                            elif identification in id_map:
                                unique_id = id_map[identification]
                            else:
                                unique_id = url_map[rootUri]

                        sdc_file = open(fullFilename, "r")
                        content = sdc_file.read()
                        sdc_file.close()

                        if "\<uniqueId\>" not in content:
                            tmp = "\\\n    <uniqueId>%s<\/uniqueId>" % (unique_id,)
                            command = "sed --in-place 's/\/rootUri>/\/rootUri>%s/g' \"%s\" " % (tmp, fullFilename,)
                            print(command)
                            os.system(command)

                        #sdc_file = open(fullFilename, "r")
                        #content = sdc_file.read()
                        #sdc_file.close()
                        ##content = content.replace("/rootUri>", "/rootUri>" + tmp)
                        #sdc_file = open(fullFilename, "w")
                        #sdc_file.write(content)
                        #sdc_file.close()

                        #print("%s : %s : %s" % (unique_id, identification.ljust(40), fullFilename,))


def getKeys(lines):
    identification = None
    rootUri = None
    for line in lines:
        if "<identification>" in line:
            identification = clean_tags(line, "identification")
        elif "<rootUri>" in line:
            rootUri = clean_tags(line, "rootUri")

    return identification, rootUri


def clean_tags(line, tag):
    temp = line
    temp = temp.replace("<" + tag + ">", "")
    temp = temp.replace("</" + tag + ">", "")
    temp = temp.strip()
    return temp


doStart()
