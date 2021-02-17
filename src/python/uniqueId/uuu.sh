
clear

export TMP_DIR=/home/ekamradt/tmp
export TMP_INNO_DIR=${TMP_DIR}/inno
export TMP_DCL_DIR=${TMP_DIR}/dcl

export INNO_DIR=/home/ekamradt/git/data-innodata-sdc
export DCL_DIR=/home/ekamradt/git/data-dcl-sdc

rm -Rf ${TMP_INNO_DIR}
rm -Rf ${TMP_DCL_DIR}

mkdir -p ${TMP_INNO_DIR}
mkdir -p  ${TMP_DCL_DIR}

cp -R ${INNO_DIR}/* ${TMP_INNO_DIR}
cp -R ${DCL_DIR}/* ${TMP_DCL_DIR}

python uniq.py | tee UNIQUE_ID_MATCHES.txt
