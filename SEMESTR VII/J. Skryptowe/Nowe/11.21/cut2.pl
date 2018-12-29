#!/usr/bin/perl
# Pawel Imiolek

use strict;

if (scalar @ARGV < 2) {
    print STDERR "Missing arguments. Use eg. from to file1 file2\n";
    exit 1;
}

my $from = shift;
if (not $from =~ /\d+/ or $from < 1) {
    print STDERR "Invalid first argument. Should be positive integer\n";
    exit 1;
}
my $to = shift;
if (not $to =~ /\d+/ or $to < 1) {
    print STDERR "Invalid second argument. Should be positive integer\n";
    exit 1;
}

while(<>) {
    my @words = split(/\s+/, $_);
    if($words[$from-1] or $words[$to-1]) {
        print "$from: $words[$from-1] $to: $words[$to-1]\n";
    } else {
        print STDERR "Error $ARGV: No words found in line $.\n";
    }

    $. = 0 if eof;
}
