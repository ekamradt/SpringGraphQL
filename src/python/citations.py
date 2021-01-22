import os

directory = '/home/ekamradt/git/data-innodata/US'

def start():
    for dir_root, dirs, files in os.walk(directory):
        count = len(files)
        if count >= 1:
            for inFile in files:
                if inFile.endswith("xml") and (inFile.startswith("brd") or inFile.startswith("sdc")):
                    fullFilename = str(dir_root) + "/" + str(inFile)
                    print("=================")
                    print("=== Root  : " + str(dir_root))
                    print("=== Dits  : " + str(dirs))
                    print("=== inFile : " + str(inFile))
                    print("=== fullFilename : " + str(fullFilename))
                    print("")
                    xml_file = open(fullFilename, 'rb')
                    # content = xml_file.read()
                    lines = xml_file.readlines()
                    xml_file.close()

                    process_lines(lines, dir_root)
                    # try:
                    #    xml_root = etree.fromstring(content)
                    #    parseEtree(xml_root, dir_root)
                    # except:
                    #    e = sys.exc_info()[0]
                    #    print("*** ERROR *** : %s" % e)
                    #    print("Failed to read file : '" + fullFilename + "'")
                    #    print("*** ERROR ***")


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
        #print("Line : '%s'" % line)

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


start()
# manifest_file = open(manifest_file_name, 'r')
# Lines = manifest_file.readlines()


# print("=================")
# print("=== Root  : " + str(root))
# print("=== Dits  : " + str(dirs))
# print("=== Files : " + str(files))
# print(str(len(files)))
