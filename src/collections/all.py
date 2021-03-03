import csv
import pprint
from collections.abc import Mapping

print("hi")


def my_strip(parts, index):
    value = parts[index]
    value = value.strip()
    value = value.replace('"', '')
    value = value.strip()
    return value


def process_all(final):
    with open("/home/ekamradt/tmp/all.csv") as infile:
        reader = csv.reader(infile)  # Create a new reader
        next(reader)  # Skip the first row
        for line in reader:
            process_line(final, line)


def process_line(final, parts):
    americas = final["Americas"]
    apac = final["APAC"]
    emea = final["EMEA"]

    latam = americas["LATAM"]
    canada = americas["Canada"]
    united_states = americas["United States"]

    states = united_states["State"]
    # federal = united_states["Federal"]

    unique_id = my_strip(parts, 0)
    geocategory = my_strip(parts, 1)
    region = my_strip(parts, 2)
    local = my_strip(parts, 3)
    issuing = my_strip(parts, 4)
    issue_type = my_strip(parts, 5)
    univ_sdc_type = my_strip(parts, 6)
    sdc_name = my_strip(parts, 7)
    mvp = my_strip(parts, 8)

    dict = {}
    dict["unique_id"] = unique_id
    dict["geocategory"] = geocategory
    dict["region"] = region
    dict["local"] = local
    dict["issuing"] = issuing
    dict["issue_type"] = issue_type
    dict["univ_sdc_type"] = univ_sdc_type
    dict["sdc_name"] = sdc_name
    dict["mvp"] = mvp

    master_dict = None

    if geocategory == "International":
        if region == "Canada":
            master_dict = canada
        elif region == "LATAM":
            master_dict = latam
        elif region == "EMEA":
            master_dict = emea
        elif region == "APAC":
            master_dict = apac

    elif geocategory == "US Federal":
        master_dict = united_states
        local = "Federal"

    elif geocategory == "US State":
        master_dict = states

    elif geocategory == "US Municipal":
        if "Chicago" in issue_type or "Illinois" in local:
            state_name = "Illinois"
            if state_name not in states:
                states[state_name] = {}
            muni = states[state_name]
            master_dict = muni
            local = "municipal"

    if master_dict is not None:
        the_instance = {
            "issuing": issuing,
            "univ_sdc_type": univ_sdc_type,
            "sdc_name": sdc_name,
            "unique_id": unique_id
        }
        if local not in master_dict:
            master_dict[local] = {}
        typ = master_dict[local]
        typ[issue_type] = the_instance


states = {}
federal = {}

united_states = {}
united_states["State"] = states
united_states["Federal"] = federal

americas = {}
americas["LATAM"] = {}
americas["Canada"] = {}
americas["United States"] = united_states

final = {}
final["Americas"] = americas
final["APAC"] = {}
final["EMEA"] = {}


# pprint.pprint(final, indent=2, width=255)

def start_decomposition(dict):
    msg_ary = []
    for key in dict:
        print(f"key : {key} ")
        msg = key
        val = dict[key]
        if isinstance(val, Mapping):
            continue_decomposition(val, msg, 1, msg_ary)

    return msg_ary
    # key = "EMEA"
    # print(f"key : {key} ")
    # msg = key
    # val = dict[key]
    # if isinstance(val , Mapping):
    #     continue_decomposition(val, msg, 1)


def continue_decomposition(dict, in_msg, level, msg_ary):
    level += 1
    msg = in_msg

    for key in dict:
        ##print(f"level : {level}  key : {key} ")
        val = dict[key]
        msg = msg + f"|{key}"
        if isinstance(val, Mapping):
            continue_decomposition(val, msg, level, msg_ary)
            msg = in_msg
        else:
            msg += f"={val}"

    if "unique_id" in msg:
        msg_ary.append(msg)
        print(msg)
    uu = 99


process_all(final)
pprint.pprint(final, indent=2, width=200)
msg_ary = start_decomposition(final)

new_ary = []
for msg in msg_ary:
    id = msg[-6:]
    new_ary.append(f"{id}|{msg}")

for msg in new_ary:
    print(msg)