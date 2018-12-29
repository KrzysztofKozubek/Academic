#!/usr/bin/perl
#Krzysztof Kozubek

use strict;
use warnings;

use File::Basename qw(dirname);
use lib dirname $0;
use kk;

kk::options(@ARGV);

while(<>) {
    if($kk::characters) {
        my $n = kk::cChars $_;
        $kk::fChars += $n;
        $kk::allChars += $n;
    }
    
    $_ =~ s/^\s+|\s+$//g;
    if($kk::lines) {
        $kk::fLines++;
        $kk::allLines++;
    }
    
    next if /^\s*$/;
    $.-- and next if $kk::igHash and /^#/;
  
    my @words = split /\s+/;

    if($kk::words) {
        my $n = scalar @words;
        $kk::fWords += $n;
        $kk::allWords += $n;
    }

    if($kk::ns or $kk::integers) {
        for my $word (@words) {
            ++$kk::allNs and ++$kk::fNs if $kk::ns and kk::isN $word;
            ++$kk::allInt and ++$kk::fInt if $kk::integers and kk::checkInt $word;
        }
    }

    if(eof) {
        my $chars;
        $chars .= sprintf "%5d ", $kk::fLines if $kk::lines;
        $chars .= sprintf "%5d ", $kk::fWords if $kk::words;
        $chars .= sprintf "%5d ", $kk::fChars if $kk::characters;
        $chars .= sprintf "%5d ", $kk::fNs if $kk::ns;
        $chars .= sprintf "%5d ", $kk::fInt if $kk::integers;
        $chars .= "$ARGV\n";
        print $chars;
        kk::RFC;
    }
}

my $chars;
$chars .= sprintf "%5d ", $kk::allLines if $kk::lines;
$chars .= sprintf "%5d ", $kk::allWords if $kk::words;
$chars .= sprintf "%5d ", $kk::allChars if $kk::characters;
$chars .= sprintf "%5d ", $kk::allNs if $kk::ns;
$chars .= sprintf "%5d ", $kk::allInt if $kk::integers;
$chars .= "TOTAL\n";
print $chars;