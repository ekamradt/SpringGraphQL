
clear
#python3 formats.py

export TMP_FIL=/tmp/TMP_CIT_FILE.info
python3 citations.py > ${TMP_FIL}

grep "|" ${TMP_FIL} | tee SDC_GLITCHES.csv
grep "ExEx:" ${TMP_FIL} | sed 's/ExEx://g' | sed 's/~/\n/g' > LEVEL_INFO.info
grep "EnumCitation:" ${TMP_FIL} | sed 's/EnumCitation://g' > ENUM_CITATION.info

