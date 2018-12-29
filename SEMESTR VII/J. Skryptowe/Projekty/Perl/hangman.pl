#!/usr/bin/perl
#Krzysztof Kozubek

use Getopt::Std;

my %options = ();
getopts("hwnd", \%options);
$help = 1 if defined $options{h};
$word = 1 if defined $options{w};
$long = 1 if defined $options{n};
$lang = 1 if defined $options{d};
$lal = 1 if defined $options{n} or defined $options{d};

if($help){
    print "HELP\n";
    print "Gra wisielec: \n\n";
    print "Opcje: \n";
    print " -h                 help \n";
    print " -n [number]        set long word\n";
    print " -d [path to file]  change dictionary to polish\n";
    print " -w [word to guess] word \n\n";
    print " without arguments. Start game with default words\n\n";

    print "The process of losing\n";
    print "_______________ _______________ _______________ _______________ _______________\n|/     |     \\| |/     |     \\| |/     |     \\| |/     |     \\| |/     |     \\|\n|             | |      0      | |      0      | |      0      | |      0      |\n|             | |     /|\\     | |     /|\\     | |     /|\\     | |     /|\\     |\n|             | |     /|      | |     /|      | |     /|      | |     /|      |\n|-------------| |-------------| |-------------| |-------------| |----|   |----|\n|             | |             | |             | |             | |    -----    |\n";
    
    exit;
}

system("clear");
$path_to_file = "/usr/share/dict/words";
if($lang or $lal){
    $path_to_file = "/usr/share/dict/polish";
    $path_to_file = "/usr/share/dict/words" if not -e $path_to_file;
}else{
    $path_to_file = "/usr/share/dict/words";
}
open my $handle, '<', $path_to_file;
chomp(my @words = <$handle>);
close $handle;

# @words=qw( konstantynopolitanczykowianeczka fluorochlorowęglanowodoroazotanocydy aspiryna acetylosalicylowy antysemityzm prestidigitator personifikacja onomatopeja konstantynopolitanczycy rozentuzjazmowany wyidealizowany zniwiarkomlocarnia tyranozaurus);
@guesses=();
$wrong=0;

@shangman=( "_______________\n|/     |     \\|\n|             |\n|             |\n|             |\n|             |\n|_____________|\n|             |\n",
            "_______________\n|/     |     \\|\n|      0      |\n|             |\n|             |\n|             |\n|_____________|\n|             |\n",
            "_______________\n|/     |     \\|\n|      0      |\n|      |      |\n|             |\n|             |\n|_____________|\n|             |\n",
            "_______________\n|/     |     \\|\n|      0      |\n|     /|\\     |\n|             |\n|             |\n|_____________|\n|             |\n",
            "_______________\n|/     |     \\|\n|      0      |\n|     /|\\     |\n|      |\\     |\n|-------------|\n|             |\n",
            "_______________\n|/     |     \\|\n|      0      |\n|     /|\\     |\n|     /|      |\n|----|   |----|\n|    -----    |\n",);


$hangman="123456";

if($word){
    $choice = shift;
}else{
    $choice=$words[rand @words];
}
if($long or $lal){
    $n = shift;
    $choice=$words[rand @words];
    while(length($choice) < $n){ 
        $choice=$words[rand @words]; 
    }
    $choice = lc $choice;
}
@letters=split(//, $choice);
@hangman=split(//, $hangman);
@blankword=(0) x scalar(@letters);

OUTER: 
while ($wrong<@hangman) {
        print "Hasło: ";
    foreach $i (0..$#letters) {
        if ($blankword[$i]) {
            print $blankword[$i];
        } else {
            print "-";
        }
    }

    print("\nPodałeś już litery: ");
    foreach(@guesses) {
        print " $_";
    }

    print "\n";
    if ($wrong) {
        print @shangman[$wrong-1];
    }
    print "\n Podaj literę: ";
    $guess=<STDIN>; chomp $guess;
    system("clear");
    foreach(@guesses) {
        next OUTER if ($_ eq $guess);
    }
    $guesses[@guesses]=$guess;
    $right=0;
    for ($i=0; $i<@letters; $i++) {
        if ($letters[$i] eq $guess) {
            $blankword[$i]=$guess;
            $right=1;
        }
    }
    $wrong++ if (not $right);
    if (join('', @blankword) eq $choice) {
        print "Super udało Ci się!\nPoprawnym słowem było: $choice \n";
        exit;
    }
}
print "$shangman[5]\nPrzegłałeś, poprawnym słowem było $choice.\n";