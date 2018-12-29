#!/usr/bin/python
#Krzysztof Kozubek

import re
import argparse
import sys
from  os.path import basename

def init_parser():
    parser = argparse.ArgumentParser(description='')


def is_grade(grade):
    return True if re.match(r"([1-6]\.\d+|[-+]?[1-6]|[1-6][-+]?)", grade) else False

def get_grade(grade):
    match = re.match(r"([1-6](\.\d)?)", grade)
    if match:
        return float(match.group())
    match = re.match(r"(\+([1-6])|([1-6]\+)")
    if match:
        return float(match.group(2) or match.group(3)) + 0.25
    match = re.match(r"(\-([1-6])|([1-6]\-)")
    if match:
        return float(match.group(2) or match.group(3)) - 0.25

def run_register():
    register = {}
    for file_name in sys.argv[1:]:
        with open(file_name) as f:
            for number, line in enumerate(f, start=1):
                line = line.lower().split()
                if len(line) != 3 or not re.match(r"[a-zA-Z-]+", line[0]) or not re.match(r"[a-zA-Z-]+", line[1]) or not is_grade(line[2]):
                    print >> sys.stderr, "BLAD {}: NIEPRAWIDLOWE DANE W LINII {}".format(basename(file_name), number)
                    continue

                key = line[1].title() + " " + line[0].title()

                if key not in register:
                    register[key] = []

                register[key].append(get_grade(line[2]))

            else:
                print "File: " + basename(file_name)
                if len(register) == 0:
                    print "BLAD {}: PLIK NIE ZAWIERA POPRAWNYCH DANYCH".format(basename(file_name))
                    continue
                average = []
                for key in sorted(register.keys()):
                    values = register[key]
                    average.append(sum(values) / float(len(values)))
                    print "{}: {}: {:-.2f}".format(key, ', '.join(map(str, values)), average[-1])
                print "Średnia: {:-.2f}\n".format(sum(average) / float(len(average)))
                register = {}

if __name__ == "__main__":
    run_register()
