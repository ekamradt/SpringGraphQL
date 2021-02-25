from lxml import etree

# ##############################################################################
# Just display citation formats and full filenames
# ##############################################################################
def processSingleFile(dict):
    full_filename = dict["fullFilename"]
    root_element = etree.parse(full_filename)
    root = root_element.getroot()

    file = open(full_filename, 'r')
    content = file.read()
    file.close()

    error = None
    lines = content.split("\n")
    for line in lines:
        if "<enum " in line:
            if "asCitation" not in line:
                short_line = line.strip()
                error = f"asCitation missing in enum '{short_line}' in file '{full_filename}'"
                break

    if error is not None:
        enumOut(error)

def enumOut(msg):
    print("EnumCitation:" + msg)

