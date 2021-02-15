
clear

cat OUTPUT.info \
  | sed 's/identification : //g' \
  | cut -d " " -f 1 \
  | sed 's/ALABAMA_//g' \
  | sed 's/ALASKA_//g' \
  | sed 's/ARIZONA_//g' \
  | sed 's/ARKANSAS_//g' \
  | sed 's/CALIFORNIA_//g' \
  | sed 's/COLORADO_//g' \
  | sed 's/CONNECTICUT_//g' \
  | sed 's/DELAWARE_//g' \
  | sed 's/FLORIDA_//g' \
  | sed 's/GEORGIA_//g' \
  | sed 's/HAWAII_//g' \
  | sed 's/IDAHO_//g' \
  | sed 's/ILLINOIS_//g' \
  | sed 's/INDIANA_//g' \
  | sed 's/IOWA_//g' \
  | sed 's/KANSAS_//g' \
  | sed 's/KENTUCKY_//g' \
  | sed 's/LOUISIANA_//g' \
  | sed 's/MAINE_//g' \
  | sed 's/MARYLAND_//g' \
  | sed 's/MASSACHUSETTS_//g' \
  | sed 's/MICHIGAN_//g' \
  | sed 's/MINNESOTA_//g' \
  | sed 's/MISSISSIPPI_//g' \
  | sed 's/MISSOURI_//g' \
  | sed 's/MONTANA_//g' \
  | sed 's/NEBRASKA_//g' \
  | sed 's/NEVADA_//g' \
  | sed 's/NEW_HAMPSHIRE_//g' \
  | sed 's/NEW_JERSEY_//g' \
  | sed 's/NEW_MEXICO_//g' \
  | sed 's/NEW_YORK_//g' \
  | sed 's/NORTH_CAROLINA_//g' \
  | sed 's/NORTH_DAKOTA_//g' \
  | sed 's/OHIO_//g' \
  | sed 's/OKLAHOMA_//g' \
  | sed 's/OREGON_//g' \
  | sed 's/PENNSYLVANIA_//g' \
  | sed 's/RHODE_ISLAND_//g' \
  | sed 's/SOUTH_CAROLINA_//g' \
  | sed 's/SOUTH_DAKOTA_//g' \
  | sed 's/TENNESSEE_//g' \
  | sed 's/TEXAS_//g' \
  | sed 's/UTAH_//g' \
  | sed 's/VERMONT_//g' \
  | sed 's/VIRGINIA_//g' \
  | sed 's/WASHINGTON_//g' \
  | sed 's/WEST VIRGINIA_//g' \
  | sed 's/WISCONSIN_//g' \
  | sed 's/WYOMING_//g' 
