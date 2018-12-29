#!/usr/bin/perl
# Pawel Imiolek

use strict;

if (scalar @ARGV < 3) {
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

($from, $to) = ($to, $from) if $to < $from;

while(<>) {
    my @words = split(/\s+/, $_);
    print join(" ", @words[$from-1..$to-1]), "\n";
}
