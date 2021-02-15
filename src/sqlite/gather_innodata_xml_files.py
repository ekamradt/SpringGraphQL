import os

from innoutil import innostates

xml_titles = [
    "displayName",
    "description",
    "identification",
    "issuingAuthority",
    "rootUri",
]
detail_titles = [
    "rank",
    "name",
    "citationFormat"
]
text_titles = {
    "Citation form": "citation_form",
    "Citation example": "citation_example",
}


def parse_path(pathname, statecode):
    # US-GA/GEORGIA_RULES_REGULATIONS/DEPARTMENT-80/2017-09-18
    temp = pathname.replace("US-", "")
    temp = temp.replace(statecode + "-", "")
    if temp.startswith(statecode):
        temp = temp[3:]
    if temp.startswith("/"):
        temp = temp[1:]
    parts = temp.split("/")

    leg_type = ""
    leg_subtype = ""
    leg_date = ""
    leg_subsubtype = ""
    partsize = len(parts)
    if partsize == 1:
        leg_type = parts[0]
    elif partsize == 2:
        leg_type = parts[0]
        leg_subtype = parts[1]
    elif partsize == 3:
        leg_type = parts[0]
        leg_subtype = parts[1]
        leg_subtype = parts[1]
        leg_date = parts[2]

    if len(leg_subtype) > 0 and "_" in leg_subtype:
        tempparts = leg_subtype.split("_")
        leg_subtype = tempparts[0]
        leg_subsubtype = tempparts[1]

    d = {
        "leg_type": leg_type,
        "leg_subtype": leg_subtype,
        "leg_subsubtype": leg_subsubtype,
        "leg_date": leg_date
    }
    return d

def clean_tags(line, tag):
    temp = line
    temp = temp.replace("<" + tag + ">", "")
    temp = temp.replace("</" + tag + ">", "")
    temp = temp.strip()
    return temp


def clean_text(line, text):
    temp = line.strip()
    temp = temp.replace(text + ":", "")
    temp = clean_tags(temp.strip(), "processingComment")
    return temp.strip()


def empty_detail(file_id):
    return {
        "file_id": file_id,
        "rank": "",
        "name": "",
        "citationFormat": "",
        "required": False
    }


def calc_state_info(line):
    statecode = line[3:]
    county = ""
    if statecode == "Federal":
        statecode = 'FD'
    if "-" in statecode:
        tmp = statecode.split("-")
        statecode = tmp[0]
        county = tmp[1]

    if statecode not in statedict:
        raise Exception('Sorry, State Code "%s" does not exists within the statedict.' % (statecode))

    state = statedict[statecode]
    print(statecode + " : " + state)
    statelower = state.replace(" ", "-").lower()

    return state, statecode, statelower, county


def write_header(f, dict):
    header = ""
    for key in dict:
        header += "," + "\"" + key + "\""
    f.write(header[1:])
    f.write("\n")


def write_data(f, dict):
    data = ""
    for key in dict:
        data += "," + "\"" + str(dict[key]) + "\""
    f.write(data[1:])
    f.write("\n")


def write_data_array(fil, ary):
    for dict in ary:
        write_data(fil, dict)


def process_files(main_file, detail_file, file_id, statedict, path, root, dirs, files):
    temproot = root.replace(path, "")

    roots = temproot.split("/")
    firstpart = roots[0]
    if not firstpart.startswith("US-"):
        return file_id

    state, statecode, statelower, county = calc_state_info(firstpart)
    path_dict = parse_path(temproot, statecode)

    found = False
    for filename in files:
        if filename.endswith(".xml"):
            file_id = file_id + 1
            main_dict, dict_details = process_file(
                file_id, statedict, filename, path_dict, root, state, statecode, statelower, county)

            if file_id == 1:
                # Write main file header
                write_header(main_file, main_dict)
                write_header(detail_file, dict_details[0])

            if file_id >= 1:
                write_data(main_file, main_dict)
                write_data_array(detail_file, dict_details)

    return file_id


def process_file(file_id, statedict, filename, path_dict, root, state, statecode, statelower, county):
    issuance_type = innostates.do_issuance_type(filename, statecode, statelower)
    try:
        fullfilename = root + "/" + filename
        file = open(fullfilename)
        contents = file.readlines()

        temp_main_dict = {'id': file_id}
        dict_details = []
        detail = empty_detail(file_id)
        start_details = False

        for line in contents:
            line = line.strip()
            if not start_details:
                if "<levels>" in line:
                    start_details = True
                    continue
                for title_phrase in xml_titles:
                    if title_phrase in line:
                        temp_main_dict[title_phrase] = clean_tags(line, title_phrase)
                        continue
                for text in text_titles:
                    if line.startswith(text):
                        temp_main_dict[text_titles[text]] = clean_text(line, text)
                        continue
            else:  # do details
                if "<level " in line:
                    detail["required"] = ("true" in line)

                if "</level>" in line:
                    # add detail
                    dict_details.append(detail)
                    detail = empty_detail(file_id)
                    continue
                for det in detail_titles:
                    if "<" + det in line:
                        value = clean_tags(line, det)
                        detail[det] = value
                        if "citationFormat" in det:
                            print(value)

    finally:
        file.close()

    main_dict = {
        "id": file_id,
        "state": state,
        "statecode": statecode,
        "county":county,
        "issuance_type": issuance_type,
        "leg_type": path_dict["leg_type"],
        "leg_subtype": path_dict["leg_subtype"],
        "leg_subsubtype": path_dict["leg_subsubtype"],
        "leg_date": path_dict["leg_date"],
        "citation_form": temp_main_dict["citation_form"] if "citation_form" in temp_main_dict else "" ,
        "citation_example": temp_main_dict["citation_example"] if "citation_example" in temp_main_dict else "" ,
        "fullfilename": fullfilename
    }
    return main_dict, dict_details

if __name__ == "__main__":

    # git@github.com:R2ai/data-dcl.git
    path_us = ["/home/ekamradt/git/data-innodata/US/", "/home/ekamradt/git/data-dcl/US/"]
    # path_us = ["/home/ekamradt/git/data-dcl/US/"]

    print ("************ jhhhhhhhhhhhhhhh 0")
    file_id = 0
    statedict = innostates.states()
    main_filename = "sdc_main.csv"
    detail_filename = "sdc_detail.csv"
    print ("************ jhhhhhhhhhhhhhhh 1")
    main_file = open(main_filename, "w")
    print ("************ jhhhhhhhhhhhhhhh 2")
    detail_file = open(detail_filename, "w")

    for path in path_us:
        for (root, dirs, files) in os.walk(path, topdown=True):
            file_id = process_files(main_file, detail_file, file_id, statedict, path, root, dirs, files)

    main_file.close()
    detail_file.close()
