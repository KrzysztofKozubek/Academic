<?php
session_start();
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-03
 * Time: 23:49
 */
$ip = "http://".$_SERVER ["SERVER_NAME"];

if(isset($_SESSION['id'])){
    if(isset($_GET['sign'])){
        session_destroy();
        header("Location: $ip");
    }

    include_once 'controller/user.php';

    $ob = new UserController();

    if(isset($_GET['add'])){
        $ob->addFriend($_SESSION['id'], $_GET['id']);
    }else{
        if(isset($_GET['remove'])){
            $ob->removeFriend($_SESSION['id'], $_GET['id']);
        }else{
            if(!isset($_GET['id']))
                $ob->index($_SESSION['id'], $_SESSION['id']);
            else
                $ob->index($_SESSION['id'], $_GET['id']);
        }

    }
}else{
    if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['age']) && isset($_POST['sex']) && isset($_POST['password'])){

        include_once 'controller/sign.php';

        $ob = new SignController();

        $ob->registion($_POST['name'], $_POST['email'], $_POST['age'], $_POST['sex'], $_POST['password']);

    }else {

        if(isset($_POST['password'])  && isset($_POST['email'])) {

            include_once 'controller/sign.php';

            $ob = new SignController();

            $ob->login($_POST['email'], $_POST['password']);

        }else {

            include_once 'controller/sign.php';

            $ob = new SignController();

            $ob->sign();

        }
    }

    if(isset($_SESSION['id']))
        header("Location: $ip");
}