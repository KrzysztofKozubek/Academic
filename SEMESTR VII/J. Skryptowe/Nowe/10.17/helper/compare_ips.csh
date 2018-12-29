#!/usr/bin/tcsh -f

set ip1 = (`echo $1 | tr "." " "`)
set ip2 = (`echo $2 | tr "." " "`)

set change = 0

foreach i (`seq 1 4`)
    if ( $ip1[$i] > $ip2[$i] ) then
        set change = 1
        break
    endif
end

if ( $change == 1 ) then
    echo 1
else 
    echo 0
endif
