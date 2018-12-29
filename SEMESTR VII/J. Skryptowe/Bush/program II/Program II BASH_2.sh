#!/bin/bash
#Krzysztof Kozubek gr.1

#Wartosci bledow
ERROR_FIRST_ARG=1;
ERROR_SECOND_ARG=2;


function Multiplication() {
   
   ARG1=$1
   ARG2=$2
      
   #Sprawdzenie poprawnosci ulozenia arg
   if [[ $ARG1 -le $ARG2 ]] ; then
   
      echo -n -e "\n  "
      for ((i=ARG1;i<=ARG2;i++))
      do
         echo -n -e "|$i \t "
      done
      
      echo -e " "
      for ((i=ARG1;i<=ARG2;i++))
      do
         echo -n "-------"
      done
      echo ""
      
      for ((i=ARG1;i<=ARG2;i++))
      do
         echo -n "$i"
         for ((j=ARG1;j<=ARG2;j++))
         do
            echo -n -e " |" $[ $i*$j ] "\t"
         done
         echo ""
      done
   fi
      
   if [[ $ARG1 -gt $ARG2 ]] ; then
   
      echo $ARG1 $ARG2
      echo -n -e "\n  "
      for ((i=ARG1;i>=ARG2;i--))
      do
         echo -n -e "|$i \t "
      done
      
      echo -e " "
      for ((i=ARG1;i>=ARG2;i--))
      do
         echo -n "-------"
      done
      echo ""
      
      for ((i=ARG1;i>=ARG2;i--))
      do
         echo -n "$i"
         for ((j=ARG1;j>=ARG2;j--))
         do
            echo -n -e " |" $[ $i*$j ] "\t"
         done
         echo ""
      done
   
   fi
}

#Sprawdzenie czy pierszy arg jest liczba
NUMBER='^(|\-)[0-9]+$'
if ! [[ $1 =~ $NUMBER ]] ; then
   echo -e "\nBledna skladnia"
   echo -e "\nPierwszy i drugi znak musi liczba"
   exit ERROR_FIRST_ARG
fi

#Sprawdzenie czy drugi arg jest liczba
NUMBER='^(|\-)[0-9]+$'
if ! [[ $2 =~ $NUMBER ]] ; then
   echo -e "\nBledna skladnia"
   echo -e "\nPierwszy i drugi znak musi liczba"
   exit ERROR_SECOND_ARG
fi


#### Main
Multiplication $1 $2