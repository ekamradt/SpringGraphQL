import json
from mad_rest import mad_name_api

# ##############################################################################
#
# ##############################################################################

# ##########################################################
# Create Name
#
print("----------------- Create Name --------------")
name = mad_name_api.create_name('{ "firstName":"Fred2", "lastName": "Test Test2" }')
assert name is not None, "Create Name returned None for Name."

name_id = name["nameId"]
assert name_id is not None, "Create Name returned unexpected name id {}".format(name_id)

print("Created Name Id : " + str(name_id))

print("----------------- Get Name --------------")
name = mad_name_api.get_name(name_id)
print(json.dumps(name, sort_keys=True, indent=2, separators=(',', ': ')))
name_cid_2 = name["nameId"]
assert name_id == name_cid_2, "Name Ids do not match : {} / {}".format(name_id, name_cid_2)

print("----------------- Lines Names --------------")
names = mad_name_api.list_names()
for name in names:
    print(json.dumps(name, sort_keys=True, indent=2, separators=(',', ': ')))

name = mad_name_api.update_name(name_id, '{ "firstName":"Fred3", "lastName": "Test Test3" }')
print("----------------- Update Name --------------")
print(json.dumps(name, sort_keys=True, indent=2, separators=(',', ': ')))

print("----------------- Delete Name --------------")
names = mad_name_api.list_names()
found = False
for name in names:
    print(json.dumps(name, sort_keys=True, indent=2, separators=(',', ': ')))
    id = name["nameId"]
    if name_id == id:
        found = True
        break

assert found, "Could not find matching name for name id {}".format(name_id)

