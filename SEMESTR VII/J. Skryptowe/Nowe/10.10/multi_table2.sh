#!/bin/bash
# Paweł Imiołek gr 1

if [ $# -lt 2 ]; then
    echo "Error: Script requires two parameters!"
    exit 1
fi

if ! [[ $1 =~ ^-?[0-9]+$ ]] || ! [[ $2 =~ ^-?[0-9]+$ ]] ; then
    echo "Error: Script requires two integers!"
    exit 2
fi

for x in {1..9}; do 
    for y in $(eval echo {$1..$2}); do
        len=${#y}
        printf "| %d * %d = %$((len + 1))d " "$y" "$x" "$((x * y))"
    done
    printf "|\n"
done
