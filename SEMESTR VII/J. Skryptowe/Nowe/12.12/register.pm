#!/usr/bin/perl
#

package register;
use strict;
use warnings;
use Getopt::Std;

our $lines = 0;
our $words = 0;
our $characters = 0;
our $numbers = 0;
our $integers = 0;

our $file_lines = 0;
our $file_words = 0;
our $file_characters = 0;
our $file_numbers = 0;
our $file_integers = 0;

our $all_lines = 0;
our $all_words = 0;
our $all_characters = 0;
our $all_numbers = 0;
our $all_integers = 0;

our $ignore_hash = 0;

sub get_options {
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
    $ignore_hash = 1 if $options{e};
} 

sub count_characters {
    return length shift;
}

sub is_number {
    return 1 if shift =~ /^[-+]?\d*\.?\d+([eEdDqD^][-+]?\d+)?$/;
}

sub is_integer {
    return 1 if shift =~ /^[-+]?\d+$/;
}

sub reset_file_counters {
    $file_lines = 0;
    $file_words = 0;
    $file_characters = 0;
    $file_numbers = 0;
    $file_integers = 0;
}

sub is_grade {
    my $grade = shift;
    if($grade =~ /^([1-6]\.[1-6]+|[-+]?[1-6]|[1-6][-+]?)$/) {
        return 1;
    }
    return 0;
}

sub get_grade {
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
