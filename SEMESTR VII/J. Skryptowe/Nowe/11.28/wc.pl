#!/usr/bin/perl
#
use strict;
use warnings;

use File::Basename qw(dirname);
use lib dirname $0;
use wc;

wc::get_options(@ARGV);

while(<>) {
    if($wc::characters) {
        my $number = wc::count_characters $_;
        $wc::file_characters += $number;
        $wc::all_characters += $number;
    }
    
    $_ =~ s/^\s+|\s+$//g;
    if($wc::lines) {
        $wc::file_lines++;
        $wc::all_lines++;
    }
    
    next if /^\s*$/;
    $.-- and next if $wc::ignore_hash and /^#/;
  
    my @words = split /\s+/;

    if($wc::words) {
        my $number = scalar @words;
        $wc::file_words += $number;
        $wc::all_words += $number;
    }

    if($wc::numbers or $wc::integers) {
        for my $word (@words) {
            ++$wc::all_numbers and ++$wc::file_numbers if $wc::numbers and wc::is_number $word;
            ++$wc::all_integers and ++$wc::file_integers if $wc::integers and wc::is_integer $word;
        }
    }

    if(eof) {
        my $string;
        $string .= sprintf "%5d ", $wc::file_lines if $wc::lines;
        $string .= sprintf "%5d ", $wc::file_words if $wc::words;
        $string .= sprintf "%5d ", $wc::file_characters if $wc::characters;
        $string .= sprintf "%5d ", $wc::file_numbers if $wc::numbers;
        $string .= sprintf "%5d ", $wc::file_integers if $wc::integers;
        $string .= "$ARGV\n";
        print $string;
        wc::reset_file_counters;
    }
}

my $string;
$string .= sprintf "%5d ", $wc::all_lines if $wc::lines;
$string .= sprintf "%5d ", $wc::all_words if $wc::words;
$string .= sprintf "%5d ", $wc::all_characters if $wc::characters;
$string .= sprintf "%5d ", $wc::all_numbers if $wc::numbers;
$string .= sprintf "%5d ", $wc::all_integers if $wc::integers;
$string .= "TOTAL\n";
print $string;
