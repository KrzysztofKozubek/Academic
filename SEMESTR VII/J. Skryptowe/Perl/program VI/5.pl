#!/usr/bin/perl
#Krzysztof Kozubek

use strict;
use warnings;
use Getopt::Std;

if ( not scalar @ARGV ) { 
    print "Zla skladnia.\n";
    exit 0; 
}

my %options = ();
getopts("hcnp", \%options);

my $hash = 0;
my $nLine = 0;
my $newFile = 0;

$hash = 1 if defined $options{h} or defined $options{n};
$nLine = 1 if defined $options{c} or defined $options{n};
$newFile = 1 if defined $options{p};

while(<>) {
    if ($nLine and $hash) {
        if (/^[^#]/) {
            print "$.: $_";
        } else {
            $.--;
        }
    }
    elsif ($nLine) {
        print "$.: $_";
    }
    elsif ($hash) {
        print "$_" if not /^#/;
    }
    else {
        print $_;
    }
    
    if ($newFile and eof) {
        $. = 0;
    }
}

