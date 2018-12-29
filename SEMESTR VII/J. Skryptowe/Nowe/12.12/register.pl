#!/usr/bin/perl
#

use strict;
use warnings;

use List::Util qw(sum);
use File::Basename qw(dirname);
use lib dirname $0;

use register;

if(not scalar @ARGV) {
    print STDERR "MIssing files";
    exit 1;
}

my %register = ();

while(<>) {
    my @line = split /\s+/, lc;

    if(scalar @line != 3) {
        print STDERR "Error $ARGV: Invalid data in line $.\n";
        next;
    }

    if($line[0] !~ /[a-zA-Z-]+/ or $line[1] !~ /[a-zA-Z-]+/ or not register::is_grade $line[2]) {
        print STDERR "Error $ARGV: Invalid data in line $.\n";
        next;
    }

    my $key = ucfirst $line[1] . " " . ucfirst $line[0];

    if(not defined $register{$key}) {
        $register{$key} = ();
    }

    push @{$register{$key}}, register::get_grade $line[2];

    if (eof) {
        print "File: $ARGV\n";
        my @values;
        my @average = ();
        for (sort keys %register) {
            @values = @{ $register{$_} };
            push @average, sum(@values) / @values;
            printf "%s: %s: %.2f\n", $_, join(', ', @values), $average[-1]; 
        }
        printf "Average: %.2f\n", sum(@average) / @average;
        %register = ();
    }
}
