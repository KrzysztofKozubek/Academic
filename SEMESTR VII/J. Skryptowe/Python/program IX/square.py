#!/usr/bin/python
#Krzysztof Kozubek

import sys
import re
from math import sqrt

print "\nRozwiazywanie rownania kwadratowego\n"

a = None
b = None
c = None

for arg in sys.argv:
    match = re.search('^a=([-+]?\d+(\.\d*)?)$', arg)
    if match:
        a = float(match.group(1))
        continue
    match = re.search('^b=([-+]?\d+(\.\d*)?)$', arg)
    if match:
        b = float(match.group(1))
        continue
    match = re.search('^c=([-+]?\d+(\.\d*)?)$', arg)
    if match:
        c = float(match.group(1))
        continue

if a is None:
    while True:
        try:
            a = float(raw_input("Podaj wartosc a: "))
            break
        except ValueError:
            print "Podano niepoprawna wartosc a"
        except KeyboardInterrupt:
            exit()

if b is None:
    while True:
        try:
            b = float(raw_input("Podaj wartosc b: "))
            break
        except ValueError:
            print "Podano niepoprawna wartosc b"
        except KeyboardInterrupt:
            exit()


if c is None:
    while True:
        try:
            c = float(raw_input("Podaj wartosc c: "))
            break
        except ValueError:
            print "Podano niepoprawna wartosc c"
        except KeyboardInterrupt:
            exit(0)

if(a == 0 and b == 0):
    print "!- Podano rownanie bez niewiadomych -!\nWynik rownania %g=0 => Rownanie %s" % (c, "tozsamosciowe" if c == 0 else "sprzeczne")
elif (a == 0):
    print "!- Podano rownanie liniowe -!\nWynik rownania %gx%+g=0 => %g" % (b, c, -c / b)
else:
    print "Wynik rownania %gx^2%+gx%+g=0 => "% (a, b, c),
    delta = b * b - 4 * a * c
    if(delta > 0):
        print "x1 = %g, x2 = %g" % ((-b + sqrt(delta)) / (2 * a), (-b - sqrt(delta)) / (2 * a))
    elif(delta == 0):
        print "x1 = x2 = %g" % (-b / (2 * a))
    else:
        print "x1 = %s, x2 = %s" % (str(-b / (2 * a)) + " + %gi" % (sqrt(-delta) / (2 * a)), str(-b / (2 * a)) + " - %gi" % (sqrt(-delta) / (2 * a)))
print "\n",
