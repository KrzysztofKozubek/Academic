#!/usr/bin/perl
# Pawel Imiolek

use strict;

if (scalar @ARGV < 4) {
    print STDERR "Missing arguments. Use eg. separator from to file1 file2\n";
    exit 1;
}

my $separator = shift;

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
    $_ =~ s/^\s+|\s+$//g;
    my @words = split(/$separator/, $_);
    print join(" ", @words[$from-1..$to-1]), "\n";
}
