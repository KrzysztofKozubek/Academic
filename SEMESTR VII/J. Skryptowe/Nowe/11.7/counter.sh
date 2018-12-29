#!/bin/bash

port=4001
server=1
ip=
file=${HOME}/.licznik.rc
store=${HOME}/.counter
counter=1

#trap "sed -i 's/^$port=.*/$port=$counter/ $store" SIGTERM SIGKILL


function server {
    if [[ $(nc -z ${ip:-localhost} $port; echo $?) -eq 0 ]]; then
        echo "!!! Error: Something is using port $port. Aborting... !!!" >&2
        return 1
    fi

    if [[ $(touch $store; echo $?) -ne 0 ]]; then
        echo "!!! Error: Could not create store file in $store" >&2
        return 1
    fi

    counter=$(grep "^$port=[0-9]*" $store | grep -oP "$port=\K([0-9]*)")
    if [[ -z $counter ]]; then
        counter=1
    fi

    trap save_counter SIGINT SIGQUIT

    echo "Server $0: $ip $port - running" >&2
    local new_ip=
    if [[ -n $ip ]]; then
        new_ip="-s $ip"
    fi
    while true; do
        eval "nc -l -c 'echo $counter' -p $port $new_ip"
        if [[ $? -ne 0 ]]; then
            return 1
        fi
        counter=$(($counter + 1))
        #echo "$counter" >&2
    done
}

function client {
    eval "nc ${ip:-localhost} $port"
}

function save_counter {
    echo "!!! Server terminated !!!" >&2
    grep -q "^$port=" $store && sed "s/^$port=.*/$port=$counter/" -i $store || echo "$port=$counter" >> $store
    pkill -P $$
    #kill $$
}

function help_msg {
echo -e "
NAME
    `basename $0` - script works as echo server or client (depends on option)

SYNOPSIS
    `basename $0` [-options]

DESCRIPTION
    `basename $0` - scripts works as echo server or as client depending on option. As server (default) listens for incoming connections and return a number of successful connections. 
    As client checks the specified port and returns a respond (if there's a respond) then exits.

OPTIONS
    -h          show this message
    -s          run script as echo server (default)
    -c          run script as client
    -p port     change port, more than 1024 (default: 40000)
    -i ip       force to use specified ip (default: all local ips)
    -f file     specify config file. File should contains NAME=VALUE in separate lines where NAME can only be port|file|ip (default: ${HOME}/.licznik.rc)
    "
} >&2

p=    
f=
i=
s=
c=

options=$(getopt -o hcsf:p:i: -- "$@")
if [[ $? -ne 0 ]]; then
    exit 1
fi

eval set -- "$options"

while [[ -n $1 ]]; do
    case "$1" in
        -h) help_msg
            exit 1
            ;;
        -c) if [[ -n $s ]]; then
                echo "!!! Error: Can't invoke with options -s and -c !!!" 1>&2
                exit 1
            else
                c=1
            fi
            shift
            ;;
        -s) if [[ -n $c ]]; then
                echo "!!! Error: Can't invoke with options -s and -c !!!" 1>&2
                exit 1
            else
                s=1
            fi
            shift
            ;;
        -f) if [[ -n $f ]]; then
                echo "!!! Error: -f - File defined multiple times !!!" 1>&2
                exit 1
            elif [[ ! -e "$2" ]]; then
                echo "!!! Error: File `basename $2` does not exist !!!" 1>&2
                exit 1
            else
                f=$2
            fi
            shift 2
            ;;
        -p) if [[ -n $p ]]; then
                echo "!!! Error: -p - Port defined multiple times !!!" 1>&2
                exit 1
            elif ! [[ $2 =~ [0-9][0-9]* ]]; then
                echo "!!! Error: Invalid port number !!!" 1>&2
                exit 1
            elif [[ $(nc -z ${ip:-"localhost"} $2; echo $?) -eq 0 ]]; then
                echo "!!! Error: Port is in use $2 !!!" 1>&2
                exit 1
            elif [[ $2 -lt 1024 ]]; then
                echo "!!! Error: Reserved port. Use port > 1024 !!!" 1>&2
                exit 1
            else
                p=$2
            fi
            shift 2
            ;;
        -i) if [[ $2 =~ ([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3}) ]]; then
                if [[ ${BASH_REMATCH[1]} -eq 127 && ${BASH_REMATCH[2]} -lt 256 && ${BASH_REMATCH[3]} -lt 256 && ${BASH_REMATCH[4]} -lt 256 ]]; then
                    i=$2
                else
                    echo "!!! Error: Not a local IP (127.*) !!!" 1>&2
                    exit 1
                fi
            else
                echo "!!! Error: Invalid IP !!!" 1>&2
                exit 1
            fi
            shift 2
            ;;
        --) shift 
            ;;
        *) echo "Error opts $1 $2" >&2
            exit 1
            ;;
    esac
done

if [[ -n $f ]]; then
    file=$f
fi

while read var; do
    if [[ -z $var || $var =~ ^# ]]; then
        continue
    elif [[ $var =~ ^port=([0-9][0-9]*) ]]; then
        if [[ ${BASH_REMATCH[1]} < 1024 ]]; then
            echo "!!! Error: Invalid port in config file ( < 1024 ) - $var !!!"
            exit 1
        else
            port=${BASH_REMATCH[1]}
        fi
    elif [[ $var =~ ^file=(..*) ]]; then
        if [[ -e ${BASH_REMATCH[1]} ]]; then
            file=${BASH_REMATCH[1]}
        else
            echo "!!! Error: Non existing file in config file - $var !!!"
            exit 1
        fi
    elif [[ $var =~ ^ip=([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3}) ]]; then
        if [[ ${BASH_REMATCH[1]} -lt 224 && ${BASH_REMATCH[2]} -lt 256 && ${BASH_REMATCH[3]} -lt 256 && ${BASH_REMATCH[4]} -lt 256 ]]; then
            ip=${BASH_REMATCH[1]}.${BASH_REMATCH[2]}.${BASH_REMATCH[3]}.${BASH_REMATCH[4]}
        else
            echo "!!! Error: Invalid ip in config file - $var !!!"
            exit 1
        fi
    else
        echo "!!! Error: Unknown option in config file - $var !!!"
        exit 1
    fi
done < $file


if [[ $? -ne 0 ]]; then
    exit 1
fi

if [[ -n $c ]]; then
    client=1
else
    server=1
fi

if [[ -n $i ]]; then
    ip=$i
fi

if [[ -n $p ]]; then
    port=$p
fi

if [[ $client -eq 1 ]]; then
    client
else
    server
fi
