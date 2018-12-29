#!/usr/bin/perl
#

use strict;
use warnings;
use Getopt::Std;

if ( not scalar @ARGV ) { 
    print "Missing arguments. Use [-hcnp] file1 file2 ...\n";
    exit 0; 
}

my %options = ();
getopts("hcnp", \%options);

my $ignore_hash = 0;
my $line_numbers = 0;
my $separate_files = 0;

$ignore_hash = 1 if defined $options{h} or defined $options{n};
$line_numbers = 1 if defined $options{c} or defined $options{n};
$separate_files = 1 if defined $options{p};

while(<>) {
    if ($line_numbers and $ignore_hash) {
        if (/^[^#]/) {
            print "$.: $_";
        } else {
            $.--;
        }
    }
    elsif ($line_numbers) {
        print "$.: $_";
    }
    elsif ($ignore_hash) {
        print "$_" if not /^#/;
    }
    else {
        print $_;
    }
    
    if ($separate_files and eof) {
        $. = 0;
    }
}
