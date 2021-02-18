
clear
#python3 citations.py

export TMP_FIL=/tmp/TMP_CIT_FILE.info
python3 citations.py > ${TMP_FIL}

grep "|" ${TMP_FIL} | tee SDC_GLITCHES.csv
grep "ExEx:" ${TMP_FIL} | sed 's/ExEx://g' | sed 's/~/\n/g' > LEVEL_IND.info

