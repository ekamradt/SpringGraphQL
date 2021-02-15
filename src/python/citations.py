import os
from reports import levelReport
from lxml import etree

directories = ['/home/ekamradt/git/data-innodata-sdc/US', '/home/ekamradt/git/data-dcl-sdc/US']

def start(report_func):
    for directory in directories:
        source = 'INNODATA' if 'innodata' in directory else 'DCL'
        for dir_root, dirs, files in os.walk(directory):

            dict = {}
            dict["dir_root"] = dir_root
            dict["dirs"] = dirs
            dict["source"] = source

            count = len(files)
            if count >= 1:
                for inFile in files:
                    lowerName = str(inFile).lower()
                    fullFilename = str(dir_root) + "/" + str(inFile)
                    dict["inFile"] = inFile
                    dict["fullFilename"] = fullFilename
                    if not lowerName.endswith("xml") or (
                            not lowerName.startswith("brd") and not lowerName.startswith("sdc")):
                        if not lowerName.endswith("pdf"):
                            ee = 9
                            # print("Bad File : " + fullFilename)
                    else:
                        # t = 99
                        report_func(dict)
                #         break
                # break

# def processSingleFile(fullFilename, dir_root, dirs, inFile, source):
#     # print("=================")
#     # print("=== Root  : " + str(dir_root))
#     # print("=== Dits  : " + str(dirs))
#     # print("=== inFile : " + str(inFile))
#     # print("=== fullFilename : " + str(fullFilename))
#     # print("")
#     root_element = etree.parse(fullFilename)
#     root = root_element.getroot()
#
#     # Isolate just sourceOfLaw and the citationFormats
#
#     docType = ""
#     for child in root:
#         tag = child.tag
#         if "sourceOfLaw" == tag:
#             docType = levelReport.sourceOfLawWithCitationFormat(child, inFile, source)
#         elif "levels" == tag:
#             levelReport.levelsWithCitationFormat(child, docType)

    # for child in root:
    #     tag = child.tag
    #     print("---------------")
    #     print("child : " + str(tag))
    #     if "sourceOfLaw" == tag:
    #         sourceOfLaw(child)
    #     elif "updateFrequency" == tag:
    #         updateFrequency(child)
    #     elif "geography" == tag:
    #         geography(child)
    #     elif "dataSchema" == tag:
    #         dataSchema(child)
    #     elif "language" == tag:
    #         language(child)
    #     elif "processingComment" == tag:
    #         processingComment(child)
    #     elif "levels" == tag:
    #         levels(child)
    #     else:
    #         i = 99

    ### xml_file = open(fullFilename, 'rb')
    ### # content = xml_file.read()
    ### lines = xml_file.readlines()
    ### xml_file.close()
    ###
    ### process_lines(lines, dir_root)

    # try:
    #    xml_root = etree.fromstring(content)
    #    parseEtree(xml_root, dir_root)
    # except:
    #    e = sys.exc_info()[0]
    #    print("*** ERROR *** : %s" % e)
    #    print("Failed to read file : '" + fullFilename + "'")
    #    print("*** ERROR ***")



def sourceOfLaw(root):
    # displayName
    # description
    # identification
    # issuingAuthority
    # rootUri
    for child in root:
        tag = child.tag
        value = child.text
        dict = {tag: value}
        print("sourceOfLaw child : " + str(dict))
        # print("sourceOfLaw child : " + str(value))


def updateFrequency(root):
    tag = root.tag
    attrs = root.attrib
    print("updateFrequency : " + str(attrs))


# <geography>
#   <sovereignCountryGeocode>US</sovereignCountryGeocode>
#   <jurisdictionGeocode>US-AK</jurisdictionGeocode>
# </geography>
def geography(root):
    for child in root:
        tag = child.tag
        value = child.text
        dict = {tag: value}
        print("grography : " + str(dict))


def dataSchema(root):
    # value="https://github.com/R2ai/data-conversion-docs/blob/b46857e572790cd63ce0f30e96098b4af22e9d22/data/schemas/engin-an.xml"/>
    tag = root.tag
    value = root.text
    dict = {tag: value}
    print("dataSchema : " + str(dict))


