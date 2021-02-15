def do_issuance_type(filename, statecode, statelower):
    if filename.startswith("sdc") or filename.startswith("brd"):
        temp_type = filename[4:]
    else:
        temp_type = filename.replace("US-", "")
        temp_type = temp_type.replace(statecode + "-", "")
        if temp_type.startswith(statecode):
            temp_type = temp_type[3:]
    temp_type = temp_type.replace(statelower + "-", "")
    temp_type = temp_type.replace(".xml", "")
    temp_type = temp_type.replace("-", " ").upper()

    issuance_type = ""
    parts = temp_type.split(" ")
    for part in parts:
        part = part.strip()
        if not part or len(part) == 0:
            continue

        contains_digit = any(map(str.isdigit, part))
        if contains_digit:
            continue

        issuance_type += " " + part

    return issuance_type.strip()

def states():
    statedict = {}
    statedict['AL'] = 'ALABAMA'
    statedict['AK'] = 'ALASKA'
    statedict['AS'] = 'AMERICAN SAMOA'
    statedict['AZ'] = 'ARIZONA'
    statedict['AR'] = 'ARKANSAS'
    statedict['CA'] = 'CALIFORNIA'
    statedict['CO'] = 'COLORADO'
    statedict['CT'] = 'CONNECTICUT'
    statedict['DE'] = 'DELAWARE'
    statedict['DC'] = 'DISTRICT OF COLUMBIA'
    statedict['FD'] = 'FEDERAL'
    statedict['FL'] = 'FLORIDA'
    statedict['GA'] = 'GEORGIA'
    statedict['GU'] = 'GUAM'
    statedict['HI'] = 'HAWAII'
    statedict['ID'] = 'IDAHO'
    statedict['IL'] = 'ILLINOIS'
    statedict['IN'] = 'INDIANA'
    statedict['IA'] = 'IOWA'
    statedict['KS'] = 'KANSAS'
    statedict['KY'] = 'KENTUCKY'
    statedict['LA'] = 'LOUISIANA'
    statedict['ME'] = 'MAINE'
    statedict['MD'] = 'MARYLAND'
    statedict['MA'] = 'MASSACHUSETTS'
    statedict['MI'] = 'MICHIGAN'
    statedict['MN'] = 'MINNESOTA'
    statedict['MS'] = 'MISSISSIPPI'
    statedict['MO'] = 'MISSOURI'
    statedict['MT'] = 'MONTANA'
    statedict['NE'] = 'NEBRASKA'
    statedict['NV'] = 'NEVADA'
    statedict['NH'] = 'NEW HAMPSHIRE'
    statedict['NJ'] = 'NEW JERSEY'
    statedict['NM'] = 'NEW MEXICO'
    statedict['NY'] = 'NEW YORK'
    statedict['NC'] = 'NORTH CAROLINA'
    statedict['ND'] = 'NORTH DAKOTA'
    statedict['MP'] = 'NORTHERN MARIANA IS'
    statedict['OH'] = 'OHIO'
    statedict['OK'] = 'OKLAHOMA'
    statedict['OR'] = 'OREGON'
    statedict['PA'] = 'PENNSYLVANIA'
    statedict['PR'] = 'PUERTO RICO'
    statedict['RI'] = 'RHODE ISLAND'
    statedict['SC'] = 'SOUTH CAROLINA'
    statedict['SD'] = 'SOUTH DAKOTA'
    statedict['TN'] = 'TENNESSEE'
    statedict['TX'] = 'TEXAS'
    statedict['UT'] = 'UTAH'
    statedict['VT'] = 'VERMONT'
    statedict['VA'] = 'VIRGINIA'
    statedict['VI'] = 'VIRGIN ISLANDS'
    statedict['WA'] = 'WASHINGTON'
    statedict['WV'] = 'WEST VIRGINIA'
    statedict['WI'] = 'WISCONSIN'
    statedict['WY'] = 'WYOMING'
    return statedict
