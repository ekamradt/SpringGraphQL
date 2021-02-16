from lxml import etree

from . import errorReport


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

    if not docType:
        error = "No identification (document type) found."

    dict["docType"] = docType
    dict["error"] = error


def levelsWithCitationFormat(root, dict):
    for levelNode in root:
        levelWithCitationFormat(levelNode, dict)


def levelWithCitationFormat(root, dict):
    attrs = root.attrib
    docType = dict["docType"]
    jurisData = dict["jurisData"]
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

    dict["rank"] = rank
    dict["levelName"] = name
    dict["citationFormat"] = citationFormat

    validateCitationFormatBrackets(citationFormat, dict)


def validateCitationFormatBrackets(citationFormat, dict):
    startCount = 0
    endCount = 0
    error = None
    dict["error"] = None

    for a in citationFormat:
        if a == "{":
            startCount += 1
            if startCount > 2:
                error = "More than 2 start brackets : '" + citationFormat + "'"
        elif a == "}":
            endCount += 1
            if startCount > 2:
                error = "More than 2 end brackets : '" + citationFormat + "'"
        else:
            if startCount == 1:
                error = "Only one beg bracket '" + citationFormat + "'"
            if endCount == 1:
                error = "Only one end bracket '" + citationFormat + "'"
            startCount = 0
            endCount = 0

    if startCount == 1:
        error = "Only one beg bracket '" + citationFormat + "'"
    if endCount == 1:
        error = "Only one end bracket '" + citationFormat + "'"

    if error:
        dict["error"] = error
        errorReport.showError(dict)

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
