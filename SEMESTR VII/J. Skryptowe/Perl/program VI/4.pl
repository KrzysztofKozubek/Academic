#!/usr/bin/perl
#Krzysztof Kozubek

#napisz zagniezdzone array
#switch -c przelacza podawanie linii
#-h nie podaje linii z #
#-n podaje liniie

#my $line = 1;
#while(<>){if(!/^#/){print $line; print; $line++;}}
while(<>){if(!/^#/){print $.,$_}else{$.--;}}