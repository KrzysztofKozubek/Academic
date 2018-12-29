#!/usr/bin/tcsh
# Paweł Imiołek gr 1

foreach x ( `seq 1 9` )
    foreach y ( `seq 1 9` )
        @ expr= $x * $y
        printf '| %d * %d = %2d ' "$y" "$x" "$expr"
    end
    printf "|\n"
end
