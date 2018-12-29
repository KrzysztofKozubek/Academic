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
<html lang="pl">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <meta name="theme-color" content="#2196F3">
    <title>Baza Filmów by Krzysztof Kozubek</title><!-- CSS-->
    <link href="<?php echo $ip; ?>/static/css/plugin-min.css" type="text/css" rel="stylesheet">
    <link href="<?php echo $ip; ?>/static/css/custom-min.css" type="text/css" rel="stylesheet">
</head>
<body><!--Pre Loader-->
<div id="loader-wrapper">
    <div id="loader"></div>
    <div class="loader-section section-left"></div>
    <div class="loader-section section-right"></div>
</div><!--Navigation-->
<header>
    <div class="navbar-fixed">
        <nav id="nav_f" role="navigation" class="default_color">
            <section class="container">
                <dt class="nav-wrapper"><a id="logo-container" href="<?php echo $ip; ?>" class="brand-logo">Baza Filmów</a>
                <ul class="right hide-on-med-and-down">
                    <li><a href="#contact">Kontakt</a></li>
                    <li><a href="<?php echo $ip; ?>">Filmy</a></li>
                    <li>
                        <form method="GET" action="<?php echo $ip; ?>">
                            <ul>
                                <li><label for="search"><i class="material-icons">search</i></label></li>
                                <li><input id="search" name="search" type="search" required=""></li>
                            </ul>
                        </form>
                    </li>
                </ul>
                <ul id="nav-mobile" class="side-nav">
                    <li><a href="<?php echo $ip; ?>">Strona główna</a></li>
                    <li><a href="<?php echo $ip; ?>">Filmy</a></li>
                    <li><a href="#contact">Kontakt</a></li>
                    <li>
                        <form method="post" action="/movies/">
                            <ul>
                                <li><label for="search"><i class="material-icons">search</i></label></li>
                                <li><input id="search" name="search" type="search" required=""></li>
                            </ul>
                        </form>
                    </li>
                </ul>
                <a href="#" data-activates="nav-mobile" class="button-collapse"><i
                        class="mdi-navigation-menu"></i></a></dt></section>
        </nav>
    </div>
</header><!-- Informaion-->