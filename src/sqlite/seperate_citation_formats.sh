

python3 gather_innodata_xml_files.py | sort -u


python3 gather_innodata_xml_files.py | sort -u | \
 sed 's/ ch. //g'   | \
 sed 's/ chap //g'   | \
 sed 's/ chap$//g'   | \
 sed 's/ tit //g'   | \
 sed 's/ tit. //g'  | \
 sed 's/ art. //g'  | \
 sed 's/§//g'       | \
 sed 's/{{.*}}//g'  | \
 sed 's/)//g'       | \
 sed 's/[\.|\,]//g' | \
 sed 's/\s+/ /g'    | \
 sed 's/\t+//g'    | \
 sed 's/\n+//g'     | \
 sed 's/\m+//g'     | \
 sed 's/\s+$//g'    | \
 sed 's/^\s+//g'    | \
 awk '{ gsub(/ +$/,""); print }' | \
 awk '{ gsub(/\t+$/,""); print }' | \
 awk '{ gsub(/^ +/,""); print }' | \
 awk '{ gsub(/^\t+/,""); print }' | \
 sort -u            | tee OUTPUT.txt
 
