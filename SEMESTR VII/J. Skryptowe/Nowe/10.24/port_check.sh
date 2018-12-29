#!/bin/bash 
# Pawel Imiolek gr 1

function is_ip {
    ip=$1

    if [[ ${ip} =~ ^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$ ]]; then
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
    let "number += $((ips[3]))"
    echo ${number}
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

    echo ${ip}
}

function compare_ips {
    if [[ $(ip_to_num $1) -gt $(ip_to_num $2) ]]; then
        return 1
    fi
    return 0
}

function ping_ips {

    local ip1=
    local ip2=
    local portList=$(echo $3 | tr ',' ' ')
    if [[ $(compare_ips $1 $2; echo $?) -eq 0 ]]; then
        ip1=$(ip_to_num $1)
        ip2=$(ip_to_num $2)
    else
        ip1=$(ip_to_num $2)
        ip2=$(ip_to_num $1)
    fi   

    for (( number = $ip1; number <= $ip2; ++number)); do
        ip=$(num_to_ip ${number})
        if [[ $(ping -bq -c 1 -W 2 $ip >& /dev/null; echo $?) -ne 0 ]]; then
            echo "$ip - dead" >&2
            continue
        fi
        
        output="$ip: "
        
        for port in $portList; do
            netcat="$(echo -e "HEAD / HTTP/1.0\n\n" | nc -vtnbw1 $ip $port 2>&1)"
            open=$?
            role=$(echo $netcat | cut -f 4 -d ' ' | sed -r 's/[()]//g')
            server=$(echo "$netcat" | grep "Server: " | cut -f 2 -d ' ')
            if [[ -z $server ]]; then
                server=$(echo $netcat | sed -n '2p')
            fi

            if [[ -z $server ]]; then
                server="open"
            fi

            if [[ $open -eq 0 ]]; then
                if [[ "$role" == "?" ]]; then
                    output+="$port - $server "
                else
                    output+="$role - $server "
                fi
            else
                if [[ "$role" == "?" ]]; then
                    output+="$port - closed"
                else
                    output+="$role - closed "
                fi
            fi
        done
        echo $output
    done
}

function check_ips {
    
    if [[ $1 =~ ^(\s*[0-9]*\s*,)*\s*[0-9]+\s*$ ]]; then
        portList=$1
        startIp=$2
        endIp=$3
    elif [[ $2 =~ ^(\s*[0-9]*\s*,)*\s*[0-9]+\s*$ ]]; then
        portList=$2
        startIp=$1
        endIp=$3
    elif [[ $3 =~ ^(\s*[0-9]*\s*,)*\s*[0-9]+\s*$ ]]; then
        portList=$3
        startIp=$1
        endIp=$2
    else
        echo "Port list is incorrect (eg. \"80\"; \"22,80\")"
        exit 1
    fi

#    if [[ $(echo ${startIp} | grep -q -E "^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$"; echo $?) -eq 0 ]]; then
#        startIp=`host -t a ${startIp} | cut -d ' ' -f 4`
#    fi
#
#    if [[ $(echo ${startIp} | grep -q -E "^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$"; echo $?) -eq 0 ]]; then
#        endIp=`host -t a ${endIp} | cut -d ' ' -f 4`
#    fi

    if [[ $(is_ip ${startIp}; echo $?) -eq 0 ]]; then
        echo "$startIp is not a valid IP"
        exit 1
    elif [[ $(is_ip ${endIp}; echo $?) -eq 0 ]]; then
        echo "$endIp is not a valid IP"
        exit 1
    fi
}

if [[ $# < 3 ]]; then
    echo "Script requires to IP addresses and list of ports"
    exit 1
fi

check_ips $1 $2 $3
ping_ips ${startIp} ${endIp} ${portList} #&> /dev/null
