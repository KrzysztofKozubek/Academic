<?php
require_once("../include/config.php");

$ip = 'http://'.$_SERVER ["SERVER_NAME"];
require_once("../createQuestion.php");
$zapytanie = createQuestion(@$_GET["tekst"], @$_GET["type"], @$_GET["fraza"], @$_GET["date1"], @$_GET["date2"], @$_GET["ocena1"], @$_GET["ocena2"], @$_GET["time1"], @$_GET["time2"]);
if($zapytanie){

	$i=0;
	echo "
	<div class=\"top5\">
	<section id=\"content\" class=\"cont_pad\">
			<div class=\"container_24\">
				<div class=\"wrapper\">
					<article class=\"grid_16\">
						<a name=\"search\"></a>
						<h2><center>Wyniki wyszukiwania!</center></h2>
						<ul class=\"latest_movies\">
	";

	$result = $mysqli->query($zapytanie);
	while($obj = $result->fetch_assoc()){

	   echo "
			
							<li>
								<div class=\"extra_container latest\">
									<figure><img src=\"$obj[photo]\" width=\"160\" height=\"237\" alt=\"\"></figure>
									<div>
										<h3>$obj[name]</h3>
										<p>$obj[premier] | $obj[time] min. | $obj[rating] <br/>
										$obj[type] </p> 
										".substr("$obj[description]", 0, 300)."<br/>
										<a href=\"$ip/watch/?$obj[id]#watch\" class=\"watch_but1\">WATCH NOW</a> 
									</div>
								</div>
							</li>
			
		";
		
	   $i++;   
	}
	echo "
			<div class=\"but_wrap\">
				<div class=\"but_wrap2\"> Brak więcej wyników wyszukiwania! <br /> &#8665; PRZEGLĄDANE FILMY &#8664;</div>
			</div>
						</ul>
						</div>
					
	";
	echo "<input type=hidden id=ile_wynikow value='$i'>";
}

?>
