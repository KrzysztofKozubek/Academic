#!/bin/tcsh
#Krzysztof Kozubek gr.1


#Wartosci bledow
set ERROR_FIRST_ARG=1;
set ERROR_SECOND_ARG=2;

#Sprawdzenie czy pierszy arg jest liczba
echo $1 | grep -q -e "^[-0-9]"
if ( $? != 0 ) then
   echo -n "\nBledna skladnia"
   echo -n "\nPierwszy i drugi znak musi liczba dodatnią\n\n"
   exit 1
endif

#Sprawdzenie czy drugi arg jest liczba
echo $2 | grep -q -e "^[-0-9]"
if ( $? != 0 ) then
   echo -n "\nBledna skladnia"
   echo -n "\nPierwszy i drugi znak musi liczba dodatnią\n\n"
   exit 2
endif


set arg1 = $1
set arg2 = $2

#### MAIN
if ( $arg1 < $arg2 ) then 

   echo -n "\n  "
      
   @ i = $arg1
   while ($i <= $arg2)
      echo -n "|$i \t "
      @ i += 1
   end


   echo " "

   @ j = $arg1
   while($j <= $arg2)
      echo -n "--------"
      @ j += 1
   end

   echo ""

   @ x = $arg1
   while ($x <= $arg2)

      echo -n "$x"
      @ y = $arg1
      while ($y <= $arg2)
      
         @ w = $x * $y
         echo -n " | $w \t"
         @ y += 1
      end
      @ x += 1
      echo ""
   end
endif

if ( $arg1 > $arg2 ) then 

   echo -n "\n  "
      
   @ i = $arg1
   while ($i >= $arg2)
      echo -n "|$i \t "
      @ i -= 1
   end


   echo " "

   @ j = $arg1
   while($j >= $arg2)
      echo -n "--------"
      @ j -= 1
   end

   echo ""

   @ x = $arg1
   while ($x >= $arg2)

      echo -n "$x"
      @ y = $arg1
      while ($y >= $arg2)
      
         @ w = $x * $y
         echo -n " | $w \t"
         @ y -= 1
      end
      @ x -= 1
      echo ""
   end
endif

