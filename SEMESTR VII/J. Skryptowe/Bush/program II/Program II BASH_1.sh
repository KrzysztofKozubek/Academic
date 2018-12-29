#!/bin/bash
#Krzysztof Kozubek gr.1

function Multiplication {

   echo -e "  |1 \t |2 \t |3 \t |4 \t |5 \t |6 \t |7 \t |8 \t |9 \t "
   echo -e " ---------------------------------------------------------------------- "
   for ((i=1;i<=9;i++))
   do
   echo -n "$i"
      for ((j=1;j<=9;j++))
      do
         echo -n -e " |" $[ $i*$j ] "\t"
      done
      echo ""
   done
}

#### Main
Multiplication