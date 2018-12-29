<?php
include("../include/config.php"); //include config file
$ip = 'http://'.$_SERVER ["SERVER_NAME"];
$address = "$_SERVER[REQUEST_URI]";
$type = strchr($address, "?");
$type = substr($type, 3);

if($_POST)
{
	//sanitize post value
	$group_number = filter_var($_POST["group_no"], FILTER_SANITIZE_NUMBER_INT, FILTER_FLAG_STRIP_HIGH);
	
	//throw HTTP error if group number is not valid
	if(!is_numeric($group_number)){
		header('HTTP/1.1 500 Invalid number!');
		exit();
	}
	
	//get current starting point of records
	$position = ($group_number * $items_per_group);
	
	//Limit our results within a specified range.
	require_once("../createQuestion.php");
	@$year = date(Y)-5;
	$zap = "SELECT * FROM `movie` WHERE `premier` >= '$year-".date('m-d')."' AND rating >= 7 AND type NOT LIKE '%Dokument%' ORDER BY `rating` DESC LIMIT $position, $items_per_group";

	$results = $mysqli->query($zap);
	
	if ($results) { 
		//output results from database
		
		while($obj = $results->fetch_object())
		{
			echo("
						<li>
                        	<div class=\"extra_container latest\">
								<figure><img src=\"$obj->photo\" width=\"160\" height=\"237\" alt=\"\"></figure>
								<div>
									<h3>$obj->name</h3>
									<p>$obj->premier | $obj->time min. | $obj->rating <br/>
										$obj->type </p> 
									".substr("$obj->description", 0, 300)."<br/>
									<a href=\"$ip/watch/?$obj->id\" class=\"watch_but1\">WATCH NOW</a> 
								</div>
							</div>
						</li>
");
		}
	
	}
	unset($obj);
	$mysqli->close();
}
?>