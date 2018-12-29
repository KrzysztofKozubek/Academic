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

while(<>) {
    my @words = split(/\s+/, $_);
    if($words[$from-1] or $words[$to-1]) {
        print "$from: $words[$from-1] $to: $words[$to-1]\n";
    } else {
        print STDERR "$ARGV: Nie znaleziono slow w linii $.\n";
    }

    $. = 0 if eof;
}
