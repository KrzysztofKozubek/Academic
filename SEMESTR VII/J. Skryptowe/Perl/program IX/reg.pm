#!/usr/bin/perl
#Krzysztof Kozubek

package reg;
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
our $fn = 0;
our $fInt = 0;

our $allLines = 0;
our $allWords = 0;
our $allChars = 0;
our $alln = 0;
our $allInt = 0;

our $ignHash = 0;

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
    $ignHash = 1 if $options{e};
} 

sub couChars {
    return length shift;
}

sub checkNumber {
    return 1 if shift =~ /^[-+]?\d*\.?\d+([eEdDqD^][-+]?\d+)?$/;
}

sub is_integer {
    return 1 if shift =~ /^[-+]?\d+$/;
}

sub reset_file_counters {
    $fLines = 0;
    $fWords = 0;
    $fChars = 0;
    $fn = 0;
    $fInt = 0;
}

sub checkGrade {
    my $grade = shift;
    if($grade =~ /^([1-6]\.[1-6]+|[-+]?[1-6]|[1-6][-+]?)$/) {
        return 1;
    }
    return 0;
}

sub grade {
    my $grade = shift;
    if ($grade =~ /^([1-6](\.\d)?)$/) {
        return $1;
    }
    elsif ($grade =~ /^(\+([1-6])|([1-6])\+)$/) {
        return (($2 || $3) + 0.25);
    }
    elsif ($grade =~ /^(\-([1-6])|([1-6])\-)$/) {
        return (($2 || $3) - 0.25);
    }
}

1;
