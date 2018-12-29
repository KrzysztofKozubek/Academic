#!/usr/bin/tcsh -f

echo $1 | grep -E "^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}"$



if ( $? == 0 ) then
    set ip = (`echo $1 | tr "." " "`)                                                        
    if ( $ip[1] < 224 && $ip[2] < 256 && $ip[3] < 256 && $ip[4] < 256 ) then
        echo 0
    else 
        echo 1
    endif 
else
    echo 1
endif                                                                          
