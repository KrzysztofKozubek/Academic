<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-07
 * Time: 02:06
 */


require_once('../config/sql.php');
$mysql = connectWithDataBase($host, $user, $pass, $dbase);

function connectWithDataBase($host, $login, $password, $db){
    $mysql = new mysqli($host, $login, $password, $db);
    $mysql->set_charset("utf8");

    $db = $mysql->connect_error;
    if ($db) {
        die('Connect Error ('.$mysql->connect_errno.') '. $mysql->connect_error);
    }else{
        return $mysql;
    }
}

function executeQuery($sql,$pdo){
    $pdo->query($sql);
}

function resultQuestion($sql,$pdo){
    $pdo->query('SET NAMES utf8');
    $select = $pdo->query($sql);
    $data = null;
    foreach ($select as $row) {
        $data[] = $row;
    }
    return $data;
}

$sql    = "SELECT Year y, Category c, Nominee n, `Additional Info` a, `Won?` w FROM `awards` WHERE Nominee > 0";
$awards = resultQuestion($sql, $mysql);

foreach($awards as $award){
    if(strpos($award['c'],'Actor -- ') !== false || strpos($award['c'],'Actress --') !== false){
        $award['c'] = str_replace("Actor --", "", $award['c']);
        $award['c'] = str_replace("Actress --", "", $award['c']);
        $sql = "INSERT INTO `award`(`idMovie`, `idPerson`, `description`, `date`, `info`, `won`) VALUES (NULL, \"$award[n]\", \"$award[c]\", \"$award[y]\", \"$award[a]\", \"$award[w]\")";
        executeQuery($sql, $mysql);
    }else{
        $sql = "INSERT INTO `award`(`idMovie`, `idPerson`, `description`, `date`, `info`, `won`) VALUES (\"$award[n]\", NULL, \"$award[c]\", \"$award[y]\", \"$award[a]\", \"$award[w]\")";
        executeQuery($sql, $mysql);
    }
}

//UPDATE `awards` a , movie m  SET `Nominee`= m.id WHERE m.title = a.Nominee
//UPDATE `awards` a , person m SET `Nominee`= m.id WHERE m.name = a.Nominee
//UPDATE `awards` a , person m SET `Nominee`= m.id WHERE m.name LIKE '%a.Nominee%'
//DELETE FROM `awards` WHERE `Nominee` > 0

//UPDATE `awards` a , person m SET `Nominee`= m.id WHERE m.name LIKE concat("%",a.Nominee, "%")