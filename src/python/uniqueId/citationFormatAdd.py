import os

directories = ['/home/ekamradt/git/data-innodata-sdc/', '/home/ekamradt/git/data-dcl-sdc/']
#directories = ['/home/ekamradt/git/data-dcl-sdc/US']

def doStart():
    for directory in directories:
        source = 'INNODATA' if 'innodata' in directory else 'DCL'
        for dir_root, dirs, files in os.walk(directory):

            dict = {}
            dict["dir_root"] = dir_root
            dict["dirs"] = dirs
            dict["source"] = source

            count = len(files)
            if count >= 1:
                for in_file in files:
                    lowerName = str(in_file).lower()
                    fullFilename = str(dir_root) + "/" + str(in_file)
                    if lowerName.endswith("xml") and lowerName.startswith("sdc"):
                        #print(fullFilename)
                        sdc_file = open(fullFilename, 'r')
                        lines = sdc_file.readlines()
                        sdc_file.close()

                        if "\<uniqueId\>" not in content:
                            tmp = "\\\n    <uniqueId>%s<\/uniqueId>" % (unique_id,)
                            command = "sed --in-place 's/\/rootUri>/\/rootUri>%s/g' \"%s\" " % (tmp, fullFilename,)
                            print(command)
                            os.system(command)

                        #sdc_file = open(fullFilename, "r")
                        #content = sdc_file.read()
                        #sdc_file.close()
                        ##content = content.replace("/rootUri>", "/rootUri>" + tmp)
                        #sdc_file = open(fullFilename, "w")
                        #sdc_file.write(content)
                        #sdc_file.close()

                        #print("%s : %s : %s" % (unique_id, identification.ljust(40), fullFilename,))


def getKeys(lines):
    identification = None
    rootUri = None
    for line in lines:
        if "<identification>" in line:
            identification = clean_tags(line, "identification")
        elif "<rootUri>" in line:
            rootUri = clean_tags(line, "rootUri")

    return identification, rootUri


def clean_tags(line, tag):
    temp = line
    temp = temp.replace("<" + tag + ">", "")
    temp = temp.replace("</" + tag + ">", "")
    temp = temp.strip()
    return temp


doStart()
