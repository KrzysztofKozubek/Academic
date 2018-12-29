<?php

$ip = 'http://'.$_SERVER ["SERVER_NAME"];
require_once("config.php");
//require_once("../account/models/config.php");
echo("
</head>
<body>
<div class=\"main\">
<!--==============================header=================================-->
   	<header>
    	<div class=\"wrapper nav_box\">
        	<ul class=\"tech_links\">
            	<li><a href=\"$ip/about/\">About</a></li>
                <li><a href=\"$ip/reviews/\">Reviews</a></li>
                <li><a href=\"$ip/faq_help/\">FAQs</a></li>
                <li><a href=\"$ip/faq_help/\">Help</a></li>
                <li><a href$ip/contact/\">Contacts</a></li>
            </ul>
");



if (!isUserLoggedIn()){  
echo("
            <div class=\"nav_panel\">
            	<div class=\"login\">
                	<a href=\"$ip/account/login.php\" class=\"log\">Login</a>
                    or
                    <a href=\"$ip/account/register.php\" class=\"account\">Create an Account</a>
                </div>
                <div class=\"social\">
                	<a href=\"$ip/facebook/\" class=\"facebook\">Facebook</a>
                    <a href=\"$ip/google/\" class=\"twitter\">Twitter</a>
                </div>
            </div>
");
}
else
{
echo("
			
            	<div class=\"logout\">
                    <a href=\"$ip/account/logout.php\" class=\"account\">WYLOGUJ</a>
                </div>                
           
");
}

echo("
        </div>
        <nav>
        	<h1><a class=\"logo\" href=\"$ip/\">Watch</a></h1>
        	<ul class=\"sf-menu\">
            	<li class=\"current\"><a href=\"$ip/\"> FILMY </a></li>
            	<!--<li><a href=\"#\">channels</a>
                	<ul>
                        <li><a href=\"#\">Lorem ipsum dolor sit</a></li>
                        <li><a href=\"#\">Amet conse</a></li>
                        <li><a href=\"#\">Ctetur adipisicing</a></li>
                        <li><a href=\"#\">Elit sed do eiusmod</a></li>
                        <li><a href=\"#\">Tempor incididunt ut</a></li>
                        <li><a href=\"#\">Labore et dolore</a></li>                    
                    </ul>
                </li>
            	<li><a href=\"$ip/seriale/\"> SERIALE </a></li>-->
            	<li><a href=\"$ip/\"> NOWO&#346;CI </a></li>
            	<li><a href=\"$ip/archiwum/\"> ARCHIWUM TV </a></li>
            	<li><a href=\"$ip/recomend/\"> POLECANE </a></li>
            	<li><a href=\"$ip/best/\"> NAJWY&#379;EJ OCENIANA </a></li>
            </ul>
            <div class=\"clear\"></div>
        </nav>
        <div class=\"search_box\">
        	<div class=\"rate\"><span>407 225</span> films and programs</div>
			
            <form class=\"search\" id=\"search\" action=\"#search\" method=\"get\">
            	<input type=\"text\" placeholder=\"Podaj frazę którą chcesz znaleźć\" onkeyup=\"podpowiedz(event)\" autocomplete=off required id=\"tekst\"/>
                <a onClick=\"document.getElementById('search').submit()\" class=\"search_but\" required>search</a>
                <a href=\"$ip/search/\" class=\"adv_search\"> Advanced Search</a>
				
            </form>
        </div>
    </header>
");
?>