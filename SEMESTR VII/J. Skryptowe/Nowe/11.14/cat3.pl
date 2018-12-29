#!/usr/bin/perl
# 

if ( not scalar @ARGV ) { 
    exit 0; 
}

while(<>) { 
    print "$.: $_" if not /^#/;
}
