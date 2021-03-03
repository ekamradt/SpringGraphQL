

#cat tt.info

rm OUT.info
while IFS= read -r line
do
  ary=(${line})
  level=${ary[0]}
  sdc=${ary[1]}

  unique=`find -L /media/ekamradt/e/sdc -iname "sdc*.xml" | xargs -I {} grep -A 8 "identification>${sdc}<"  {} | grep uniqueId \
    | sed 's/<uniqueId>//g' | sed 's/<\/uniqueId>//g'  | sed 's/\t\+//g' | sed 's/\s\+//g' | sed 's/ \+//g'`


  echo ${unique},${sdc} >> OUT.info
done < "tt.info"