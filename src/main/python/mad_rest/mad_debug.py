import json
from mad_rest import mad_constant

baseurl = mad_constant.baseurl()

def raise_error(ah_response, error_message):
    try:
        api_error = json.dumps(ah_response['apiError'], sort_keys=True, indent=2, separators=(',', ': '))
        errMsg = error_message +  " '{}' {}".format(ah_response['status'], api_error)
    except:
        errMsg = json.dumps(ah_response, sort_keys=True, indent=2, separators=(',', ': '))

    raise Exception(errMsg)
