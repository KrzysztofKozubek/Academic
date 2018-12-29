#!/usr/bin/tcsh -X

set port = 4001
set server = 1
set client = 0
set ip = localhost
set file = "${HOME}/.licznik.rc"
set store = "${HOME}/.counter"
set counter = 1

onintr save
goto script

server_function:
    
    if ( `nc -z $ip $port >& /dev/null; echo $?` == 0 ) then
        echo "!!! Error: Something is using port $port. Aborting... !!!" >&2
        exit 1
    endif

    if ( `touch $store; echo $?` != 0 ) then
        echo "!!! Error: Could not create store file in $store" >&2
        exit 1
    endif

    set counter = `grep "^$port=[0-9]*" $store | grep -oP "$port=\K([0-9]*)"`
    if ( $counter == "" ) then
        set counter = 1
    endif

    echo "Server $0\: $ip $port - running" >&2
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
    goto save
    exit 0
    


client_function:
    eval "nc $ip $port"
    exit 0

script:
set help_msg = "\
NAME\
    `basename $0` - script works as echo server or client (depends on option)\
\
SYNOPSIS\
    `basename $0` [-options]\
\
DESCRIPTION\
    `basename $0` - scripts works as echo server or as client depending on option. As server (default) listens for incoming connections and return a number of successful connections\
    As client checks the specified port and returns a respond (if there's a respond) then exits\
\
OPTIONS\
    -h          show this message\
    -s          run script as echo server (default)\
    -c          run script as client\
    -p port     change port, more than 1024 (default: 40000)\
    -i ip       force to use specified ip (default: all local ips)\
    -f file     specify config file. File should contains NAME=VALUE in separate lines where NAME can only be port|file|ip (default: ${HOME}/.licznik.rc)\
    "

set p =
set f = 
set i = 
set s = 
set c = 

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
                echo "!!! Error: Can't invoke with options -s and -c !!!" 1>&2
                exit 1
            else
                set c = 1
            endif
            shift
            breaksw
        case -s: 
            if ( $c != "" ) then
                echo "!!! Error: Can't invoke with options -s and -c !!!" 1>&2
                exit 1
            else
                set s = 1
            endif
            shift
            breaksw
        case -f: 
            if ( $f != "" ) then
                echo "!!! Error: -f - File defined multiple times !!!" 1>&2
                exit 1
            else if ( ! -e "$2:q" ) then
                echo "!!! Error: File `basename $2` does not exist !!!" 1>&2
                exit 1
            else
                set f = "$2:q"
            endif
            shift
            shift
            breaksw
        case -p: 
            if ( $p != "" ) then
                echo "!!! Error: -p - Port defined multiple times !!!" 1>&2
                exit 1
            else if ! ( $2:q =~ [0-9][0-9]* ) then
                echo "!!! Error: Invalid port number !!!" 1>&2
                exit 1
            else if ( `nc -z ${ip:-"localhost"} $2:q; echo $?)` == 0 ) then
                echo "!!! Error: Port is in use $2:q !!!" 1>&2
                exit 1
            else if ( $2 < 1024 ) then
                echo "!!! Error: Reserved port. Use port > 1024 !!!" 1>&2
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
                    echo "!!! Error: Not a local IP (127.*) !!!" 1>&2
                    exit 1
                endif
            else
                echo "!!! Error: Invalid IP !!!" 1>&2
                exit 1
            endif
            shift 
            shift
            breaksw
        case --: 
            shift 
            break
        case *: 
            echo "Error opts $1 $2" >&2
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
            echo "!!! Error: Invalid port in config file ( < 1024 ) - $var !!!"
            exit 1
        else
            set port = "$REMATCH[1]"
        endif
    else if ( $var =~ ^file=(..*) ) then
        if ( -e "$REMATCH[1]" ) then
            set file = "$REMATCH[1]"
        else
            echo "!!! Error: Non existing file in config file - $var !!!"
            exit 1
        endif
    else if ( $var =~ ^ip=([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3}) ) then
        if ( "$REMATCH[1]" < 224 && "$REMATCH[2]" < 256 && "$REMATCH[3]" < 256 && "$REMATCH[4]" < 256 ) then
            set ip = "$REMATCH[1]"."$REMATCH[2]"."$REMATCH[3]"."$REMATCH[4]"
        else
            echo "!!! Error: Invalid ip in config file - $var !!!"
            exit 1
        endif
    else
        echo "!!! Error: Unknown option in config file - $var !!!"
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
    goto client_function
else
    goto server_function
endif

save:
    onintr
    echo "!!! Server terminated !!!" >&2
    grep -q "^$port=" $store && sed "s/^$port=.*/$port=$counter/" -i $store || echo "$port=$counter" >> $store
    pkill -P "$$"
    kill "$$"
