#!/bin/bash
#Krzysztof Kozubek gr.1

#Config
port=40000
server=1
ip=
file=${HOME}/.licznik.rc
store=${HOME}/.counter
counter=1


function client {
    eval "nc $ip $port"
}

function server {
    if [[ $(touch $store; echo $?) -ne 0 ]]; then
        echo "Nie mozna utworzyc pliku $store" >&2
        exit 1
    fi

    trap "grep -q '^$port=' $store && sed 's/^$port=.*/$port=$counter/' -i $store || sed '$ a\$port=$counter' -i $store; pkill -P $$" SIGINT SIGQUIT

    echo "Server $0: $ip $port - dziala" >&2
    local new_ip=
    if [[ -n $ip ]]; then
        new_ip="-s $ip"
    fi
    while true; do
        eval "nc -l -c 'echo $counter' -p $port $new_ip"
        if [[ $? -ne 0 ]]; then
            exit 1
        fi
        counter=$(($counter + 1))
        echo "$counter" >&2
    done
}

function validateOptions {

    set -- `getopt "csf:p:i:" "$@"`

    local p=    
    local f=
    local i=
    local s=
    local c=
    
    while [ -n "$1" ]; do
        case "$1" in
            -h) help
                exit 0
                ;;
            -c) if [[ -n $s ]]; then
                    echo "Zla skladnia." 1>&2
                    exit 1
                else
                    c=1
                fi
                ;;
            -s) if [[ -n $c ]]; then
                    echo "Zla skladnia." 1>&2
                    exit 1
                else
                    s=1
                fi
                ;;
            -f) if [[ -n $f ]]; then
                    echo "Blad wielokrotne zdefiniowanie pliku" 1>&2
                    exit 1
                elif [[ ! -e "$2" ]]; then
                    echo "Plik `basename $2` nie istnieje" 1>&2
                    exit 1
                else
                    f=$2
                fi
                ;;
            -p) if [[ -n $p ]]; then
                    echo "Blad port zostal zdefiniowaniy wielokrotnie" 1>&2
                    exit 1
                elif [[ $2 =~ [0-9][0-9]* ]]; then
                    echo "Zly port" 1>&2
                    exit 1
                elif [[ $(nc $ip $2; echo $?) -ne 0 ]]; then
                    echo "Port jest uzywany" 1>&2
                    exit 1
                elif [[ $2 -lt 1024 ]]; then
                    echo "Zly port" 1>&2
                    exit 1
                else
                    p=$2
                    fi;;
            -i) if [[ $2 =~ ([0-9]{1,3}\.){3}([0-9]{1,3}) ]]; then
                    if [[ $BASH_REMATCH[1] -lt 127 && $BASH_REMATCH[2] -lt 256 && $BASH_REMATCH[3] -lt 256 && $BASH_REMATCH[4] -lt 256 ]]; then
                        i=$2
                    else
                        echo "Adres musi byc lokalny" 1>&2
                        exit 1
                    fi
                else
                    echo "Zly adres IP" 1>&2
                    exit 1
                fi
                ;;
        esac
        shift
    done

    if [[ -n $f ]]; then
        `validateFile $f`
    elif [[ -n $file && -e $file ]]; then
        `validateFile $file`
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
}

function validateFile {
    while read var; do
        if [[ -z $var || $var =~ ^# ]]; then
            continue
        elif [[ $var =~ ^port=(.+) ]]; then
            echo $BASH_REMATCH[1] >&2
        elif [[ $var =~ ^file=(.+) ]]; then
            echo $BASH_REMATCH[1] >&2
        elif [[ $var =~ ^ip=(.+) ]]; then
            echo $BASH_REMATCH[1] >&2
        else
            echo "Zle ustawione opcje w pliku konfiguracyjnym - $var" >&2
        fi
    done < $1
}

function help {
echo -e "

    `basename $0` - server = 1, client = inny nr

Opcje:
    -h          pokaz wiadomosc
    -s          uruchom server jako echo server
    -c          uruchom jako klient
    -p port     zmiana portu 
    -i ip       zmiana IP
    -f file     plik konfiguracyjny
    "
} >&2

#### MAIN

`validateOptions`
if [[ $server -eq 1 ]]; then
	`server`
else
	`client`
fi
