
export TMP_DIR=/home/ekamradt/git/SpringGraphQL/src/bash
cd ~/git/data-innodata
find . -iname "sdc*.xml" | sort -u > ${TMP_DIR}/OUT1.txt

cd ~/git/data-innodata-sdc
find . -iname "sdc*.xml" | sort -u > ${TMP_DIR}/OUT2.txt

diff ${TMP_DIR}/OUT1.txt ${TMP_DIR}/OUT2.txt