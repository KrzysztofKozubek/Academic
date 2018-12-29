#!/bin/bash
#Krzysztof Kozubek gr.1

function Help {

      echo ""
      echo "Program Krzysztof Kozubek gr.1"
      echo "To jest program, ktorego zadaniem jest wyluskiwanie podstawowych informacji o  "
      echo "osobie zalogowanej takich jak:"
      echo "->imie nazwisko "
      echo "->login "
      echo ""
      echo "Bez agrumentowe wypisanie danych powyzej"
      echo "Arg -h i --help pomoc"
      echo "Arg -q i --quiet tryb cihcy"
}

function HelpMisstake {

   echo ""
   echo "Co miales na mysli? Niezrozumiala skladnia"
   Help
   exit 1
}

function QuietMode {

   exit 0
}

function ShowBaseInf {

   echo "login:" $USER
   echo "Name and Surname:"
   getent passwd $USER | cut -d: -f5 | cut -d, -f1
}

#dostepne opcje programu
ARG1="-h"
ARG2="--help"
ARG3="-q"
ARG4="--quiet"

#### Main

#Sprawdznie czy nie zostal wywolana pomoc -h lub --help
for VAR in $*; do

   if [ $ARG1 = $VAR ] || [ $ARG2 = $VAR ] ; then
      Help
      exit 0
   fi
done

#Sprawdznie czy nie zostal wywolana tryb cichy -q lub --quiet
for VAR in $*; do

   if [ $ARG3 = $VAR ] || [ $ARG4 = $VAR ] ; then
      QuietMode
   fi
done

#Sprawdznie czy nie zostal podany zly arg
for VAR in $*; do

   if [ "-" = "${VAR:0:1}" ] ; then
      HelpMisstake
   fi
done


#Wywolanie skryptu
ShowBaseInf
