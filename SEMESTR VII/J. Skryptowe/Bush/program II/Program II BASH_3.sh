#!/bin/bash
#Krzysztof Kozubek gr.1
set -f

#Wartosci bledow
ERROR_FIRST_ARG=1;
ERROR_SECOND_ARG=1;
ERROR_MISSTAKE_OPERATOR=2;

Usage(){

cat << EOF

	Użycie:

		[liczba 1] [operator] [liczba 2]

	Dostępne operatory:

		=>Dodawanie '+'
		=>Odejmowanie '-'
		=>Mnożenie '*'
		=>Dzielenie '/'
		=>Modulo '%'
		=>Potęgowanie '**'


EOF
}

function Table() {

	echo -n -e "\n  Operator: $2\n  "
	for i in $(eval echo {$1..$3})
	do
		echo -n -e "|$i \t "
	done
	echo -e " "
	for i in $(eval echo {$1..$3})
	do
		echo -n "-------"
	done
	echo

	for i in $(eval echo {$1..$3})
	do
		echo -n "$i"
		for j in $(eval echo {$1..$3})
		do
			echo -n -e " |$((i $2 j))\t"
		done
		echo
	done
}

function Operation() {
	
	echo "$1 $2 $3"
	number='^(|\-)[0-9]+$'

	if ! [[ $1 =~ $number ]] ; then 
		echo "Niepoprawny pierwszy argument. Pierwszy arg musi być liczbą"
		exit $ERROR_FIRST_ARG
	fi

	if ! [[ $3 =~ $number ]] ; then
		echo "Niepoprawny trzeci argument. Trzeci arg musi być liczbą"
		exit $ERROR_SECOND_ARG
	fi

	operator='(\+|\-|\*|\/|\%|\*\*)'

	if ! [[ $2 =~ $operator ]] ; then
		echo "Niepoprawny operator. Argument trzeci musi być operatorem"
		exit $ERROR_MISSTAKE_OPERATOR
	fi
}


#### MAIN

while getopts ":h" FLAG; do
	case $FLAG in
		h)
			Usage
			exit 0
			;;
	esac
done

Operation $1 $2 $3
Table $1 $2 $3
