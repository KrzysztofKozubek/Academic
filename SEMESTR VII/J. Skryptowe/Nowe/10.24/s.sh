#!/bin/bash

numer_ip="spk-ssh.if.uj.edu.pl"
port="78-82"

netcat=$(echo "exit" | nc -v -w1 $numer_ip $port 2>&1)

readarray -t lista < <(echo "$netcat")

test=$(echo ${lista[0]} | grep "$numer_ip" | rev | cut -d' ' -f 1 | rev)

echo $test
