

#cat tt.info

while IFS= read -r line
do
  #######echo Line: "${line}"
  ary=(${line})
  level=${ary[0]}
  sdc=${ary[1]}

  tmpfil=`find -L /media/ekamradt/e/sdc -iname "sdc*.xml" | xargs -I {} grep -l "identification>${sdc}<"  {}`

  grep lawConfig ${tmpfil} > /dev/null

  err=$?

  if [[ ${err} -eq 0 ]]; then
    #echo FOUND in ${tmpfil}
    level2=`grep -A 2 "<lawConfig " ${tmpfil} | grep "<name>" | sed 's/<name>//g' | sed 's/<\/name>//g' | sed 's/ \+//g'`
    if [[ "${level}" = "${level2}" ]]; then
      export dummy="stupid"
    else
      echo ERROR Should be at level "'${level}'" not "'${level2}'"  in "'$tmpfil}'"
    fi
  else
    export t=9
    ##echo NOT FOUND in ${tmpfil}
    tmpline=`grep -n "name>${level}<" ${tmpfil} | cut -d ":" -f 1`
    tmpline=`expr ${tmpline} - 2`
    sed -i "${tmpline} a \ \ \ \ \  <lawConfig isMvd=\"true\"/>" ${tmpfil}
    echo fixed level ${level} in file ${tmpfil}
  fi
  #echo ${level} : ${sdc}
done < "tt.info"