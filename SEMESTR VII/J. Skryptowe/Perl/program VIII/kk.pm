#!/usr/bin/perl
#Krzysztof Kozubek

package kk;
use strict;
use warnings;
use Getopt::Std;

our $lines = 0;
our $words = 0;
our $characters = 0;
our $numbers = 0;
our $integers = 0;

our $fLines = 0;
our $fWords = 0;
our $fChars = 0;
our $file_numbers = 0;
our $fInt = 0;

our $allLines = 0;
our $allWords = 0;
our $allChars = 0;
our $all_numbers = 0;
our $allInt = 0;

our $igHash = 0;

sub cChars {
    return length shift;
}

sub checkInt {
    return 1 if shift =~ /^[-+]?\d+$/;
}

sub isNumber {
    return 1 if shift =~ /^[-+]?\d*\.?\d+([eEdDqD^][-+]?\d+)?$/;
}

sub RFC {
    $fLines = 0;
    $fWords = 0;
    $fChars = 0;
    $file_numbers = 0;
    $fInt = 0;
}

sub options {
    my %options = ();
    getopts("lwcide", \%options);

    if(not values %options) {
        $lines = 1;
        $words = 1;
        $characters = 1;
        return;
    };

    $lines = 1 if $options{l};
    $words = 1 if $options{w};
    $characters = 1 if $options{c};
    $numbers = 1 if $options{d};
    $integers = 1 if $options{i};
    $igHash = 1 if $options{e};
} 
1;