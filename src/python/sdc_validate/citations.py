import os

from reports import errorReport, bracketReport, levelReport

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


def consoleReportHeader(msg):
    print("-- -----------------------------------------------------")
    print("-- " + msg)
    print("-- -----------------------------------------------------")


errorReport.showErrorHeader()

consoleReportHeader("Level Report")
start(levelReport.processSingleFile)

consoleReportHeader("Bracket Report")
start(bracketReport.processSingleFile)
