import os

from lxml import etree

from .Components import Components
from .Enums import Enums
from .Level import Level
from .LevelIdentifier import LevelIdentifier

HCONTAINER_FILENAME = "index.xml"
GLOBAL_DICT = {}


def processSingleFile(dict):
    global GLOBAL_DICT
    fullFilename = dict["fullFilename"]
    filename = dict["filename"]
    file_dir = dict["file_directory"]
    partial_dir = dict["partial_dir"]
    directory = dict["directory"]

    filename = filename.lower()
    dirname = filename[4:]
    dirname = dirname.replace(".xml", "")
    dirname = dirname.replace("-", "_")
    dirname = dirname.upper()

    tmp_dir = "%s/%s/%s" % (file_dir, partial_dir, dirname,)
    tmp_dir = tmp_dir.replace("//", "/")
    is_dir = os.path.isdir(tmp_dir)

    partial_filename = "US" + fullFilename.replace(directory, "")
    partial_dir = tmp_dir.replace(file_dir, "")
    partial_dir = partial_dir[1:]

    if is_dir not in GLOBAL_DICT:
        GLOBAL_DICT[is_dir] = []

    tmp_description = {"dirname": partial_dir, "filename": partial_filename}
    GLOBAL_DICT[is_dir].append(tmp_description)

    if is_dir:
        match_citation_formats(dict, tmp_dir, tmp_description)


def match_citation_formats(dict, tmp_dir, tmp_description):
    do_dir(dict, tmp_dir)


def do_dir(dict, tmp_dir):
    dir_stack = [tmp_dir]
    while len(dir_stack) > 0:
        next_dir = dir_stack[0]
        dir_stack = dir_stack[1:]
        for dir_root, dirs, files in os.walk(next_dir):
            if "source" == dir:
                continue

            for filename in files:
                if filename == HCONTAINER_FILENAME:
                    dict["data_dir"] = dir_root
                    dict["data_file"] = HCONTAINER_FILENAME
                    tax = dir_root.replace(next_dir, "")
                    tax = tax[1:]
                    dict["data_taxonomy"] = tax
                    # print("Index.xml : " + dir_root)
                    validate_citation_format(dict)


def validate_citation_format(dict):
    file_dict = extract_citation_info(dict)


def extract_citation_info(dict):
    fullFilename  = dict["fullFilename"]
    root_element = etree.parse(fullFilename)
    root = root_element.getroot()
    # Isolate just sourceOfLaw and the citationFormats

    docType = ""
    for child in root:
        tag = child.tag
        if "levels" == tag:
            levelsWithCitationFormat(child, dict)


def levelsWithCitationFormat(levelsNode, dict):
    level_dict = {}

    for levelNode in levelsNode:
        level = Level()
        for child in levelNode:
            tag = child.tag
            if "rank" == tag:
                level.rank = child.text
            elif "citationFormat" == tag:
                level.citation_format = child.text
            elif "levelIdentifier" == tag:
                level.identifiers.append(levelIdentifier(child, dict))


# -------------------------------------------------------------------------------
# type           = None
# value          = None
# decoration     = None # parenthesis, period, none, any
# required       = False
# target         = False
# padding_length = None
# case           = None  # upper, lower, any
# examples       = []
# components     = []    # contains LevelIndicators
# enums          = []   # enum [ fullName, identifier, citationFormat ]
# -------------------------------------------------------------------------------
def levelIdentifier(identifierNode, dict):
    identifier = LevelIdentifier()
    identifier.type = identifierNode.attrib["type"]
    identifier.value = identifierNode.attrib["value"] if "value" in identifierNode.attrib else identifier.value
    identifier.decoration = identifierNode.attrib[
        "decoration"] if "decoration" in identifierNode.attrib else identifier.decoration
    identifier.required = identifierNode.attrib[
        "required"] if "required" in identifierNode.attrib else identifier.required
    identifier.target = identifierNode.attrib["target"] if "target" in identifierNode.attrib else identifier.target
    identifier.case = identifierNode.attrib["case"] if "case" in identifierNode.attrib else identifier.case

    for child in identifierNode:
        tag = child.tag
        if "components" == tag:
            identifier.components.append(component(child, dict))
        elif "enum" == tag:
            identifier.enums.append(enums(child, dict))
        elif "example" == tag:
            identifier.examples.append(child.text)

    return identifier


def enums(root, dict):
    enums = Enums()
    # for child in root:
    #    tag = child.tag
    #    component.identifiers.append(levelIdentifier(child, dict))
                                                fullFilename
    return enums


def component(root, dict):
    component = Components()
    for child in root:
        tag = child.tag
        component.identifiers.append(levelIdentifier(child, dict))

    return component


def getOriginalLine(child):
    originalLine = str(etree.tostring(child, pretty_print=True), 'UTF-8')
    originalLine = originalLine.replace("\\n", "")
    originalLine = originalLine.replace("\\", "")
    originalLine = originalLine.strip()
    return originalLine


def show_report():
    rpt_prefix = "TrueFalse:"
    print(rpt_prefix + "-- ----------------------------------------")
    print(rpt_prefix + "-- Laws found/not found")
    print(rpt_prefix + "-- ----------------------------------------")
    true_false_rpt_out(True, rpt_prefix)

    print(rpt_prefix + "-- ----------------------------------------")
    true_false_rpt_out(False, rpt_prefix)
    print(rpt_prefix + "-- ----------------------------------------")


def true_false_rpt_out(tf, rpt_prefix):
    dirs = GLOBAL_DICT[tf]
    msg = "Dir Exists : " if tf else "Not Exists : "
    rpts = []
    for dir in dirs:
        dirname = dir["dirname"]
        filename = dir["filename"]
        rpts.append(rpt_prefix + msg + dirname.ljust(60) + ": " + filename)

    rpts.sort()
    for line in rpts:
        print(line)
