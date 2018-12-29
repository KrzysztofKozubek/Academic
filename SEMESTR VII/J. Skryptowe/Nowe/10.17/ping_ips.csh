#!/usr/bin/tcsh -f


if ( `./compare_ips.csh $1 $2` == 0 ) then
    set ip1 = `./ip_to_num.csh $1`
    set ip2 = `./ip_to_num.csh $2`
else
    set ip1 = `./ip_to_num.csh $2`
    set ip2 = `./ip_to_num.csh $1`
endif

set number = $ip1
while ( $number < $ip2 ) 
    set ip = `./num_to_ip.csh $number`
    ping -c 1 -W 3 $ip >& /dev/null
    if ( "$?" == "0" ) then
        echo "$ip - alive" >& /dev/stdout
    else
        echo "$ip - dead" >& /dev/stdout
    endif
    @ number = ($number + 1)
end
