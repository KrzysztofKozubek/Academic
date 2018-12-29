#!/bin/bash
# Paweł Imiołek gr. 1

function help {
    msg="
NAME
    $0 - prints login name, first name and last name of user
    
SYNOPSIS
    $0 [OPTION]
    
DESCRIPTION
    Script prints login name, first name and last name of user.

OPTIONS    
    -h, --help   displays this message
    -q, --quiet  exits with code 0       
"
    
    echo -e "$msg"
}

q=0
other=""

for arg in "$@"; do
    case "$arg" in 
        -*h*| --help )
            help
            exit 0;;
        -*q*|--quiet)
            q=1
            shift;;
        -*)
            if [ -z "$other" ]; then
                other="$arg"
            fi
            shift;;
        *)
            shift;;
    esac
done

if [ $q -ne 0 ]; then
    exit 0;
elif [ -n "$other" ]; then
    echo -e "\n!!! Invalid parameter -- '$other' !!!"
    help
    exit 1;
else
    echo $USER `getent passwd $USER | cut -d : -f 5 | cut -d , -f 1`
fi
