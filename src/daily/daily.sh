#!/bin/bash

#set -x

clear

#cat sources.txt | cut -d ":" -f 1 \
#  | xargs -I {} grep 'path:\|snapshot_date' {}/manifest.yaml \
#  | cut -d "/" -f 1 \
#  | awk 'NR%2{printf "%s ",$0;next;}1' \
#  | sed 's/path://g' \
#  | sed 's/snapshot_date://g' \
#  | sed 's/T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]//g' \
#  | sort -u


function snap {
gcloud container clusters get-credentials engin-dev --zone us-east4-a  --project r2ai-266621
kubectl --context gke_r2ai-266621_us-east4-a_engin-dev -n trireme-perf exec -it confluent-tools -- bash <<EOFF
cd /data/packages/latest
cat sources.txt | cut -d ":" -f 1 \
  | xargs -I {} grep 'path:\|snapshot_date' {}/manifest.yaml \
  | cut -d "/" -f 1
EOFF
}

#snap | tee /tmp/DIRS.txt

echo "== ==============================================================="
echo "== Snapshot dates"
echo "== ==============================================================="
cat /tmp/DIRS.txt \
  | awk 'NR%2{printf "%s ",$0;next;}1' \
  | sed 's/-//g' \
  | sed 's/path://g' \
  | sed 's/snapshot_date://g' \
  | sed 's/T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]//g' \
  | sed 's/\s\s/ /g' \
  | sed 's/^\s//g' \
  | sort -u \
  | awk '{print $2,$1}'
echo " "
echo " "

# How do I tell what was run and what was suppose to run?
# On the shared drive there are 3 listed under "latest"; however, in the logs there are many that ran after these.
# CODE_OF_GEORGIA
# FINANCIAL_ACTION_TASK_FORCE_FATF_MEDIA
# NATIONAL_RISK_ASSESSMENT
#   Which ones do I pay attention to for the purpose of updating the QA spreadsheet?
#   Do I log each one after a certain time?
#   And what time would that be?

# #######################
# Join every two lines
# #######################
# awk 'NR%2{printf "%s ",$0;next;}1'
# clear ; ls | grep  pack \
#  | xargs -I {} grep 'source_document_name\|snapshot' {}/manifest.yaml \
#  | sed 's/T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]//g' \
#  | awk 'NR%2{printf "%s ",$0;next;}1' \
#  | sort -u
# #######################


#export DDIR=~/tmp
#export FFILE=${DDIR}/token.txt
#export TEMP_FILE=${DDIR}/temp.txt
#export UUSER="user1@getngi.com"
#
#function spacer {
#  echo ""
#  echo "---"
#  echo ""
#}
#
#function doDaily {
#  gcloud container clusters get-credentials engin-dev --zone us-east4-a  --project r2ai-266621
#  kubectl --context gke_r2ai-266621_us-east4-a_engin-dev -n trireme-perf exec -it confluent-tools -- bash <<EOFF
##
## List each daily manifest.yaml file
## Strip out the 'path' and 'snapshot_date'
## Strip off the time from the date
## Combine the lines into one and dispay the output
##
#cat ${TMP_FIL} | cut -d ":" -f 1 | xargs -I{} grep 'path:\|snapshot_date' ${TMP_DIR}/{}/manifest.yaml | sed 's/T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]//g'
#exit
#EOFF
#}
#
#clear
#
## export THE_DATE=`date +'%Y-%m-%d'`
## export THE_DATE="2021-02-17"
#
#
## echo THE DATE : \"${THE_DATE}\"
## echo TMP_DIR  : \"${TMP_DIR}\"
## echo TMP_FIL  : \"${TMP_FIL}\"
## doDaily | tee /tmp/OUTPUT.txt
##
## python3 daily.py
##
## echo THE DATE : \"${THE_DATE}\"
## echo TMP_DIR  : \"${TMP_DIR}\"
## echo TMP_FIL  : \"${TMP_FIL}\"
