#!/bin/bash

#set -x

clear

gcloud container clusters get-credentials engin-dev --zone us-east4-a  --project r2ai-266621
kubectl --context gke_r2ai-266621_us-east4-a_engin-dev -n trireme-perf exec -it confluent-tools -- bash <<EOFF

cd /data/packages/
ls | xargs -I{} grep 'path:\|snapshot_date' /data/packages/{}/manifest.yaml | sed 's/T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]//g'
EOFF

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
