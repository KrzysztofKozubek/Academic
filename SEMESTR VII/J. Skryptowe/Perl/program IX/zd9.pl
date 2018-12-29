#!/usr/bin/perl
#Krzysztof Kozubek

use strict;
use warnings;

use List::Util qw(sum);
use File::Basename qw(dirname);
use lib dirname $0;

use reg;

if(not scalar @ARGV) {
    print STDERR "Brak pliku";
    exit 1;
}

my %reg = ();

while(<>) {
    my @line = split /\s+/, lc;

    if(scalar @line != 3) {
        print STDERR "$ARGV: Niepoprawne dane w linii $.\n";
        next;
    }

    if($line[0] !~ /[a-zA-Z-]+/ or $line[1] !~ /[a-zA-Z-]+/ or not reg::checkGrade $line[2]) {
        print STDERR "$ARGV: Niepoprawne dane w linii $.\n";
        next;
    }

    my $key = ucfirst $line[1] . " " . ucfirst $line[0];

    if(not defined $reg{$key}) {
        $reg{$key} = ();
    }

    push @{$reg{$key}}, reg::grade $line[2];

    if (eof) {
        print "Plik: $ARGV\n";
        my @values;
        my @average = ();
        for (sort keys %reg) {
            @values = @{ $reg{$_} };
            push @average, sum(@values) / @values;
            printf "%s: %s: %.2f\n", $_, join(', ', @values), $average[-1]; 
        }
        printf "Średnia: %.2f\n", sum(@average) / @average;
        %reg = ();
    }
}