#!/usr/bin/tcsh -f

set ips = (`echo $1 | tr '.' ' '`) 
set number = 0 

@ number = ($number + ($ips[1] << 24))
@ number = ($number + ($ips[2] << 16))
@ number = ($number + ($ips[3] << 8))
@ number = ($number + $ips[4])
echo $number

