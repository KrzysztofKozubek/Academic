<?php

$ip = 'http://'.$_SERVER ["SERVER_NAME"];
require_once("../include/config.php");
include("../account/models/config.php");
include("../createQuestion.php");

$address = "$_SERVER[REQUEST_URI]";
$id = strchr($address, "?");
$id = substr($id, 1);
$results = $mysqli->query("SELECT COUNT(*) as t_records FROM movie");
if($results){
$total_records = $results->fetch_object();
$total_groups = ceil($total_records->t_records/$items_per_group);
$results->close(); 
}




	showHeadDeffoult($ip); 
	showSignInOut($loggedInUser, $ip);
showStartContent();
					
echo <<< SKRYPT

				<h2>Kilka słów o nas | naszej stronie</h2>
                    <ul class="latest_movies">
					
SKRYPT;
	
echo("
						<li>
                        	<div class=\"extra_container \">
								
								<div>
									<h3>Kim jesteśmy</h3>
									Jesteśmy grupką młodych, która uwielbia spędzać swój wolny czas na oglądaniu nowych filmów i tych które już widzieliśmy setki razy :)
									<br /><br />
									<h3>Główny cel</h3>
									Strona została stworzona aby każdy mógł bez ograniczej, jakich kolwiek wysiłków, bez reklam z naszej strony oglądać filmy online.
									Dokładamy cały czas wszystkich starań do tego aby nasi użytkownicy byli zadowoleni.<br />
									Wszystkie filmy które chciałbyś obejrzeć możesz znaleść na tej stronie. <br/>
									Każdego dnia pszysyłacie nam mnóstwo linków, które dodajemy do naszej bazy danych, za które dziękujemy :).
									Dzięki wam nasza strona jest na bierząco z wszystkimi nowościami, które pojawiają się.
									<br /> <br /> 
									<h3>Prośba</h3>
									Jedyne o co prosimy to wsparcie naszej strony.<br /> 
									Pomocy można udzielać na kilka sposobów:<br /> 
									1. Korzystać z naszej strony!<br /> 
									2. Reklamować i promować naszą stronę aby stawała się coraz bardziej popularna <br /> 
									3. Mówienie o niej znajomym, dzielić się z nimi linkami do filmów<br /> 
									4. Kupywanie kont premium, które umożliwi nam wykupienie playerów bez limitów! <br /> 
									5. Dodawaniu nowych filmów, linków<br /> 
									6. Przekazywanie darmowych datków na utrzymanie serwera.<br /> 
									<p></p> 
								</div>
							</div>
						</li>
");

if($loggedInUser)
{

	$result = getUserId($mysqli, $loggedInUser);
	
echo("
						</ul>
						
					</article>
					
				<article class=\"grid_8\">
");
}
else{

	echo("
						</ul>
						
					<div id=\"wyniki\"></div>
					</article>
					
				<article class=\"grid_8\">
				
	");
}

shwoPanelUser($loggedInUser, $ip);

echo("				
              	</article>
            </div>
        </div>
 
");
showFooter($ip);