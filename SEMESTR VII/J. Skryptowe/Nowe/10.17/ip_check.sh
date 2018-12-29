#!/bin/bash
# Pawel Imiolek gr 1

function is_ip {
    ip=$1

    if [[ $ip =~ ^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$ ]]; then
        if [[ ${BASH_REMATCH[1]} -lt 224 && ${BASH_REMATCH[2]} -lt 256 && ${BASH_REMATCH[3]} -lt 256 && ${BASH_REMATCH[4]} -lt 256 ]]; then
            return 1
        fi
    fi
    return 0
}

function ip_to_num {
    local ips=(`echo $1 | tr '.' ' '`)
    local number=0

    let "number += $((ips[0] << 24))"
    let "number += $((ips[1] << 16))"
    let "number += $((ips[2] << 8))"
    let "number += ${ips[3]}"
    echo $number
}

function num_to_ip {
    local number=$1
    local ip=""
    local tmp=0

    let "tmp = $(($number >> 24))"
    ip+="$tmp."
    let "number -= $(($tmp << 24))"
    let "tmp = $(($number >> 16))"
    ip+="$tmp."
    let "number -= $(($tmp << 16))"
    let "tmp = $(($number >> 8))"
    ip+="$tmp."
    let "number -= $(($tmp << 8))"
    ip+="$number"

    echo $ip
}

function compare_ips {
    local ip1=(`echo $1 | tr "." " "`)
    local ip2=(`echo $2 | tr "." " "`)
    for i in {0..3}; do
        if [[ ${ip1[i]} -gt ${ip2[i]} ]]; then
            return 1
        fi
    done
    return 0
}

function ping_ips {
    if [[ $(compare_ips $1 $2; echo $?) -eq 0 ]]; then
        local ip1=`ip_to_num $1`
        local ip2=`ip_to_num $2`
    else
        local ip1=`ip_to_num $2`
        local ip2=`ip_to_num $1`
    fi   

    for (( number = $ip1; number <= $ip2; ++number)); do
        ip=$(num_to_ip $number)
        (ping -c 1 -W 3 $ip)
        if [[ $? -eq 0 ]]; then
            echo "$ip - alive" >&2
        else
            echo "$ip - dead" >&2
        fi
    done
}   

startIp=$1
endIp=$2

$(echo $1 | grep -q -E "^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$")
if [[ $? -ne 0 ]]; then
    startIp=`host -t a $startIp | cut -d ' ' -f 4`
fi

$(echo $2 | grep -q -E "^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$")
if [[ $? -ne 0 ]]; then
    endIp=`host -t a $endIp | cut -d ' ' -f 4`
fi

if [[ $# < 2 ]]; then
    echo "Script requires to IP addresses"
    exit 1
elif [[ $(is_ip $startIp; echo $?) -eq 0 ]]; then
    echo "$startIp is not a valid IP"
    exit 1
elif [[ $(is_ip $endIp; echo $?) -eq 0 ]]; then
    echo "$endIp is not a valid IP"
    exit 1
fi

`ping_ips $startIp $endIp` &> /dev/null
