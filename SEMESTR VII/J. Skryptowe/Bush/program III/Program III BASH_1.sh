#!/bin/bash
#Krzysztof Kozubek gr.1

function Validation() {

	if [[ $1 =~ ^[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
		ip1=`echo $1 | cut -d"." -f1`
		ip2=`echo $1 | cut -d"." -f2`
		ip3=`echo $1 | cut -d"." -f3`
		ip4=`echo $1 | cut -d"." -f4`

		if [ $ip1 -gt 255 ] || [ $ip2 -gt 255 ] || [ $ip3 -gt 255 ] || [ $ip4 -gt 255 ]; then 
		   return 1; 
		fi

	else
		return 1
	fi
}

function IPToInt() {

	IP=$1
	for (( i=0 ; i<4 ; ++i )); 
	do
	   ((IPNUM+=${IP%%.*}*$((256**$((3-${i}))))))
	   IP=${IP#*.}
	done
	echo $IPNUM 
}

function itoa {

	echo -n $(($(($(($((${1}/256))/256))/256))%256)).
	echo -n $(($(($((${1}/256))/256))%256)).
	echo -n $(($((${1}/256))%256)).
	echo $((${1}%256)) 
}

#### MAIN

if ! Validation $1; then 
   echo -e "\nPierwszy adres IP jest niepoprawny \nPrzykładowy adres IP: 127.0.0.1\n"; 
   exit 1; 
fi
if ! Validation $2; then 
   echo -e "\nDrugi adres IP jest niepoprawny \nPrzykładowy adres IP: 127.0.0.1\n"; 
   exit 1; 
fi

arg1=$(IPToInt $1)
arg2=$(IPToInt $2)

if [ $arg1 -gt $arg2 ]; then
	temp=$arg1
	arg1=$arg2
	arg2=$temp
fi

for (( i=$arg1 ; i<=$arg2 ; ++i ))
do
   ip=$(itoa $i)
	ping -c 1 "$ip" > /dev/null 2>&1
   if [ $? -eq 0 ]; then
      echo "$ip odpowiada."
   else
      echo "$ip nie odpowiada."
  fi
done 


