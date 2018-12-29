<?php

$ip = 'http://'.$_SERVER ["SERVER_NAME"];
require_once("config.php");

echo("
<!DOCTYPE html>
<html lang=\"en\">
<head>
    <title>FILMY ONLINE</title>
    <meta charset=\"utf-8\">
    <link rel=\"icon\" href=\"$ip/images/favicon.ico\" type=\"image/x-icon\">
	<link rel=\"shortcut icon\" href=\"$ip/images/favicon.ico\" type=\"image/x-icon\" />
    <link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"$ip/css/style.css\">
    <link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"$ip/css/carousel.css\">
	<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"$ip/css/correct.css\">
	<script type=\"text/javascript\" src=\"$ip/js/jquery-1.9.0.min.js\"></script>
    <script type=\"text/javascript\" src=\"$ip/js/jquery-1.6.1.min.js\"></script>
    <script type=\"text/javascript\" src=\"$ip/js/superfish.js\"></script>
	<script type=\"text/javascript\" src=\"$ip/js/jquery.min.js\"></script>
    <script type=\"text/javascript\" src=\"$ip/js/jcarousellite.js\"></script>
    <script type=\"text/javascript\" src=\"$ip/js/gallery.js\"></script>
	<script type='text/javascript' src=\"scripts/gen_validatorv31.js\"></script>
	<!--[if lt IE 8]>
   <div style=' clear: both; text-align:center; position: relative;'>
     <a href=\"http://windows.microsoft.com/en-US/internet-explorer/products/ie/home?ocid=ie6_countdown_bannercode\">
       <img src=\"http://storage.ie6countdown.com/assets/100/images/banners/warning_bar_0000_us.jpg\" border=\"0\" height=\"42\" width=\"820\" alt=\"You are using an outdated browser. For a faster, safer browsing experience, upgrade for free today.\" />
    </a>
  </div>
<![endif]-->
    <!--[if lt IE 9]>
   		<script type=\"text/javascript\" src=\"$ip/js/html5.js\"></script>
    	<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"$ip/css/ie.css\">
	<![endif]-->
");
?>