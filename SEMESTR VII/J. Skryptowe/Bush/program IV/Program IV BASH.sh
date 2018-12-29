#!/bin/bash
#Krzysztof Kozubek gr.1

function Validation {

    if [[ $1 =~ ^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$ ]]; then
        if [[ ${BASH_REMATCH[1]} -lt 224 && ${BASH_REMATCH[2]} -lt 256 && ${BASH_REMATCH[3]} -lt 256 && ${BASH_REMATCH[4]} -lt 256 ]]; then
            return 1
        fi
    fi
    return 0
}

function IPToInt {
	IP=$1
	for (( i=0 ; i<4 ; ++i )); 
	do
	   ((IPNUM+=${IP%%.*}*$((256**$((3-${i}))))))
	   IP=${IP#*.}
	done
	echo $IPNUM 
}

function IntToIP {
	echo -n $(($(($(($((${1}/256))/256))/256))%256)).
	echo -n $(($(($((${1}/256))/256))%256)).
	echo -n $(($((${1}/256))%256)).
	echo $((${1}%256)) 
}

function compare_ips {
    if [[ $(IPToInt $1) -gt $(IPToInt $2) ]]; then
        return 1
    fi
    return 0
}

function Ping {

    local ip1=
    local ip2=
    local portList=$(echo $3 | tr ',' ' ')
    if [[ $(compare_ips $1 $2; echo $?) -eq 0 ]]; then
        ip1=$(IPToInt $1)
        ip2=$(IPToInt $2)
    else
        ip1=$(IPToInt $2)
        ip2=$(IPToInt $1)
    fi   

    for (( number = $ip1; number <= $ip2; ++number)); do
        ip=$(IntToIP ${number})
        if [[ $(ping -bq -c 1 -W 2 $ip >& /dev/null; echo $?) -ne 0 ]]; then
            echo "$ip - nieodpowiada" >&2
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
                server="otwarty"
            fi

            if [[ $open -eq 0 ]]; then
                if [[ "$role" == "?" ]]; then
                    output+="$port - $server "
                else
                    output+="$role - $server "
                fi
            else
                if [[ "$role" == "?" ]]; then
                    output+="$port - zamkniety"
                else
                    output+="$role - zamkniety "
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
        echo "Lista portow jest niepoprawna. Przykładowa wartosc 22 80"
        exit 1
    fi

    if [[ $(Validation ${startIp}; echo $?) -eq 0 ]]; then
        echo "Arg $startIp nie jest poprawnym adresem IP. Przykładowa wartosc to 127.0.0.1"
        exit 1
    elif [[ $(Validation ${endIp}; echo $?) -eq 0 ]]; then
        echo "Arg $endIp nie jest poprawnym adresem IP. Przykładowa wartosc to 127.0.0.1"
        exit 1
    fi
}

#### MAIN


if [[ $# < 3 ]]; then
    echo "Złe arg. Przykładowa składnia: IP IP port"
    exit 1
fi

check_ips $1 $2 $3
Ping ${startIp} ${endIp} ${portList} #&> /dev/null