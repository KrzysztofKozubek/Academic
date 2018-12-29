#!/bin/bash
#Projekt zaliczeniowy BASH
#Krzysztof Kozubek

function QuietMode {

   exit 0
}

function Slide() {

    local -r TPUT=$(type -p tput || kill -9 $$)
    local -r IFS='' MESSAGE=${1:-$(date +%A), $(date +%T) | ↲ Next | ^c Quit }
    local -ri COLS=$($TPUT cols) ROWS=$($TPUT lines)
    local -r COLORS=(bold=1 italics=3 under=4 hide=8 cross=9 red=31 green=32 yellow=33 blue=34 purple=35 cyan=36 bgRed=41 bgGreen=42 bgYellow=43 bgBlue=44 bgPurple=45 bgCyan=46 bgRand1=$(( ( RANDOM % 8 )  + 40 )) bgRand2=$(( ( RANDOM % 8 )  + 40 )) bgRand3=$(( ( RANDOM % 8 )  + 40 )) bgRand4=$(( ( RANDOM % 8 )  + 40 )) bgRand5=$(( ( RANDOM % 8 )  + 40 )) bgRand6=$(( ( RANDOM % 8 )  + 40 )) rand1=$(( ( RANDOM % 8 )  + 30 )) rand2=$(( ( RANDOM % 8 )  + 30 )) rand3=$(( ( RANDOM % 8 )  + 30 )) rand4=$(( ( RANDOM % 8 )  + 30 )) rand5=$(( ( RANDOM % 8 )  + 30 )) rand6=$(( ( RANDOM % 8 )  + 30 )) end=)
    local -i CENTER=0 LINENUM=0 CTRPOS=0 MSGPOS=0 HASCOLOR=1
    local LINE='' RAW=''
    trap "$TPUT clear" 0
    $TPUT clear

    while read LINE && RAW=$LINE; do

        #color
        [ "$LINE" == '!!color' ]    && HASCOLOR=1 && continue
        [ "$LINE" == '!!nocolor' ]  && HASCOLOR=0 && continue
        if [ $HASCOLOR -eq 1 ]; then
            for C in ${COLORS[@]}; do
                RAW=${RAW//<${C%%=*}>/}
                LINE=${LINE//<${C%%=*}>/\\033\[0\;${C##*=}m}
            done
        fi

        #format font
        [ "$RAW" == '!!center' ]    && CENTER=1 && continue
        [ "$RAW" == '!!nocenter' ]  && CENTER=0 && continue
        [ "$RAW" == '!!pause' ]     && read -s < /dev/tty && continue
        if [ "$RAW" == '!!sep' ]; then
            printf -vRAW "%${COLS}s" '' && RAW=${RAW// /-}
            LINE=${LINE//\!\!sep/$RAW}
        fi

        [ ${#MESSAGE} -lt $COLS ]   && MSGPOS=$(((COLS-1)-${#MESSAGE}))
        [ ${#RAW} -le $COLS ]       && CTRPOS=$(((COLS-${#RAW})/2))
        [ $CENTER -eq 1 ]           && $TPUT cup $LINENUM $CTRPOS || $TPUT cup $LINENUM 0
        
        printf -- "${LINE//%/%%}\n"
        
        $TPUT cup $ROWS $COLS && let LINENUM++
        [ ${#RAW} -gt $COLS ] && let LINENUM++
    done

    $TPUT cup $ROWS 0 && printf "%${MSGPOS}s\033[0m%s" " " $MESSAGE
    read -s -n 1 CHAR < /dev/tty
    return 0
}

function Help() {

	cat << EOF

	Presentation.sh it's script to present using terminal of linux 
	(recommand using and tested in Ubuntu)
	
	Options:
	
	-h --help	help
	-g --generete 	generete template Presentation
	-q --quiet	quiet mode

	Priorytet options:

	-- First 	help 
	-- Second 	quiet mode
	-- Third 	Bad parameter
	-- Fourth 	generete template presentation
'
EOF
}

function GenereteTemplate() {

MY_PATH="`dirname \"$0\"`"              
MY_PATH="`( cd \"$MY_PATH\" && pwd )`"

cat << EOF > ExamplePresentation.sh
#!/bin/bash 
#Projekt zaliczeniowy BASH
#Krzysztof Kozubek
source $MY_PATH/./presentation.sh
EOF

	cat <<< '
function Help() {

	cat << EOF

	NamePresentation.sh it is script to keep Slides presentation.
	!!Important This script has to have script presentation.sh in the same directory! !!Important
	
	Options:
	
	-h --help	help
	-q --quiet	quiet mode

	Priorytet options:

	-- First 	help 
	-- Second 	quiet mode
	-- Third 	Bad parameter
	-- Fourth 	show presentation


	Tamplate create presentation:
	#!/bin/bash
	source ./presentation.sh
	#slide 1
	Slide <<EOF
		Content of slide 1
	\EOF
	

	Contant have options:

	Alignment text:
	!!center	center text
	!!nocenter	end center text

	Format text:
	<bold>		bold text
	<italics>	italics text
	<under>		create underline 
	<hide>		hide text
	<cross>		crossing text
	<end>		end formating text

	Color Background on text:
	<bgRed>		background red
	<bgGreen>	background green
	<bgYellow>	background yellow
	<bgBlue>	background blue
	<bgPurple>	background purple
	<bgCyan>	background cyan
	<end>		end formating text

	Color text:
	<red>		red color text
	<green>		green color text
	<yellow>	yellow color text
	<blue>		blue color text
	<purple>	purple color text
	<cyan>		cyan color text
	<end>		end formating text

	Rand Color Background on text:
	<bgRand1>	rand backgroud on text
	<bgRand2> 	rand backgroud on text
	<bgRand3>	rand backgroud on text 
	<bgRand4> 	rand backgroud on text
	<bgRand5> 	rand backgroud on text
	<bgRand6> 	rand backgroud on text

	Rand Color text:
	<rand1>		rand color text
	<rand2>		rand color text 
	<rand3>		rand color text 
	<rand4>		rand color text 
	<rand5>		rand color text 
	<rand6>		rand color text 

	Variable:
	\$(function)
	Example: 	To day is \$(data +%A)

	Pause
	!!pause 	waiting for push enter key

	Separator
	<sep>		create line on the whole width



EOF
}

function QuietMode {

   exit 0
}

#### MAIN

#Available options in script
ARG1="-h"
ARG2="--help"
ARG3="-q"
ARG4="--quiet"

#Check options -h and --help
for VAR in $*; do

   if [ $ARG1 = $VAR ] || [ $ARG2 = $VAR ] ; then
      Help
      exit 0
   fi
done

#Check options -q lub --quiet
for VAR in $*; do

   if [ $ARG3 = $VAR ] || [ $ARG4 = $VAR ] ; then
      QuietMode
   fi
done

#Chekc not know parameter
for VAR in $*; do

	if [ $ARG3 != $VAR ] && [ $ARG4 != $VAR ] ; then
		echo -e "\n	You use bad parameter\n"
		Help
		exit 1
	fi
done


############ Presentation

Slide <<EOF

!!center
<red>
!!sep

 ██████╗██████╗ ███████╗ █████╗ ████████╗███████╗
██╔════╝██╔══██╗██╔════╝██╔══██╗╚══██╔══╝██╔════╝
██║     ██████╔╝█████╗  ███████║   ██║   █████╗  <purple>
██║     ██╔══██╗██╔══╝  ██╔══██║   ██║   ██╔══╝  
╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████╗
 ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
        
<green>                                         
!!sep

██████╗ ██╗   ██╗
██╔══██╗╚██╗ ██╔╝
██████╔╝ ╚████╔╝ <end>
██╔══██╗  ╚██╔╝  
██████╔╝   ██║   
╚═════╝    ╚═╝   
     
<yellow>            
!!sep

██╗  ██╗██████╗ ██╗███████╗    
██║ ██╔╝██╔══██╗██║██╔════╝    
█████╔╝ ██████╔╝██║███████╗    <cyan>
██╔═██╗ ██╔══██╗██║╚════██║     
██║  ██╗██║  ██║██║███████║    
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚══════╝   

<yellow> 
!!sep

██╗  ██╗ ██████╗ ███████╗██╗   ██╗██████╗ ███████╗██╗  ██╗
██║ ██╔╝██╔═══██╗╚══███╔╝██║   ██║██╔══██╗██╔════╝██║ ██╔╝
█████╔╝ ██║   ██║  ███╔╝ ██║   ██║██████╔╝█████╗  █████╔╝ <cyan>
██╔═██╗ ██║   ██║ ███╔╝  ██║   ██║██╔══██╗██╔══╝  ██╔═██╗
██║  ██╗╚██████╔╝███████╗╚██████╔╝██████╔╝███████╗██║  ██╗
╚═╝  ╚═╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝


EOF

Slide <<EOF

!!center
<rand1> 
 ██████╗███████╗███╗   ██╗████████╗███████╗██████╗ 
██╔════╝██╔════╝████╗  ██║╚══██╔══╝██╔════╝██╔══██╗
██║     █████╗  ██╔██╗ ██║   ██║   █████╗  ██████╔╝<rand2>
██║     ██╔══╝  ██║╚██╗██║   ██║   ██╔══╝  ██╔══██╗
╚██████╗███████╗██║ ╚████║   ██║   ███████╗██║  ██║
 ╚═════╝╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
<end>
!!sep
<rand3>
W celu wyśrodkowania tekstu należy użyć komendy w tekście !!center
Przykładowy tekst:
<rand4>
!!center
! Ten tekst będzie wyśrodokowany !
! Zakończenie wyśrodkowania !

<spr>
!!nocenter
! Ten tekst nie będzie wyśrodokowany !
! Zakończenie tekstu !

EOF

Slide <<EOF

!!center
<rand1>
███████╗ ██████╗ ██████╗ ███╗   ███╗ █████╗ ████████╗
██╔════╝██╔═══██╗██╔══██╗████╗ ████║██╔══██╗╚══██╔══╝
█████╗  ██║   ██║██████╔╝██╔████╔██║███████║   ██║   <rand2>
██╔══╝  ██║   ██║██╔══██╗██║╚██╔╝██║██╔══██║   ██║   
██║     ╚██████╔╝██║  ██║██║ ╚═╝ ██║██║  ██║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   
                                                    <rand1> 
███████╗ ██████╗ ███╗   ██╗████████╗
██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝
█████╗  ██║   ██║██╔██╗ ██║   ██║   <rand2>
██╔══╝  ██║   ██║██║╚██╗██║   ██║   
██║     ╚██████╔╝██║ ╚████║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   
!!sep

Dostpne są formatowania czcionki:

!!sep
!!nocenter
<end> 
	bold 	-> 	<bold>pogrubia tekst<end>
	italics -> 	<italics>pochyla tekst<end>
	under 	-> 	<under>linia pod tekstem<end>
	hide 	-> 	<hide>tekstu nie bedzie widac <end>
	cross 	-> 	<cross>przekreslony tekst<end>

!!sep
Użycie <formatowanie>
Zakończenia formatowania wpisujesz komende end <end>
EOF

Slide <<EOF

!!center
<rand1>
███████╗ ██████╗ ██████╗ ███╗   ███╗ █████╗ ████████╗
██╔════╝██╔═══██╗██╔══██╗████╗ ████║██╔══██╗╚══██╔══╝
█████╗  ██║   ██║██████╔╝██╔████╔██║███████║   ██║   <rand2>
██╔══╝  ██║   ██║██╔══██╗██║╚██╔╝██║██╔══██║   ██║   
██║     ╚██████╔╝██║  ██║██║ ╚═╝ ██║██║  ██║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   
                                                    <rand1> 
███████╗ ██████╗ ███╗   ██╗████████╗
██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝
█████╗  ██║   ██║██╔██╗ ██║   ██║   <rand2>
██╔══╝  ██║   ██║██║╚██╗██║   ██║   
██║     ╚██████╔╝██║ ╚████║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   
!!sep

Dostpne są formatowania czcionki:

!!sep
!!nocenter
<end> 
	bgRed	-> 	<bgRed>tlo czerwone <end>
	bgGreen	-> 	<bgGreen>tlo zielone <end>
	bgYellow-> 	<bgYellow>tlo żółte  <end>
	bgBlue	-> 	<bgBlue>tlo niebieskie <end>
	bgPurple-> 	<bgPurple>tlo purpurowe  <end>
	bgCyan	-> 	<bgCyan>tlo cyjanowe<end>

!!sep
Użycie <colorTła>
Zakończenia formatowania wpisujesz komende end <end>

EOF

Slide <<EOF

!!center
<rand1>
███████╗ ██████╗ ██████╗ ███╗   ███╗ █████╗ ████████╗
██╔════╝██╔═══██╗██╔══██╗████╗ ████║██╔══██╗╚══██╔══╝
█████╗  ██║   ██║██████╔╝██╔████╔██║███████║   ██║   <rand2>
██╔══╝  ██║   ██║██╔══██╗██║╚██╔╝██║██╔══██║   ██║   
██║     ╚██████╔╝██║  ██║██║ ╚═╝ ██║██║  ██║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   
                                                    <rand1> 
███████╗ ██████╗ ███╗   ██╗████████╗
██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝
█████╗  ██║   ██║██╔██╗ ██║   ██║   <rand2>
██╔══╝  ██║   ██║██║╚██╗██║   ██║   
██║     ╚██████╔╝██║ ╚████║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   
!!sep

Dostpne są formatowania czcionki:

!!sep
!!nocenter
<end> 
	red	-> 	<red>czerwony tekst<end>
	green	-> 	<green>zielony tekst <end>
	yellow	-> 	<yellow>żółty tekst <end>
	blue	-> 	<blue>niebieski tekst <end>
	purple	-> 	<purple>purpurowy tekst<end>
	cyan	-> 	<cyan>cyjanowy tekst<end>

!!sep
Użycie <color>
W celu zakończenia formatowania wpisujesz komende end <end>

EOF

Slide <<EOF

!!center
<rand1>
███████╗ ██████╗ ██████╗ ███╗   ███╗ █████╗ ████████╗
██╔════╝██╔═══██╗██╔══██╗████╗ ████║██╔══██╗╚══██╔══╝
█████╗  ██║   ██║██████╔╝██╔████╔██║███████║   ██║   <rand2>
██╔══╝  ██║   ██║██╔══██╗██║╚██╔╝██║██╔══██║   ██║   
██║     ╚██████╔╝██║  ██║██║ ╚═╝ ██║██║  ██║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   
                                                    <rand1> 
███████╗ ██████╗ ███╗   ██╗████████╗
██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝
█████╗  ██║   ██║██╔██╗ ██║   ██║   <rand2>
██╔══╝  ██║   ██║██║╚██╗██║   ██║   
██║     ╚██████╔╝██║ ╚████║   ██║   
╚═╝      ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   
!!sep

Dostpne są formatowania czcionki:

!!sep
!!nocenter
<end> 
	rand1	-> 	<rand1>randomowy kolor 1<end>
	rand2	-> 	<rand2>randomowy kolor 2<end>
	rand3	-> 	<rand3>randomowy kolor 3<end>
	rand4	-> 	<rand4>randomowy kolor 4<end>
	rand5	-> 	<rand5>randomowy kolor 5<end>
	rand6	-> 	<rand6>randomowy kolor 6<end>

!!sep

	bgRand1	-> 	<bgRand1>randomowe tlo 1<end>
	bgRand2	-> 	<bgRand2>randomowe tlo 2<end>
	bgRand3	-> 	<bgRand3>randomowe tlo 3<end>
	bgRand4	-> 	<bgRand4>randomowe tlo 4<end>
	bgRand5	-> 	<bgRand5>randomowe tlo 5<end>
	bgRand6	-> 	<bgRand6>randomowe tlo 6<end>

!!sep
Użycie <randNr>
W celu zakończenia formatowania wpisujesz komende end <end>
EOF

Slide <<EOF

!!center
<rand1>
██╗   ██╗ █████╗ ██████╗ ██╗ █████╗ ██████╗ ██╗     ███████╗
██║   ██║██╔══██╗██╔══██╗██║██╔══██╗██╔══██╗██║     ██╔════╝
██║   ██║███████║██████╔╝██║███████║██████╔╝██║     █████╗  <rand2>
╚██╗ ██╔╝██╔══██║██╔══██╗██║██╔══██║██╔══██╗██║     ██╔══╝  
 ╚████╔╝ ██║  ██║██║  ██║██║██║  ██║██████╔╝███████╗███████╗
  ╚═══╝  ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝
!!sep

Dostępne jest wykorzystanie funkcji systemowych: 
<end>
!!sep



Przykładowo, wypisanie czasu w różnych regionach świata:
!!sep
   California      $(TZ=America/Los_Angeles date)
   Panama          $(TZ=America/Panama date)
   Virgin Islands  $(TZ=America/Virgin date)
   Tahiti          $(TZ=Pacific/Tahiti date)
   Athens          $(TZ=Europe/Athens date)
!!sep

Użycie: dziś mamy \$(date +%A) = $(date +%A)
EOF

Slide <<EOF

!!center
<rand1>
██╗   ██╗ █████╗ ██████╗ ██╗ █████╗ ██████╗ ██╗     ███████╗
██║   ██║██╔══██╗██╔══██╗██║██╔══██╗██╔══██╗██║     ██╔════╝
██║   ██║███████║██████╔╝██║███████║██████╔╝██║     █████╗  <rand2>
╚██╗ ██╔╝██╔══██║██╔══██╗██║██╔══██║██╔══██╗██║     ██╔══╝  
 ╚████╔╝ ██║  ██║██║  ██║██║██║  ██║██████╔╝███████╗███████╗
  ╚═══╝  ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝
!!sep

Dostępne jest wykorzystanie funkcji systemowych: 
<end>
!!sep
!!nocenter


Przykładowo, kilka informacji o użytkowniku:
!!sep
	System 			$(uname)
	Network 		$(uname -n)
	Kernel Ver 		$(uname -v)
	Kernel Release 	$(uname -r)
	Hardware 		$(uname -m)
!!sep
	Podsumowanie 	

	$(uname -a)

!!sep
EOF

Slide <<EOF

!!center
<rand1>
██╗   ██╗ █████╗ ██████╗ ██╗ █████╗ ██████╗ ██╗     ███████╗
██║   ██║██╔══██╗██╔══██╗██║██╔══██╗██╔══██╗██║     ██╔════╝
██║   ██║███████║██████╔╝██║███████║██████╔╝██║     █████╗  <rand2>
╚██╗ ██╔╝██╔══██║██╔══██╗██║██╔══██║██╔══██╗██║     ██╔══╝  
 ╚████╔╝ ██║  ██║██║  ██║██║██║  ██║██████╔╝███████╗███████╗
  ╚═══╝  ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚══════╝
!!sep

Dostępne jest wykorzystanie funkcji systemowych: 
<end>
!!sep
!!nocenter


Informacje o CPU:
!!sep
$(lscpu)
!!sep
EOF

Slide <<EOF

!!center
<rand1>
██████╗  █████╗ ██╗   ██╗███████╗███████╗
██╔══██╗██╔══██╗██║   ██║██╔════╝██╔════╝
██████╔╝███████║██║   ██║███████╗█████╗  <rand2>
██╔═══╝ ██╔══██║██║   ██║╚════██║██╔══╝  
██║     ██║  ██║╚██████╔╝███████║███████╗
╚═╝     ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝
!!sep

Dostępne jest wykorzystanie funkcji pause w prezentacji: 
<end>
!!sep
!!nocenter
!!pause
Pierwsza pauza
!!sep
   California      $(TZ=America/Los_Angeles date)
!!pause
   Panama          $(TZ=America/Panama date)
!!pause
   Virgin Islands  $(TZ=America/Virgin date)
!!pause
   Tahiti          $(TZ=Pacific/Tahiti date)
!!pause
   Athens          $(TZ=Europe/Athens date)
!!pause
!!sep
EOF


Slide <<EOF

!!center
<rand1>
████████╗██╗  ██╗███████╗    ███████╗███╗   ██╗██████╗ <rand2>
╚══██╔══╝██║  ██║██╔════╝    ██╔════╝████╗  ██║██╔══██╗<rand3>
   ██║   ███████║█████╗      █████╗  ██╔██╗ ██║██║  ██║<rand4>
   ██║   ██╔══██║██╔══╝      ██╔══╝  ██║╚██╗██║██║  ██║<rand5>
   ██║   ██║  ██║███████╗    ███████╗██║ ╚████║██████╔╝<rand6>
   ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝╚═════╝ 
                                                       
EOF
' >> ExamplePresentation.sh

chmod +x ExamplePresentation.sh
}





#### MAIN

#Available options in script
ARG1="-h"
ARG2="--help"
ARG3="-q"
ARG4="--quiet"
ARG5="-g"
ARG6="--generete"
ARG7="presentation.sh"

if [[ $0 == *$ARG7* ]] ; then

#Check options -h and --help
for VAR in $*; do

   if [ $ARG1 = $VAR ] || [ $ARG2 = $VAR ] ; then
      Help
      exit 0
   fi
done

#Check options -q lub --quiet
for VAR in $*; do

   if [ $ARG3 = $VAR ] || [ $ARG4 = $VAR ] ; then
      QuietMode
      exit 0
   fi
done

#Chekc not know parameter
for VAR in $*; do

	if [ $ARG5 != $VAR ] && [ $ARG6 != $VAR ] ; then
		echo -e "\n	You use bad parameter\n"
		Help
		exit 1
	fi
done


#Check generete template presentation
for VAR in $*; do

	if [ $ARG5 = $VAR ] || [ $ARG6 = $VAR ] ; then

		echo -e "\n\n 	Start generete a ExamplePresentation.sh \n"
		GenereteTemplate
		echo -e "	Done! Generete ExamplePresentation.sh complete\n\n 	Now if you want check possibilities use command:\n 	./ExamplePresentation.sh to start ExamplePresentation\n 	./ExamplePresentation.sh -h to show possibilities\n\n"
		exit 0
	fi
done

fi