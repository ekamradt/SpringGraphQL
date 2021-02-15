


cat OUTPUT.txt | \
 sed 's/[0-9]//g'    | \
 sort -u            | tee OUTPUT2.txt


#cat OUTPUT.txt | sort -u | \
# sed 's/ ch. //g'   | \
# sed 's/ chap //g'   | \
# sed 's/ chap$//g'   | \
# sed 's/ tit //g'   | \
# sed 's/ tit. //g'  | \
# sed 's/ art. //g'  | \
# sed 's/§//g'       | \
# sed 's/{{.*}}//g'  | \
# sed 's/)//g'       | \
# sed 's/[\.|\,]//g' | \
# sed 's/\s+/ /g'    | \
# sed 's/\t+//g'    | \
# sed 's/\n+//g'     | \
# sed 's/\m+//g'     | \
# sed 's/\s+$//g'    | \
# sed 's/^\s+//g'    | \
# sed 's/[0-9]//g'    | \
# sed 's/["|-]//g'    | \
# awk '{ gsub(/ +$/,""); print }' | \
# awk '{ gsub(/\t+$/,""); print }' | \
# awk '{ gsub(/^ +/,""); print }' | \
# awk '{ gsub(/^\t+/,""); print }' | \
# sort -u            | tee OUTPUT2.txt
#
