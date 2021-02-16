def dictFetch(dict, key):
    value = dict[key] if key in dict else ""
    return value if value else ""


def showErrorHeader():
    print("source|file|rank|error|docType|levelName|fullFilename")


def showError(dict):
    fullFilename = dictFetch(dict, "fullFilename")
    dir_root = dictFetch(dict, "dir_root")
    dirs = dictFetch(dict, "dirs")
    inFile = dictFetch(dict, "inFile")
    source = dictFetch(dict, "source")
    error = dictFetch(dict, "error")
    docType = dictFetch(dict, "docType")
    rank = dictFetch(dict, "rank")
    levelName = dictFetch(dict, "levelName")
    source = dictFetch(dict, "source")
    print(source + "|" + inFile + "|" + rank + "|" + error + "|" + docType + "|" + levelName + "|" + fullFilename)
