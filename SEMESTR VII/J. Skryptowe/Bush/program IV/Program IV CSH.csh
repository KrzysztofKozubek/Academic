#!/usr/bin/tcsh
# Krzysztof Kozubek

if ( $#argv != 3 ) then
	echo "Złe arg. Przykładowa składnia: IP IP port"
	exit 0
endif

set ip1 = `./Validation.csh $1`
set ip2 = `./Validation.csh $2`

if ( "$ip1" != "0" ) then
	echo "Ip $1 jest niepoprawne"
	exit 0
endif
if ( "$ip2" != "0" ) then
	echo "Ip $2 jest niepoprawne"
	exit 0
endif
endif

set tport = `echo $3 | egrep '^[0-9]{1,6}+(\,[0-9]{1,6})*$'`

if ( "${%tport}" > 0 ) then
	set port = ($tport:as/,/ /)
else 
	echo "Trzeci arg jest niepoprawny, przykład: 80,22"
    exit 0
endif

set iIp1 = `./IPToInt.csh $1`
set iIp2 = `./IPToInt.csh $2`

if ( $iIp1 < $iIp2 ) then
	set minorIp = $iIp1
	set majorIp = $iIp2 
else 
	set minorIp = $iIp2
	set majorIp = $iIp1 
endif

while ( $minorIp <= $majorIp )
	foreach n ($port)
		set tmp = `./IntToIP.csh $minorIp`
		nc -zv -w 1 -i 1 $tmp $n > /dev/null
		if ( $? == 0 ) then
			nc -zv -w 1 -i 1 $tmp $n
		else
			echo "$tmp $n martwy"
		endif
	end
	@ minorIp++
end