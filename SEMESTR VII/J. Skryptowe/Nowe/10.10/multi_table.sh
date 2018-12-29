#!/bin/bash
# Paweł Imiołek gr 1

for x in {1..9}; do 
    for y in {1..9}; do
        printf "| %d * %d = %2d " $y $x $((x * y))
    done
    printf "|\n"
done
