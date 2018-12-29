#!/usr/bin/python
#Krzysztof Kozubek

import sys

if len(sys.argv) < 2:
    print "Złe składnia. Sposób użycia " + sys.argv[0] + " liczba_całkowita"
    exit(0)

step = 1

try:
    if int(sys.argv[1]) < 0:
        step = -1
except ValueError:
    print "Agr musi być liczba całkowita"
    exit(0)


for row in range(1, 11):
    print " | ".join(("{:4}".format(row * col) for col in range(1, int(sys.argv[1]) + step, step)))


