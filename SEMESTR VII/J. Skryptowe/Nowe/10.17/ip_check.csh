#!/usr/bin/tcsh -f
# Pawel Imiolek gr 1

if ( $#argv < 2 ) then
    echo "Script requires to IP addresses"
    exit 1
else if ( "`helper/is_ip.csh $1`" == "1" ) then
    echo "$1 is not a valid IP"
    exit 1
else if ( "`helper/is_ip.csh $2`" == "1" ) then
    echo "$2 is not a valid IP"
    exit 1
endif

#`ping_ips $1 $2` &> /dev/null
