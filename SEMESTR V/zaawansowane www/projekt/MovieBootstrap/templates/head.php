<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 21:12
 */

require 'ip.php';
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="<?php echo "$ip"; ?>/static/image/favicon.ico">

    <title>ZamulaczTV</title>

    <!-- Bootstrap core CSS -->
    <link href="<?php echo "$ip"; ?>/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="<?php echo "$ip"; ?>/static/css/offcanvas.css" rel="stylesheet">
    <link href="<?php echo "$ip"; ?>/static/css/bootstrap-select.css" rel="stylesheet">


</head>
<body>
<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Szukaj Filmu</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<?php echo $ip; ?>">Baza Filmów</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="<?php echo $ip; ?>">Filmy</a></li>
                <li><a href="<?php echo $ip; ?>/?person&&site=1&&search=">Aktorzy</a></li>
                <li><a href="<?php echo $ip; ?>/contact/">Kontakt</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right text-right">

                <?php
                if(isset($_SESSION['id'])){
                    echo "<li><a href=\"$ip/user\">Witaj $_SESSION[name]!</a></li>
                          <li><a href=\"$ip/user/?sign\">Wyloguj</a></li>";
                }else{
                    echo "<li><a href=\"$ip/user\">Logowanie</a></li>
                          <li><a href=\"$ip/user\">Rejestracja</a></li>";
                }
                ?>

            </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</nav><!-- /.navbar -->