

print ("************")

# ##############################################################################
# 
# ##############################################################################
file1 = open('OUTPUT.txt', 'r')
Lines = file1.readlines()
count = 0

# Strips the newline character

infos = {}
name = ""

for line in Lines:
  if "path:" in line:
    tmp = line.replace("path:", "")
    tmp = tmp.strip()
    parts = tmp.split("/")
    tmp = parts[0]
    tmp = tmp.replace("-", "")
    tmp = tmp.strip()
    if tmp != name:
      name = tmp
      infos[name] = ""
    continue

  if "snapshot_date:" in line:
    tmp = line.replace("snapshot_date:", "")
    tmp = tmp.strip()
    infos[name] = tmp


#for info in sorted(infos):
lst = sorted(infos.items(), key=lambda kv: kv[1] + kv[0])
for info in lst:
  print(info[0].ljust(60) + " : " + str(info[1]))

# - path: SEC_FINAL_RULES/2020-12-03/FINAL/RULES/RELEASENUMBER-ReleaseNoIC34128/
# snapshot_date: 2021-02-16
# - path: KANSAS_STATUTES/2021-01-04/STATUTES/CHAPTER-083/
# snapshot_date: 2021-02-16