def language(root):
    # >en</language>
    tag = root.tag
    value = root.text
    dict = {tag: value}
    print("language : " + str(dict))


def processingComment(root):
    # >FRBRdate "Publication date"- Use processing date (date when the document acquired from the site)
    tag = root.tag
    value = root.text
    dict = {tag: value}
    print("processingComment : " + str(dict))


def levels(root):
    # <levels>
    #     <level required="true" sourceRoot="true">
    #       <rank>1</rank>
    #       <name>ALASKA_ADMINISTRATIVE_CODE</name>
    #     <levelIdentifier type="Constant"/>
    # </level>
    for levelNode in root:
        level(levelNode)


# <level required="true" sourceRoot="true">
#   <rank>1</rank>
#   <name>ALASKA_ADMINISTRATIVE_CODE</name>
#   <citationFormat>Alaska Admin. Code tit. {{TITLE}}</citationFormat>
#   <levelIdentifier type="Composition">
#       <components>
#           <levelIdentifier type="Constant" value="TITLE " decoration="none" required="true"/>
#           <levelIdentifier type="NumericalSequence" decoration="none" required="true" target="true">
#               <example>1</example>
#           </levelIdentifier>
#       </components>
#       <example>TITLE 1</example>
#   </levelIdentifier>
#   <processingComment>include the subject heading within the level tag</processingComment>

def level(root):
    attrs = root.attrib
    print("level : " + str(attrs) if attrs != None else "*None*")
    for child in root:
        tag = child.tag
        if "rank" == tag:
            print("Rank : " + child.text)
        elif "name" == tag:
            print("Name : " + child.text)
        elif "citationFormat" == tag:
            print("citationFormat : " + child.text)
        elif "levelIdentifier" == tag:
            levelIdentifier(child)


def levelIdentifier(root):
    print("levelIdentifier : " + root.text if "text" in root else "*None*")
    for child in root:
        tag = child.tag
        if "example" == tag:
            print("example : " + child.text)
        elif "components" == tag:
            components(child)


# <levelIdentifier type="Constant" value="TITLE " decoration="none" required="true"/>
# <levelIdentifier type="NumericalSequence" decoration="none" required="true" target="true">
#     <example>1</example>
# </levelIdentifier>
def components(root):
    print("components : " + root.text)
    for child in root:
        tag = child.tag
        if "levelIdentifier" == tag:
            componentLevelIdentifier(child)


def componentLevelIdentifier(root):
    attrs = root.attrib
    print("componentLevelIdentifier : " + str(attrs))
    for child in root:
        tag = child.tag
        if "example" == tag:
            componentLevelIdentifierExample(child)


def componentLevelIdentifierExample(root):
    tag = root.tag
    value = root.text
    dict = {tag: value}
    print("componentLevelIdentifierExample : " + str(dict))


# <level required="true" documentRoot="true">
# <rank>2</rank>
# <name>TITLE</name>
# <citationFormat>Ga. Code tit. {{TITLE}}</citationFormat>
def process_lines(lines, dir):
    dict = {}
    dicts = []

    for line in lines:
        line = line.decode('utf-8')
        line = line.strip()
        # print("Line : '%s'" % line)

        if line.startswith("<rank>"):
            if len(dict) > 0:
                dicts.append(dict)
                dict = {}

            dict["level"] = remove_tag(line, "rank")

        if line.startswith("<name>"):
            dict["name"] = remove_tag(line, "name")

        if line.startswith("<citationFormat>"):
            dict["citationFormat"] = remove_tag(line, "citationFormat")
            dicts.append(dict)
            dict = {}

    print("----------------------------------------")
    for d in dicts:
        print(str(d))
    print("----------------------------------------")


def remove_tag(line, tag):
    line = line.replace("<%s>" % tag, "")
    line = line.replace("</%s>" % tag, "")
    return line


def parseEtree(root, dir):
    print("Root tag : " + root.tag)

levelReport.showErrorHeader()
start(levelReport.processSingleFile)
