<!--
<video height="160" width="280" controls="controls">
   <source src="http://le020a.cda.pl/pobierz/video/3c3701ed7fbc597b0372ec8a57f09911.mp4" type="video/mp4">
Twoja przeglądarka nie obsługuje wideo.
</video>--->
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


$result = $mysqli->query("SELECT *  FROM `movie` WHERE `id` = $id Limit 0, 1 "); 
if($row = $result->fetch_assoc()){
	showHead($row['name'], $row['description'], $ip); 


	//showHeadDeffoult($ip); 
	showScrips($total_groups);
	showSignInOut($loggedInUser, $ip);

	echo '
	<div id="tab-container-1">

		
		';
		$iv = $row['views'] + 1;
		@$resultUser = getUserId($mysqli, $loggedInUser);
		$i = 0;$p = 0;$z = 0;
		$quer = "SELECT link, version FROM `link` WHERE `id_film` = ".$id." ORDER BY version, id ASC ";
		$resul = $mysqli->query($quer);
		while($ro = $resul->fetch_assoc()){
		
			$i++;
			$res = getDomen($ro['link']);
			if( $ro['version'] == 0 && $z == 0){ echo '<ul id="tab-container-1-nav"><div id=opis>Nieznany typ:</div>'; $p = $ro['version'];$z++;}
			if( $ro['version'] == 1 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>Orginał:</div>'; $p = $ro['version'];}
			if( $ro['version'] == 2 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>Napisy:</div>'; $p = $ro['version'];}
			if( $ro['version'] == 3 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>Lektor:&nbsp;</div>'; $p = $ro['version'];}
			if( $ro['version'] == 4 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>Dubbing:</div>'; $p = $ro['version'];}
			if( $ro['version'] == 5 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>HD Orginał:</div>'; $p = $ro['version'];}
			if( $ro['version'] == 6 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>HD Napisy:</div>'; $p = $ro['version'];}
			if( $ro['version'] == 7 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>HD Lektor:</div>'; $p = $ro['version'];}
			if( $ro['version'] == 8 && $ro['version'] != $p){ echo '</ul><ul id="tab-container-1-nav"><div id=opis>HD Dubbing:</div>'; $p = $ro['version'];}
			echo "
				<li><a href=\"#".$res."\"  onClick=\"wczytaj('".$resultUser."', '".$id."', '".$i."', '".$iv."')\" >".$res."</a></li>";
		}
		
		
echo "
						</ul>
						<div id=\"watch\"></div>
						
						
";

	

//showPlayer($mysqli, $id);

	showStartContent();
						
	echo <<< SKRYPT

					<h2>Opis Filmu:</h2>
						<ul class="latest_movies">
						
SKRYPT;




	echo("
							<li>
								<div class=\"extra_container latest\">
									<figure><img src=\"$row[photo]\" width=\"160\" height=\"237\" alt=\"\"></figure>
									<div>
										<h3>$row[name]</h3>
										<p>$row[type]</p> 
										$row[description]<br/><br/>
										premiera: $row[premier] <br/>
										Czas filmu: $row[time] min. <br/>
										Odsłon: $row[views] <br/>
										Ocena: $row[rating] 
									</div>
								</div>
							</li>
							</ul>
							<div id=\"wyniki\"></div>
							</article>
							
							<article class=\"grid_8\">
	");
}
	echo("

	<script type=\"text/javascript\">
	
		function Zap(adres){

      if(xmlHttp==null){ //w zależności od przeglądarki tworzymy obiekt XMLHTTP
         if(window.ActiveXObject)xmlHttp = new ActiveXObject(\"Microsoft.XMLHTTP\"); //dla IE
	 else if(window.XMLHttpRequest)xmlHttp = new XMLHttpRequest();  //Firefox, Opera, Safari itp.
      }
      if (xmlHttp == null){alert(\"Nie udało się zainicjować obiektu xmlHttpRequest!\");return;} //jeśli obiekt nie został utworzony, zwracamy, błąd a skrypt zostaje przerwany
      
      xmlHttp.onreadystatechange = function(){ //funkcja ma za zadanie wyświetlić wyniki zwrócone przez serwer
         if (xmlHttp.readyState == 4 || xmlHttp.status == 200) //sprawdzamy czy udało się pobrać zawartość strony (readyState=4) lub czy serwer nie zwrócił błędu(status=200 oznacza że jest OK)
         document.getElementById(\"watch\").innerHTML = xmlHttp.responseText; //zwrócony tekst zapisujemy do warstwy 

      };   
      xmlHttp.open(\"GET\", adres); //ustawiamy metodę i adres żądania
      xmlHttp.send(null); //wysyłamy żądanie
}
	
		function wczytaj(iusername, imessage, il, iview) {
				
				post_data = {'username':iusername, 'message':imessage, 'view':iview };
			 	Zap(\"delete.php?id=\"+imessage+\"&l=\"+il+\"+&v=\"+iview+\"\");
				if(iusername){
				//send data to \"addToArchiwum.php\" using jQuery $.post()
				$.post('addToArchiwum.php', post_data, function(data) {	
				}).fail(function(err) { 
				});
				}
	};
</script>

");


shwoPanelUser($loggedInUser, $ip);
showFormSearch($mysqli, $ip);

echo("				
              	</article>
            </div>
        </div>
    </section>
    <div class=\"container_24\">
    	<div class=\"wrapper\">
            <div class=\"grid_24\">
                <div class=\"links_box\">
                    <ul>
                        <li><a href=\"#\">HOME</a></li>                 	
                    </ul>
                    <ul>
                        <li><a href=\"#\">HOME</a></li>                  	
                    </ul>
                    <ul>
                    	<li><a href=\"#\">HOME</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
	<!--==============================footer=================================-->
    <div class=\"container_24\">
    	<div class=\"wrapper\">
        	<div class=\"grid_24\">
          		 <footer>
                 	Watch Online &nbsp;© 2012 &nbsp;&nbsp;&nbsp;&nbsp;  <a href=\"#\">Privacy Policy</a><br>
                    <!-- {%FOOTER_LINK} -->
                </footer>  
            </div>
        </div>
    </div>
</div>
");
?>
<a href="#" title="Do góry!" id="scroll-to-top"></a>

 
<button title="Na Dół!" id="scroll-to-bottom" class="scroll-to-bottom"><a href="#" title="Na Dół!" id="scroll-to-bottom"></a></button>

 
 <script>
 $(function(){
    var stt_is_shown = false;
    $(window).scroll(function(){
       var win_height = 300;
       var scroll_top = $(document).scrollTop(); 
       if (scroll_top > win_height && !stt_is_shown) {
          stt_is_shown = true;
          $("#scroll-to-top").fadeIn();
       } else if (scroll_top < win_height && stt_is_shown) {
          stt_is_shown = false;
          $("#scroll-to-top").fadeOut();
       }
   });
   $("#scroll-to-top").click(function(e){
      e.preventDefault();
      $('html, body').animate({scrollTop:200},250);
    });
 });
 </script>
</body>
</html>
