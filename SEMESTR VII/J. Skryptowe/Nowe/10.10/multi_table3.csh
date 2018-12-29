#!/usr/bin/tcsh
# Paweł Imiołek gr 1

set noglob

if ( $#argv < 3 ) then
    echo -e "\nError: Script requires three parameters!\n"
    exit 1
endif

foreach arg (`seq 1 3`)
    if ( `echo $argv[$arg] | egrep '^-?[0-9]+$'` ) then
        if ( ! $?arg1 ) then
            set arg1 = $argv[$arg]
        else if ( ! $?arg2 ) then
            set arg2 = $argv[$arg]
        else
            echo "\nError: No sign passed!\n"
            exit 2
        endif
    else if ( `echo $argv[$arg] | egrep '[-+*/%^]'` != "") then
        if ( ! $?sign ) then
            set sign = $argv[$arg]
        else
            echo "\nRequire two integers!\n"
            exit 1
        endif
    else
        echo "\nError: Invalid sign! - $argv[$arg]"
        echo "For multiply, please use \* or '*'\n"
        exit 2
    endif
end
#echo "$arg1 $sign $arg2"

set ARRAY = ()
if ( $arg1 < $arg2) then
    set step = 1
    set min = $arg1
else
    set step = -1
    set min = $arg2
endif
foreach i (`seq $arg1 $step $arg2`)
    set len = `echo "$i $sign 9" | bc`
    set ARRAY = ( $len $ARRAY)
endif

foreach x (`seq 1 9`) 
    foreach y (`seq $arg1 $step $arg2`)
        @ index = ($y - $min)
#        if ( $x == 9 ) then
#            printf "| %d %s %d = %`echo $ARRAY[$index] | awk '{print length()}'`d " "$y" "$sign" "$x" $ARRAY[$index] 
#        else
            printf "| %d %s %d = %d " "$y" "$sign" "$x" `echo "$y $sign $x" | bc`
        endif
    end
    printf "|\n"
end
