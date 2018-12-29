#!/usr/bin/tcsh -f

set portList = `echo $3 | tr ',' ' '`

if ( `helper/compare_ips.csh $1 $2` == 0 ) then
    set ip1 = `helper/ip_to_num.csh $1`
    set ip2 = `helper/ip_to_num.csh $2`
else
    set ip1 = `helper/ip_to_num.csh $2`
    set ip2 = `helper/ip_to_num.csh $1`
endif

set number = $ip1
while ( $number < $ip2 ) 
    set ip = `helper/num_to_ip.csh $number`
    ping -c 1 -W 3 $ip >& /dev/null
    if ( $? != 0 ) then
        echo "${ip}: - dead" >& /dev/stdout
        @ number = ($number + 1)
        continue
    endif

    set output = "${ip}: "

    foreach port ($portList)
        set netcat = `echo -e 'HEAD / HTTP/1.0\n\n' | nc -vtnbw1 $ip $port >& /dev/stdout`
        set open = "$?"
        set role = `echo $netcat | cut -f 4 -d ' ' | sed -r 's/[()]//g'`
        set server = `echo $netcat | grep -o "<address>\S*" | sed -r 's/<address>//'`
        
        if ( "$server" == "" ) then
            set server = `echo "$netcat" | sed -n '2p'`
        endif

        if ( "$server" == "" ) then
            set server = "open"
        endif

        if ( "$open" == "0" ) then
            if ( "$role" == "?" ) then
                set output = "$output $port - $server"
            else
                set output = "$output $role - $server"
            endif
        else
            if ( "$role" == "?" ) then
                set output = "$output $port - closed"
            else
                set output = "$output $role - closed"
            endif
        endif
    end
    echo "$output" >& /dev/stdout
    @ number = ($number + 1)
end
