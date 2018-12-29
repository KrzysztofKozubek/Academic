#!/usr/bin/perl
#Krzysztof Kozubek

use strict;

if (scalar @ARGV < 2) {
    print STDERR "Zla skladnia. Opcje od do plik\n";
    exit 1;
}

my $from = shift;
if (not $from =~ /\d+/ or $from < 1) {
    print STDERR "Niepoprawny 1 arg.\n";
    exit 1;
}
my $to = shift;
if (not $to =~ /\d+/ or $to < 1) {
    print STDERR "Niepoprawny 2 arg.\n";
    exit 1;
}

($from, $to) = ($to, $from) if $to < $from;

while(<>) {
    my @words = split(/\s+/, $_);
    print join(" ", @words[$from-1..$to-1]), "\n";
}
