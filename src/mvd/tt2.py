

#cat tt.info

fil = open("tt.info", "r")
lines = fil.readlines()
fil.close()

for line in lines:
  print("Line : " + line)
  parts = line.split(" ")
  level = parts[0]
  sdc = parts[1]
  
