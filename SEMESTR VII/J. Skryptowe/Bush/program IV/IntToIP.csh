#!/usr/bin/tcsh -f

set number = $1 
set ip = ""
set tmp = 0

@ tmp = ($number >> 24)
set ip = "$tmp."
@ number = ($number - ($tmp << 24))
@ tmp = ($number >> 16)
set ip = "$ip$tmp."
@ number = ($number - ($tmp << 16))
@ tmp = ($number >> 8)
set ip = "$ip$tmp."
@ number = ($number - ($tmp << 8))
set ip = "$ip$number"

echo $ip

