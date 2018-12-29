#!/bin/tcsh -X
#Krzysztof Kozubek gr.1

#Config
set port = 4321
set server = 1
set client = 0
set ip = localhost
set file = "${HOME}/.licznik.rc"
set store = "${HOME}/.counter"
set counter = 1

#ARG
set p =
set f = 
set i = 
set s = 
set c = 

onintr saveF
goto help
    
clientF:
    eval "nc $ip $port"
    exit 0
	
serverF:
    
    if ( `nc -z $ip $port >& /dev/null; echo $?` == 0 ) then
        echo "Port jest uzywany $port" >&2
        exit 1
    endif

    if ( `touch $store; echo $?` != 0 ) then
        echo "Nie mozna utworzyc pliku $store" >&2
        exit 1
    endif

    set counter = `grep "^$port=[0-9]*" $store | grep -oP "$port=\K([0-9]*)"`
    if ( $counter == "" ) then
        set counter = 1
    endif

    echo "Server $0\: $ip $port - dziala" >&2
    set new_ip = ""
    if ( $ip != "localhost" ) then
        set new_ip = "-s $ip"
    endif
    while (1)
        eval "nc -l -c 'echo $counter' -p $port $new_ip"
        if ( $status != 0 ) then
            exit 1
        endif
        @ counter++
        #echo "$counter" >&2
    end
    goto saveF
    exit 0

#### MAIN
	
help:
set help_msg = "\
    `basename $0` - server = 1, client = inny nr\
\
Opcje:\
    -h          pokaz wiadomosc\
    -s          uruchom server jako echo server\
    -c          uruchom jako klient\
    -p port     zmiana portu\
    -i ip       zmiana IP\
    -f file     plik konfiguracyjny\
    "

set options = `getopt -s tcsh -o hcsf:p:i: -- $argv:q`
if ( $status != 0 ) then
    exit 1
endif

eval set argv = \($options:q\)

while ( 1 )
    switch ($1:q)
        case -h: 
            echo $help_msg:q
            exit 1
            breaksw
        case -c: 
            if ( $s != "" ) then
                echo "Zla skladnia." 1>&2
                exit 1
            else
                set c = 1
            endif
            shift
            breaksw
        case -s: 
            if ( $c != "" ) then
                echo "Zla skladnia." 1>&2
                exit 1
            else
                set s = 1
            endif
            shift
            breaksw
        case -f: 
            if ( $f != "" ) then
                echo "Blad wielokrotne zdefiniowanie pliku konfiguracyjnego" 1>&2
                exit 1
            else if ( ! -e "$2:q" ) then
                echo "Plik `basename $2` nie istnieje" 1>&2
                exit 1
            else
                set f = "$2:q"
            endif
            shift
            shift
            breaksw
        case -p: 
            if ( $p != "" ) then
                echo "Blad port zostal zdefiniowaniy wielokrotnie" 1>&2
                exit 1
            else if ! ( $2:q =~ [0-9][0-9]* ) then
                echo "Zly port" 1>&2
                exit 1
            else if ( `nc -z ${ip:-"localhost"} $2:q; echo $?)` == 0 ) then
                echo "Port jest uzywany $2:q" 1>&2
                exit 1
            else if ( $2 < 1024 ) then
                echo "Zly port" 1>&2
                exit 1
            else
                set p = "$2"
            endif
            shift 
            shift
            breaksw
        case -i: 
            if ( $2 =~ ([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3}) ) then
                if [[ "$REMATCH[1]" -eq 127 && "$REMATCH[2]" -lt 256 && "$REMATCH[3]" -lt 256 && "$REMATCH[4]" -lt 256 ]]; then
                    i=$2
                else
                    echo "Adres musi byc lokalny" 1>&2
                    exit 1
                endif
            else
                echo "Zly adres IP" 1>&2
                exit 1
            endif
            shift 
            shift
            breaksw
        case --: 
            shift 
            break
        case *: 
            echo "Zle arg $1 $2" >&2
            exit 1
            breaksw
    endsw
end 

if ( $f != "" ) then
    set file = "$f"
endif

foreach var ( "`cat $file`" )
    if ( $var == "" || $var =~ ^"#" ) then
        continue
    else if ( $var =~ ^port=([0-9][0-9]*) ) then
        if ( "$REMATCH[1]" < 1024 ) then
            echo "Zle ustawione opcje w pliku konfiguracyjnym portu - $var !!!"
            exit 1
        else
            set port = "$REMATCH[1]"
        endif
    else if ( $var =~ ^file=(..*) ) then
        if ( -e "$REMATCH[1]" ) then
            set file = "$REMATCH[1]"
        else
            echo "Zle ustawione opcje w pliku konfiguracyjnym pliku - $var !!!"
            exit 1
        endif
    else if ( $var =~ ^ip=([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3}) ) then
        if ( "$REMATCH[1]" < 224 && "$REMATCH[2]" < 256 && "$REMATCH[3]" < 256 && "$REMATCH[4]" < 256 ) then
            set ip = "$REMATCH[1]"."$REMATCH[2]"."$REMATCH[3]"."$REMATCH[4]"
        else
            echo "Zle ustawione opcje w pliku konfiguracyjnym adresu - $var !!!"
            exit 1
        endif
    else
        echo "Nieznane opcje w pliku konfiguracyjnym - $var !!!"
        exit 1
    endif
end


if ( $status != 0 ) then
    exit 1
endif

if ( $c != "" ) then
    set client = 1
else
    set server = 1
endif

if ( $i != "" ) then
    set ip = "$i"
endif

if ( $p != "" ) then
    set port = "$p"
endif

if ( $client == 1 ) then
    goto clientF
else
    goto serverF
endif

saveF:
    onintr
    echo "Zakonczenie dzialania servera" >&2
    grep -q "^$port=" $store && sed "s/^$port=.*/$port=$counter/" -i $store || echo "$port=$counter" >> $store
    pkill -P "$$"
    kill "$$"
