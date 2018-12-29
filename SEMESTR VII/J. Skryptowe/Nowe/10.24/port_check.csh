#!/usr/bin/tcsh -f
# Pawel Imiolek gr 1

if ( $#argv < 3 ) then
    echo "Script requires two IP addresses and port list"
    exit 1
else if ( "`helper/is_ip.csh $1`" == "1" ) then
    echo "$1 is not a valid IP"
    exit 1
else if ( "`helper/is_ip.csh $2`" == "1" ) then
    echo "$2 is not a valid IP"
    exit 1
endif

`ping_ips $1 $2 $3` &> /dev/null
