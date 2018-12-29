#!/usr/bin/python

import sys

if len(sys.argv) < 2:
    print "Brak arg, uzyj " + sys.argv[0] + " numeru"
    exit(0)

step = 1

try:
    if int(sys.argv[1]) < 0:
        step = -1
except ValueError:
    print "Arg nie jest liczba calkowita"
    exit(0)


for row in range(1, 11):
    print " | ".join(("{:4}".format(row * col) for col in range(1, int(sys.argv[1]) + step, step)))


