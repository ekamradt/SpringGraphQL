import os

from reports import filename_match

directories = [{
    "sdc_dir": '/home/ekamradt/git/data-innodata-sdc/US',
    "file_dir": "/media/ekamradt/e/git/data-innodata-files"
}, {
    "sdc_dir": '/home/ekamradt/git/data-dcl-sdc/US',
    "file_dir": "/media/ekamradt/e/git/data-dcl-files"
}]

def start(report_func):
    for dir_dict in directories:
        directory = dir_dict["sdc_dir"]
        file_directory = dir_dict["file_dir"]
        source = 'INNODATA' if 'innodata' in directory else 'DCL'
        for dir_root, dirs, files in os.walk(directory):
            dict = {}
            dict["directory"] = directory
            dict["file_directory"] = file_directory
            dict["dir_root"] = dir_root
            dict["dirs"] = dirs
            dict["source"] = source

            count = len(files)
            if count >= 1:
                for filename in files:
                    lowerName = str(filename).lower()
                    fullFilename = str(dir_root) + "/" + str(filename)
                    partial_dir = "US/" + fullFilename.replace(directory, "")
                    partial_dir = partial_dir.replace(filename, "")
                    partial_dir = partial_dir.replace("//", "/")

                    dict["partial_dir"] = partial_dir
                    dict["filename"] = filename
                    dict["fullFilename"] = fullFilename

                    if not lowerName.endswith("xml") or not lowerName.startswith("sdc"):
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


consoleReportHeader("Filename Match Report")
start(filename_match.processSingleFile)
filename_match.show_report()

#consoleReportHeader("Citation Format Match Report")
#start(citation_format_match.processSingleFile)
