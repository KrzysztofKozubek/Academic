<?php
$db_user 	= 'root';
$db_pass 	= 'root';
$db_name 	= 'u752771722_movie';
$db_host 	= '127.0.0.1';
/*
$db_user 	= 'u752771722_root';
$db_pass 	= 'koperta123';
$db_name 	= 'u752771722_movie';
$db_host 	= 'localhost';

	$db_host = "mysql1.ph-hos.osemka.pl";
	$db_user = "1410052578_f";
	$db_pass = "koperta123";
	$db_name = "1275327_qwe";
*/
//$db_username 	= 'zamulacztv';
//$db_password 	= 'koperta123';
//$db_name 		= 'zamulacztv_cba_pl';
//$db_host 		= 'mysql.cba.pl';

$items_per_group = 6;
$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name);
$mysqli->set_charset("utf8");
?>