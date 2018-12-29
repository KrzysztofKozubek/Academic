#!/usr/bin/python

from os.path import basename
import argparse 
import re
import sys

counters = {
        'file_lines' : 0,
        'all_lines' : 0,
        'file_words' : 0,
        'all_words' : 0,
        'file_characters' : 0,
        'all_characters' : 0,
        'file_numbers' : 0,
        'all_numbers' : 0,
        'file_integers' : 0,
        'all_integers' : 0
        }

def get_options(options):
    parser = argparse.ArgumentParser()
    parser.add_argument('-l', action='store_true', dest='lines', help="count lines")
    parser.add_argument('-w', action='store_true', dest='words', help="count words")
    parser.add_argument('-c', action='store_true', dest='characters', help="count characters")
    parser.add_argument('-i', action='store_true', dest='integers', help="count integers")
    parser.add_argument('-d', action='store_true', dest='numbers', help="count numbers")
    parser.add_argument('-e', action='store_true', dest='show_hash', help="show lines starting with #")
    parser.add_argument('file', action='store', nargs='+', help='files to check')
    args = parser.parse_args(options)
    if not (args.lines or args.words or args.characters or args.integers or args.numbers or args.show_hash):
        args.lines = True
        args.words = True
        args.characters = True
    return args

def count_characters(line):
    return len(line)

def is_number(number):
    return True if re.match("^[-+]?\d+", number) else False

def is_integer(number):
    return True if re.match("^[-+]?\d*\.?\d+([eEdDqQ^][-+]?\d+)?", number) else False

def reset_file_counters():
    global counters
    for key in counters:
        if key.startswith('file'):
            counters[key] = 0

def run_wc():
    global counters
    parser = get_options(sys.argv[1:])
    for file_name in parser.file:
        try:
            with open(file_name) as f:
                for line in f:
                    if parser.show_hash and line.strip().startswith('#'):
                        continue

                    if parser.characters:
                        chars = count_characters(line)
                        counters['file_characters'] += chars
                        counters['all_characters'] += chars
                    
                    line = line.strip()

                    if parser.lines:
                        counters['file_lines'] += 1
                        counters['all_lines'] += 1

                    if line == '':
                        continue

                    words = line.split()

                    if parser.words:
                        number = len(words)
                        counters['file_words'] += number
                        counters['all_words'] += number

                    if parser.numbers or parser.integers:
                        for word in words:
                            if parser.numbers and is_number(word):
                                counters['all_numbers'] += 1
                                counters['file_numbers'] += 1
                            if parser.integers and is_integer(word):
                                counters['all_integers'] += 1
                                counters['file_integers'] += 1
                
                else:
                    string = ''
                    if parser.lines: string += "%5d " % counters['file_lines']
                    if parser.words: string += "%5d " % counters['file_words']
                    if parser.characters: string += "%5d " % counters['file_characters']
                    if parser.numbers: string += "%5d " % counters['file_numbers']
                    if parser.integers: string += "%5d " % counters['file_integers']
                    print string + basename(file_name)
                    reset_file_counters()

        except Exception as e:
            print e
    else:
        string = ''
        if parser.lines: string += "%5d " % counters['all_lines']
        if parser.words: string += "%5d " % counters['all_words']
        if parser.characters: string += "%5d " % counters['all_characters']
        if parser.numbers: string += "%5d " % counters['all_numbers']
        if parser.integers: string += "%5d " % counters['all_integers']
        print string + "TOTAL"

if __name__ == '__main__':
    run_wc()
