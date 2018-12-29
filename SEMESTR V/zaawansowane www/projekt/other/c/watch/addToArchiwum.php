
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
$iv = $_POST['view'];
$id = $_POST['message'];

	$zapyta = "UPDATE movie
	SET views=$iv
	WHERE id=$id";
	
	echo $zapyta;
	$instert = $mysqli->query($zapyta);


?>