#!/usr/bin/perl
# Pawel Imiolek

use strict;

if (scalar @ARGV < 4) {
    print STDERR "Missing arguments. Use eg. $0 separator from to file1 file2 ...\n";
    exit 1;
}

my $separator = shift;

my $from = shift;
if (not $from =~ /\d+/ or $from < 1) {
    print STDERR "Invalid second argument. Should be positive integer\n";
    exit 1;
}
my $to = shift;
if (not $to =~ /\d+/ or $to < 1) {
    print STDERR "Invalid third argument. Should be positive integer\n";
    exit 1;
}
print "Separator: '$separator'\n";
while(<>) {
    $_ =~ s/^\s+|\s+$//g;
    my @words = split($separator, $_);
    if($words[$from-1] or $words[$to-1]) {
        print "$from: " . ($words[$from-1] || "''") . ", $to: " . ($words[$to-1] || "''") . "\n";
    } else {
        print STDERR "Error $ARGV: No words found in line $.\n";
    }

    $. = 0 if eof;
}
