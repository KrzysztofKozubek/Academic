#!/bin/csh
#Krzysztof Kozubek gr.1

set ip11=`echo $1 | cut -d"." -f1`
set ip12=`echo $1 | cut -d"." -f2`
set ip13=`echo $1 | cut -d"." -f3`
set ip14=`echo $1 | cut -d"." -f4`

set ip21=`echo $2 | cut -d"." -f1`
set ip22=`echo $2 | cut -d"." -f2`
set ip23=`echo $2 | cut -d"." -f3`
set ip24=`echo $2 | cut -d"." -f4`

if ( $#argv != 2 ) then
	echo "\nPodane argumenty są niepoprawne.\n Poprawne użycie: ./Program\ III\ CSH_1.csh 192.168.0.1 192.168.0.20\n"
	exit 1
endif

set ip1 = `echo $1 | egrep '^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$'`
set ip2 = `echo $2 | egrep '^([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})$'`

if ( $ip1 != "" ) then
else
   echo "\nPierwszy adres IP jest niepoprawny \nPrzykładowy adres IP: 127.0.0.1\n"
   exit 1
endif

if ( $ip2 != "" ) then
else
	echo "\nDrugi adres IP jest niepoprawny \nPrzykładowy adres IP: 127.0.0.1\n"
	exit 1
endif

if ( $ip11 >= 0 && $ip11 <= 255 && $ip12 >= 0 && $ip12 <= 255 && $ip13 >= 0 && $ip13 <= 255 && $ip14 >= 0 && $ip14 <= 255 ) then
else
	echo "\nPierwszy adres IP jest niepoprawny \nPrzykładowy adres IP: 127.0.0.1\n"
	exit 1
endif

if ( $ip21 >= 0 && $ip21 <= 255 && $ip22 >= 0 && $ip22 <= 255 && $ip23 >= 0 && $ip23 <= 255 && $ip24 >= 0 && $ip24 <= 255 ) then
else
	echo "\nDrugi adres IP jest niepoprawny \nPrzykładowy adres IP: 127.0.0.1\n"
	exit 1
endif


#### MAIN

if ( $ip11 == $ip21 ) then
   if ( $ip12 == $ip22 ) then
      if ( $ip13 == $ip23 ) then
         if ( $ip14 < $ip24 ) then
            set arg1=$1
            set arg2=$2
         else
            set arg1=$2
            set arg2=$1
         endif
      else if ( $ip13 < $ip23 ) then
         set arg1=$1
         set arg2=$2
      else if ( $ip13 > $ip23 ) then
         set arg1=$2
         set arg2=$1
      endif
   else if ( $ip12 < $ip22 ) then
      set arg1=$1
      set arg2=$2
   else if ( $ip12 > $ip22 ) then
      set arg1=$2
      set arg2=$1
   endif
else if ( $ip11 < $ip21 ) then
   set arg1=$1
   set arg2=$2
else if ( $ip11 > $ip21 ) then
   set arg1=$2
   set arg2=$1
endif


foreach i (`seq $ip11 $ip21`)
   set ip12=`echo $arg1 | cut -d"." -f2`
   set ip22=`echo $arg2 | cut -d"." -f2`
   if ( $i == $ip11 && $i != $ip21 ) then
      set ip22=255
   endif
   if ( $i != $ip11 && $i != $ip21 ) then 
      set ip12=0
      set ip22=255
   endif
   if ( $i == $ip21 && $i != $ip11 ) then 
      set ip12=0
   endif
   foreach j (`seq $ip12 $ip22`)
      set ip13=`echo $arg1 | cut -d"." -f3`
      set ip23=`echo $arg2 | cut -d"." -f3`
      if ( $j == $ip12 && $j != $ip22 ) then
         set ip23=255
      endif
      if ( $j != $ip12 && $j != $ip22 ) then 
         set ip13=0
         set ip23=255
      endif
      if ( $j == $ip22 && $j != $ip12 ) then 
         set ip13=0
      endif
      foreach k (`seq $ip13 $ip23`)
         set ip14=`echo $arg1 | cut -d"." -f4`
         set ip24=`echo $arg2 | cut -d"." -f4`
         if ( $k == $ip13 && $k != $ip23 ) then
            set ip24=255
         endif
         if ( $k != $ip13 && $k != $ip23 ) then 
            set ip14=0
            set ip24=255
         endif
         if ( $k == $ip23 && $k != $ip13 ) then 
            set ip14=0
         endif
         foreach l (`seq $ip14 $ip24`)
            ping -c 1 -W 1 "$i.$j.$k.$l" >/dev/null
            if ($status) then
               echo "$i.$j.$k.$l nie odpowiada."
            else
               echo "$i.$j.$k.$l odpowiada."
            endif
         end
      end
   end
end