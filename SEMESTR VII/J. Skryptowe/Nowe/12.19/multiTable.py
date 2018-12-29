#!/usr/bin/python
#

import sys

if len(sys.argv) < 2:
    print "Missing argument. Use " + sys.argv[0] + " number"
    exit(0)

step = 1

try:
    if int(sys.argv[1]) < 0:
        step = -1
except ValueError:
    print "Argument is not integer"
    exit(0)


for row in range(1, 11):
    print " | ".join(("{:4}".format(row * col) for col in range(1, int(sys.argv[1]) + step, step)))


