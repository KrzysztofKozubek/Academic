<?php
require_once("../include/config.php");

function showIframe($address){

	if(strstr($address, "hd3d")){
	
		echo "
		<div class=\"tab\" id=\"hd3d\">
			<iframe src=\"".$address."\"  style=\"width:100%;height:500px;border:0px;\" scrolling=\"no\" >	</iframe>
		</div>
		";
	}
	if(strstr($address, "novamov")){
	
		echo "
		<div class=\"tab\" id=\"novamov\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:600px; height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "divxstage")){
	
		echo "
		<div class=\"tab\" id=\"divxstage\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "tinymov")){
	
		echo "
		<div class=\"tab\" id=\"tinymov\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "firedrive")){
	
		echo "
		<div class=\"tab\" id=\"firedrive\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}

	if(strstr($address, "videoweed")){
	
		echo "
		<div class=\"tab\" id=\"videoweed\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	
	if(strstr($address, "putlocker")){
	
		echo "
		<div class=\"tab\" id=\"putlocker\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	
	if(strstr($address, "vshare")){
	
		echo "
		<div class=\"tab\" id=\"vshare\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "nowvideo")){
	
		echo "
		<div class=\"tab\" id=\"nowvideo\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "dwn.so")){
	
		echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "maxupload")){
	
		echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "vidzer.net")){
	
		echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
	}
	if(strstr($address, "played.to")){
	
		$address = substr($address, strpos($address, "to/") + 3);
		$address = "http://played.to/embed-".$address."-940x500.html";
		//echo $address;
		echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"".$address."\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" FRAMEBORDER=0 MARGINWIDTH=0 MARGINHEIGHT=0 SCROLLING=NO WIDTH=850 HEIGHT=500>	</iframe></center>
		</div>
		";
	}
}
$id = $_GET['id'];
$l = $_GET['l'];
$l -=1;
$que = "SELECT link FROM `link` WHERE `id_film` = $id  ORDER BY version, id ASC LIMIT $l, 1";

	$res = $mysqli->query($que);
	while($r = $res->fetch_assoc()){

		showIframe($r['link']);
	}






?>