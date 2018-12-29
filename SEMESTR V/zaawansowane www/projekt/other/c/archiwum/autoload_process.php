<?php
include("../include/config.php"); //include config file
include("../account/models/config.php");
$results = $mysqli->query("SELECT COUNT(*) as t_records FROM movie");
	if($results){
	$total_records = $results->fetch_object();
	$total_groups = ceil($total_records->t_records/$items_per_group);
	$results->close(); 
	}
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
	
	
	$result = $mysqli->query("SELECT user_id FROM `userpie_users` WHERE `username` LIKE '".$loggedInUser->display_username."' Limit 0, 1 "); 
	while($row = $result->fetch_assoc()){
		$id_user = $row['user_id'];
		$zapytanie = "SELECT `id_film`,`date` FROM `moved` WHERE `id_us` = $id_user ORDER BY date DESC LIMIT $position, $items_per_group";
	}
	
	$resulta = @$mysqli->query($zapytanie);
	while($rowa = $resulta->fetch_assoc()){
	
	$results = $mysqli->query("SELECT `id`, `photo`, `name`, `description`, `premier`, `type` FROM `movie` WHERE id = ".$rowa['id_film']." LIMIT 0, 1");
		
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
										<p>$obj->type <br />premiera: $obj->premier | obejrzane: ".$rowa['date']."</p> 
										".substr("$obj->description", 0, 300)."<br/>
										<a href=\"$ip/watch/?$obj->id\" class=\"watch_but1\">WATCH NOW</a> 
									</div>
								</div>
							</li>
	");
			}
		
		}
	
	}
	unset($obj);
	$mysqli->close();
}
?>