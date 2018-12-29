#!/bin/bash
# Paweł Imiołek gr 1

if [ $# -lt 3 ]; then
    echo -e "\nError: Script requires three parameters!\n"
    exit 1
fi

for arg in {1..3}; do
    echo ${!arg}
    if [[ ${!arg} =~ ^-?[0-9]+$ ]]; then
        if [[ ! "$arg1" ]]; then
            arg1=${!arg}
        elif [[ ! "$arg2" ]]; then
            arg2=${!arg}
        else
            echo -e "\nError: No sign passed!\n"
            exit 2
        fi
    elif [[ ${!arg} =~ [-+*/%^] ]]; then
        if [[ ! "$sign" ]]; then
            sign=${!arg}
        else
            echo -e "\nRequire two integers!\n"
            exit 1
        fi
    else
        echo -e "\nError: Invalid sign! - ${!arg}"
        echo -e "For multiply, please use \* or '*'\n"
        exit 2
    fi
done
echo "$arg1 $sign $arg2"
ARRAY=()
sum=$((arg1-arg2))
min=$((arg1 < arg2 ? arg1 : arg2))
for i in $(eval echo {$arg1..$arg2}); do
    len=`echo "$i $sign 9" | bc`
    echo $len
    ARRAY[$((i - min))]=$len
done

for x in {1..9}; do
    for y in $(eval echo {$arg1..$arg2}); do
        if [ $x -eq 9 ]; then
            printf "| %${#y}d %s %d = %${#ARRAY[y - min]}d " "$y" "$sign" "$x" "${ARRAY[y - min]}" 
        else
            printf "| %${#y}d %s %d = %${#ARRAY[y - min]}d " "$y" "$sign" "$x" `echo "$y $sign $x" | bc`
        fi
    done
    printf "|\n"
done
