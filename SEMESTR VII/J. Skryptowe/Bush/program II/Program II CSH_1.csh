#!/bin/tcsh
#Krzysztof Kozubek gr.1

#### Main

echo "  |1 \t |2 \t |3 \t |4 \t |5 \t |6 \t |7 \t |8 \t |9 \t "
echo " ---------------------------------------------------------------------- "
foreach x (1 2 3 4 5 6 7 8 9)

   echo -n $x
   foreach y (1 2 3 4 5 6 7 8 9)
      @ w = $x * $y
      echo -n " | $w \t"
   end
   echo ""
end
