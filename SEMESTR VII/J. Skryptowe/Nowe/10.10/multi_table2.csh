#!/usr/bin/tcsh
# Paweł Imiołek gr 1

if ( $#argv < 2 ) then
    echo "\nError: Script requires two parameters!\n"
    exit 1
else if ( `echo $1 | egrep '^-?[0-9]+$'` == "" || `echo $2 | egrep '^-?[0-9]+$'` == "" ) then
    echo "\nError: Script requires two integers!\n"
    exit 2
endif

if ( $1 < $2 ) then
    set step = 1
else 
    set step = -1
endif

foreach x  (`seq 1 9`)
    foreach y (`seq $1 $step $2`)
        @ mul = ($x * $y)
        @ len = `echo $y | awk '{print length()}'` + 1
        printf "| %d * %d = %${len}d " "$y" "$x" "$mul"
    end
    printf "|\n"
end
