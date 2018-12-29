#!/bin/tcsh
#Krzysztof Kozubek gr.1

set arg1 = "-h"
set arg2 = "--help"
set arg3 = "-q"
set arg4 = "--quiet"

#Sprawdznie czy nie zostal wywolana pomoc -h lub --help
foreach var ( $* )
   if ( "$var" == "$arg1" || "$var" == "$arg2") then

      echo ""
      echo "Program Krzysztof Kozubek gr.1"
      echo "To jest program, ktorego zadaniem jest wyluskiwanie podstawowych informacji o osobie zalogowanej takich jak:"
      echo "->imie nazwisko "
      echo "->login "
      echo ""
      echo "Bez agrumentowe wypisanie danych powyzej"
      echo "Arg -h i --help pomoc"
      echo "Arg -q i --quiet tryb cihcy"

      exit 0
   endif
end

#Sprawdznie czy nie zostal wywolana tryb cichy -q lub --quiet
foreach var ( $* )
   if ( "$var" == "$arg3" || "$var" == "$arg4") then

      exit 0
   endif
end

#Sprawdznie czy nie zostal podany zly arg
foreach var ( $* )

   echo $var | grep -q -e "[*-]"
   if ( $? == 0 ) then
      echo ""
      echo "Co miales na mysli? Niezrozumiala skladnia"
      echo ""
      echo "Program Krzysztof Kozubek gr.1"
      echo "To jest program, ktorego zadaniem jest wyluskiwanie podstawowych informacji o osobie zalogowanej takich jak:"
      echo "->imie nazwisko "
      echo "->login "
      echo ""
      echo "Bez agrumentowe wypisanie danych powyzej"
      echo "Arg -h i --help pomoc"
      echo "Arg -q i --quiet tryb cihcy"

      exit 1
   endif
end

#### MAIN
echo "login:" $USER
echo "Name and Surname:"
getent passwd $USER | cut -d: -f5 | cut -d, -f1
