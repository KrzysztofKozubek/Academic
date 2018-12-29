#!/usr/bin/python
#Krzysztof Kozubek

import sys
import argparse
import mimetypes

def is_text_file(file_name):
    file_type = mimetypes.guess_type(file_name)[0]
    if file_type and file_type.startswith('text'):
        return True

def init_parser():
    parser = argparse.ArgumentParser(description='skrypt czyta zawartość pliku i wypisuja', usage='%(prog)s [opcje] pliki')
    group = parser.add_mutually_exclusive_group()
    group.add_argument('-n',    action='store_true', dest='numbers',        help='numeracja linii dla kazdego pliku osobno')
    group.add_argument('-N',    action='store_true', dest='all_numbers',    help='numeracja linii dla wszystkich plikow')
    parser.add_argument('-v',   action='store_true', dest='show_hash',      help='pokazuje linie z #')
    parser.add_argument('-f',   action='store_true', dest='force',          help='wykrywanie plikow nie tekstowych i mozliwosc ich przeczytania')
    parser.add_argument('file', nargs='+', help='sciezka do pliku')
    return parser.parse_args(sys.argv[1:])

if __name__ == '__main__':
    parser = init_parser()
    counter = 0
    for file_name in parser.file:
        try:
            if not is_text_file(file_name) and not parser.force:
                continue
            with open(file_name) as f:
                for line in f:
                    counter += 1
                    if line.strip().startswith('#') and not parser.show_hash:
                        continue
                    if parser.numbers or parser.all_numbers:
                        print counter, line,
                    else:
                        print line,
                else:
                    print 
                    if not parser.all_numbers:
                        counter = 0
        except Exception, e:
            print e


