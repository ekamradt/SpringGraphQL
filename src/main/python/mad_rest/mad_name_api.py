import requests
import json
from mad_rest import mad_debug, mad_constant

baseurl = mad_constant.baseurl()
headers = {'Content-type': 'application/json'}

def create_name(params):
    response = requests.post(baseurl + "/name", data = params, headers = headers)
    print("Create Name : " + str(response))
    name_response = json.loads(response.content)

    if name_response['status'] != 200:
        mad_debug.raise_error(name_response, "Create Name : Returned Bad Http Status")
    return name_response['entity']

def get_name(name_id):
    response = requests.get(baseurl + "/name/{}".format(name_id))
    print("Get Name : " + str(response))
    name_response = json.loads(response.content)

    if name_response['status'] != 200:
        mad_debug.raise_error(name_response, "Get Name : Returned Bad Http Status")
    return name_response['entity']

def update_name(name_id, params):
    #response = requests.request(method = 'put', url = baseurl + "/name/{}".format(name_id), data = params, headers = headers)
    response = requests.put(baseurl + "/name/{}".format(name_id), data = params, headers = headers)
    print("Update Name : " + str(response))

    update_response = json.loads(response.content)
    if update_response['status'] != 200:
        mad_debug.raise_error(update_response, "Update Name : Returned Bad Http Status")
    return update_response['entity']

def delete_name(name_id):
    response = requests.delete(baseurl + "/name/{}".format(name_id))
    print("Delete Name : " + str(response))

    delete_response = json.loads(response.content)
    if delete_response['status'] != 200:
        mad_debug.raise_error(delete_response, "Delete Name : Returned Bad Http Status")
    return delete_response['entity']

def list_names():
    response = requests.get(baseurl + "/names")
    print("List Names : " + str(response))

    list_response = json.loads(response.content)
    if list_response['status'] != 200:
        mad_debug.raise_error(list_response, "List Names : Returned Bad Http Status")
    return list_response['entity']

def list_names_and_delete():
    names = list_names()
    i = 0
    for name in names:
        i += 1
        print("--------------------- " + str(i))
        print("Deleting Name Id : " + name['nameId'])
        delete_name(name['nameId'])
