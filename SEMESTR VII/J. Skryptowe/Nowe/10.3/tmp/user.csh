#!/usr/bin/tcsh
# Paweł Imiołek gr. 1

set msg = "\nNAME\n    $0 - prints login name, first name and last name of user\n\nSYNOPSIS\n    $0 [OPTION]\n\nDESCRIPTION\n     Script prints login name, first name and last name of user\n\nOPTIONS\n    -h, --help   displays this message\n    -q, --quiet  exits with code 0\n"

set q = 0
set other = ""

foreach arg ("$argv")
    switch ("$arg")
    case -*h*:
    case --help:
        echo "$msg"
        exit 0
    case -*q*:
    case --quiet:
            set q = 1
            breaksw
    case -*:
            set other = "$arg"
            breaksw
    case default:
            breaksw
    endsw
end

if ( $q != 0 ) then
    exit 0;
else if ( "$other" != "" ) then
    echo "\n\!\!\! Invalid parameter -- '$other' \!\!\!"
    echo "$msg"
    exit 1;
else
    echo $USER `getent passwd $USER | cut -d : -f 5 | cut -d , -f 1`
endif
