def dictFetch(dict, key):
    value = dict[key] if key in dict else ""
    return value if value else ""


def getErrorHeader(delimiter="|"):
    return "source|file|rank|error|docType|levelName|fullFilename"


def showErrorHeader(delimiter="|"):
    print(getErrorHeader(delimiter))


def getErrorString(dict, delimiter="|"):
    fullFilename = dictFetch(dict, "fullFilename")
    inFile = dictFetch(dict, "inFile")
    error = dictFetch(dict, "error")
    docType = dictFetch(dict, "docType")
    rank = dictFetch(dict, "rank")
    levelName = dictFetch(dict, "levelName")
    source = dictFetch(dict, "source")
    d = delimiter
    return source + d + inFile + d + rank + d + error + d + docType + d + levelName + d + fullFilename


def getNamedErrorString(dict, delimiter="|"):
    fullFilename = dictFetch(dict, "fullFilename")
    inFile = dictFetch(dict, "inFile")
    error = dictFetch(dict, "error")
    docType = dictFetch(dict, "docType")
    rank = dictFetch(dict, "rank")
    levelName = dictFetch(dict, "levelName")
    source = dictFetch(dict, "source")
    d = delimiter
    return "SOURCE    : '%s'%sFILE      : '%s'%sRANK      : '%s'%sDOCTYPE   : '%s'%sLEVELNAME : '%s'%sFULLFILE  : '%s'%sERROR     : '%s'" % \
           (source, d, inFile, d, rank, d, docType, d, levelName, d, fullFilename, d, error,)


def showError(dict, delimiter="|"):
    print(getErrorString(dict, delimiter))
