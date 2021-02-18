from lxml import etree

from . import db
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
                errorReport.showError(dict)
                break
        elif "levels" == tag:
            levelsWithCitationFormat(child, dict)


def dictFetch(dict, key):
    return dict[key] if key in dict else ""


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
    if docType == "NYSE_RULES":
        t = 44
    jurisData = dict["jurisData"]
    if not jurisData:
        return

    jurisName = jurisData["name"]
    docName = docType.replace("1", "")
    docName = docName.replace("2", "")
    data = db.searchForLawType(docName)
    if not data:
        value = adjustValueForSearch(docType, jurisName)
        data = db.searchForLawType(value)

    if not data:
        matchingValues = jurisData["matching_values"]
        parts = matchingValues.split("/")
        for part in parts:
            if len(part) > 0:
                value = part.replace(" ", "_")
                value = adjustValueForSearch(docName, value)
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
    rank = None
    name = None
    citationFormat = ""
    for child in root:
        tag = child.tag
        if "name" == tag:
            name = child.text
            dict["levelName"] = name
        elif "rank" == tag:
            rank = child.text
            dict["rank"] = rank
        elif "citationFormat" == tag:
            citationFormat = child.text
            dict["citationFormat"] = citationFormat
            if abbr not in citationFormat:
                error = "citation format " + citationFormat + " is missing '" + abbr + "'"
                dict["error"] = error
                errorReport.showError(dict)
            extraWordInCitationFormat(citationFormat, dict)
        elif "levelIdentifier" == tag:
            levelIdentifier(child, dict)


def levelIdentifier(root, dict):
    content = getOriginalLine(root)
    lines = content.split("\n")
    exampleCount = - 0
    for line in lines:
        if "<example" in line:
            exampleCount += 1
            if exampleCount > 1:
                break
        else:
            exampleCount = 0

    if exampleCount > 1:
        dict["error"] = "More than one example -- do we need to adjust this levelIdentifier group?"
        errMsg = errorReport.getNamedErrorString(dict, "~")
        msgPrefix = "ExEx:"
        print(msgPrefix)
        print(msgPrefix)
        print(msgPrefix + errMsg)
        for line in lines:
            print(msgPrefix + line)


def getOriginalLine(child):
    originalLine = str(etree.tostring(child, pretty_print=True), 'UTF-8')
    originalLine = originalLine.replace("\\n", "")
    originalLine = originalLine.replace("\\", "")
    originalLine = originalLine.strip()
    return originalLine


#    if name != docType:
#        print("  " + name.ljust(20) + rank.ljust(3) + citationFormat)

def extraWordInCitationFormat(citationFormat, dict):
    justWords = stripAllButWord(citationFormat, dict)
    if len(justWords) > 0:
        jurisData = dict["jurisData"]
        lawTypeData = dict["lawTypeData"]
        abbr = jurisData["abbreviation"]
        bluebook = lawTypeData["bluebook_value"]
        dict["error"] = "Bad word '%s' for citation '%s'  Juris:'%s'  LawType:'%s'" % (
            justWords, citationFormat, abbr, bluebook)
        errorReport.showError(dict)
    else:
        dict["error"] = None


def stripAllButWord(citationFormat, dict):
    startCount = 0
    endCount = 0
    error = None
    returnString = ""

    for a in citationFormat:
        if a == "{":
            if startCount == 2 and endCount == 2:
                startCount = 0
                endCount = 0
            startCount += 1
            if startCount > 2:
                error = "More than 2 start brackets : '" + citationFormat + "'"
        elif a == "}":
            endCount += 1
            if endCount > 2:
                error = "More than 2 end brackets : '" + citationFormat + "'"
        else:
            if startCount > 0 and endCount == 0:
                continue

            if startCount == 2 and endCount == 2:
                startCount = 0
                endCount = 0
            returnString += a

    returnString = returnString.replace("  ", " ")

    jurisData = dict["jurisData"]
    lawTypeData = dict["lawTypeData"]

    bluebook = lawTypeData["bluebook_value"]
    returnString = returnString.replace(bluebook, "")

    abbr = jurisData["abbreviation"]
    returnString = returnString.replace(abbr, "")

    removeAry = [" subdiv. ", " subart. ", " subpt. ", " subtit. ", " subch. ", " agency ", " dept. ", " tit. ", ",",
                 " ch. ", "§", "()", " pt. ", " art. ", ' div. ', "-", " vol. ", " sec. ", " sub. ", " No. ", "(", ")"]
    for remove in removeAry:
        returnString = returnString.replace(remove, " ")

    returnString = returnString.strip()
    while ".." in returnString:
        returnString = returnString.replace("..", ".")
    while "::" in returnString:
        returnString = returnString.replace("::", ":")

    returnString = returnString.replace('"', '')
    returnString = returnString.strip()
    removeAry = [".", ":", "r.", ". .", "/"]
    for word in removeAry:
        if returnString == word:
            returnString = ""

    returnString = returnString.strip()
    return returnString

# #######################################
# Error:
#   file
#   docType
#   rank
#   levelVame
#   citationFormat
#
