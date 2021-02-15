from lxml import etree

from . import db


def processSingleFile(dict):
    # print("=================")
    # print("=== Root  : " + str(dir_root))
    # print("=== Dits  : " + str(dirs))
    # print("=== inFile : " + str(inFile))
    # print("=== fullFilename : " + str(fullFilename))
    # print("")

    fullFilename = dict["fullFilename"]
    dir_root = dict["dir_root"]
    dirs = dict["dirs"]
    inFile = dict["inFile"]
    source = dict["source"]

    root_element = etree.parse(fullFilename)
    root = root_element.getroot()

    # Isolate just sourceOfLaw and the citationFormats

    docType = ""
    for child in root:
        tag = child.tag
        if "sourceOfLaw" == tag:
            sourceOfLawWithCitationFormat(child, dict)
            if dict["error"]:
                showError(dict)
                break
        elif "levels" == tag:
            levelsWithCitationFormat(child, dict)


def showErrorHeader():
    print("source|file|error|docType|rank|levelName|fullFilename")


def dictFetch(dict, key):
    return dict[key] if key in dict else ""


def showError(dict):
    fullFilename = dict["fullFilename"]
    dir_root = dictFetch(dict, "dir_root")
    dirs = dictFetch(dict, "dirs")
    inFile = dictFetch(dict, "inFile")
    source = dictFetch(dict, "source")
    error = dictFetch(dict, "error")
    docType = dictFetch(dict, "docType")
    rank = dictFetch(dict, "rank")
    levelName = dictFetch(dict, "levelName")
    source = dictFetch(dict, "source")
    print(source + "|" + inFile + "|" + error + "|" + docType + "|" + rank + "|" + levelName + "|" + fullFilename)


# displayName, description, identification,, issuingAuthority, rootUri
def sourceOfLawWithCitationFormat(root, dict):
    inFile = dict["inFile"]
    source = dict["source"]
    dict["jurisData"] = None
    dict["docType"] = None
    dict["error"] = None
    error = None
    docType = None
    data = None
    for child in root:
        tag = child.tag
        if "identification" == tag:
            docType = child.text
            print("")
            print(docType.ljust(40) + inFile)

    if not docType:
        error = "No identification (document type) found."
    else:
        searchJurisdictionFromDocType(dict, docType)
        if dict["jurisData"]:
            searchLawTypeFromDocType(dict, docType)
            if not dict["lawTypeData"]:
                error = "No law type data found for '" + docType + "'"
        else:
            error = "No jurisdiction data found for '" + docType + "'"

    dict["docType"] = docType
    dict["error"] = error


def searchJurisdictionFromDocType(dict, docType):
    # Search for jurisdiction match
    data = None
    # try the first two parts
    data = db.searchForJurisdiction(docType)
    if not data:
        parts = docType.split("_")
        if len(parts) > 1:
            data = db.searchForJurisdiction(parts[0] + " " + parts[1])
        else:
            data = db.searchForJurisdiction(parts[0])
        if not data:
            for part in parts:
                data = db.searchForJurisdiction(part)
                if data:
                    break

    dict["jurisData"] = data


def searchLawTypeFromDocType(dict, docType):
    # Search for jurisdiction match
    data = None
    jurisData = dict["jurisData"]
    if not jurisData:
        return

    name = jurisData["name"]
    value = adjustValueForSearch(docType, name)
    data = db.searchForLawType(value)

    if not data:
        matchingValues = jurisData["matching_values"]
        parts = matchingValues.split("/")
        for part in parts:
            if len(part) > 0:
                value = part.replace(" ", "_")
                value = adjustValueForSearch(docType, value)
                data = db.searchForLawType(value)
                if data:
                    break

    dict["lawTypeData"] = data


def adjustValueForSearch(docType, name):
    upperName = name.upper().replace(" ", "_")
    value = docType.replace(upperName, "")
    if value.startswith("_"):
        value = value[1:]
    if value.endswith("_"):
        value = value[:-1]
    value = value.replace("__", "_")
    return value


def levelsWithCitationFormat(root, dict):
    for levelNode in root:
        levelWithCitationFormat(levelNode, dict)


def levelWithCitationFormat(root, dict):
    attrs = root.attrib
    docType = dict["docType"]
    jurisData = dict["jurisData"]
    abbr = jurisData['abbreviation']
    name = None
    rank = None
    citationFormat = ""
    for child in root:
        tag = child.tag
        if "name" == tag:
            name = child.text
        elif "rank" == tag:
            rank = child.text
        elif "citationFormat" == tag:
            citationFormat = child.text
            if abbr not in citationFormat:
                error = "citation format " + citationFormat + " is missing " + abbr
                dict["error"] = error
                showError(dict)

    dict["rank"] = rank
    dict["levelName"] = name
    dict["citationFormat"] = citationFormat
#    if name != docType:
#        print("  " + name.ljust(20) + rank.ljust(3) + citationFormat)

# #######################################
# Error:
#   file
#   docType
#   rank
#   levelVame
#   citationFormat
#
