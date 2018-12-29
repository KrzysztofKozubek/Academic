#!/bin/tcsh
#Krzysztof Kozubek gr.1
set noglob

#Wartosci bledow
set ERROR_FIRST_ARG=1;
set ERROR_SECOND_ARG=1;
set ERROR_OPERATOR_ARG=2;

foreach arg ( $* )
	if ( $arg == '-h' ) then
		echo "\n\tUżycie:"
		echo "\n\n\t\t[liczba 1] [operator] [liczba 2]"
		echo "\n\tDostępne operatory:\n\n\t\tDodawanie '+'\n\t\tOdejmowanie '-'\n\t\tMnożenie '*'\n\t\tDzielenie '/'\n\t\tModulo '%'\n\t\tPotęgowanie '**'\n\n"		
		exit 0
	endif
end

set number1 = `echo $1 | grep '^[-0-9]*$'`
set number2 = `echo $3 | grep '^[-0-9]*$'`
echo $2
if (($number1)) then
else
	echo "Niepoprawny pierwszy argument \n Powinnien być liczbą całkowitą\n"
	exit $ERROR_FIRST_ARG
endif

if (($number2)) then
else
	echo "Niepoprawny trzeci argument \n Powinnien być liczbą całkowitą\n"
	exit $ERROR_SECOND_ARG
endif


#### MAIN

echo -n "\n  Operator: $2\n  "

if ( $1 < $3 ) then

	echo -n "\n  "
	@ i = $1
   while ($i <= $3)
      echo -n "|$i \t "
      @ i += 1
   end
	echo " "
	@ j = $1
   while($j <= $3)
      echo -n "--------"
      @ j += 1
   end
   echo ""

	set y = $1
	while ( $y <= $3 )
		echo -n "$y"
		set x = $1
		while ( $x <= $3 )
			@ k = ( $x $2 $y )	
			printf " | $k \t"
			@ x++
		end
		echo ''
		@ y++
	end
else

	echo -n "\n  "
	@ i = $1
   while ($i >= $3)
      echo -n "|$i \t "
      @ i -= 1
   end
	echo " "
	@ j = $1
   while($j >= $3)
      echo -n "--------"
      @ j -= 1
   end
   echo ""

	set y = $1
	while ( $y >= $3 )
		echo -n "$y"
		set x = $1
		while ( $x >= $3 )

			@ k = ( $x $2 $y )	
			printf " | $k \t"
			@ x--
		end
		echo ''
		@ y--
	end
endif

