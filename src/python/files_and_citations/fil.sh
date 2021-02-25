
clear
#python3 formats.py

python3 filcitations.py > OUTPUT.info

cat OUTPUT.info | grep "TrueFalse:" | sed 's/TrueFalse://g' > TrueFalse_Filename_Match.rpt
cat OUTPUT.info | grep -v "TrueFalse:"