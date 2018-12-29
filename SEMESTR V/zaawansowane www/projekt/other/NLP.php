<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2016-03-15
 * Time: 13:28
 */

$ip                 = "http://".$_SERVER ["SERVER_NAME"];
$urlNextSite        = $ip . $_SERVER ["PHP_SELF"];


//create URL site
if(empty($_GET['NumberLicencePlate'])){
    //permutation number licence plate
    $NumberLicencePlate     = generationNLPPolish();
    $NumberLicencePlateNext = generationNLPPolish();
}
else {
    //permutation number licence plate
    $NumberLicencePlate     = $_GET['NumberLicencePlate'];
    $NumberLicencePlateNext = generationNLPPolish();
}

?>
    <!DOCTYPE html>
    <html>
    <meta charset="UTF-8">
<?php
echo "<meta http-equiv=\"refresh\" content=\"100000; URL=$urlNextSite?NumberLicencePlate=$NumberLicencePlateNext\">";
?>
    <head></head>
    <body>
<?php

//what will be download
$NLP               = $NumberLicencePlate;
$photoNLPFormatPNG = "";

$url = "http://zamulacztv.cba.pl/";
//functions basic
function getmicrotime(){
    $microtime = explode(' ', microtime());
    return $microtime[1] . substr($microtime[0], 1);
}

function downloadSite($url) {
//    $curl = curl_init();
////    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
////    curl_setopt($curl, CURLOPT_TIMEOUT, 15);
//    curl_setopt($curl, CURLOPT_HTTPPROXYTUNNEL, 1);
//    curl_setopt($curl, CURLOPT_PROXYTYPE, "CURLPROXY_HTTP");
//    curl_setopt($curl, CURLOPT_PROXYAUTH, "CURLAUTH_BASIC");
//    curl_setopt($curl, CURLOPT_URL, "$url");
//    $result = curl_exec($curl);
//    curl_close($curl);
//
//    if (is_int($result)) {
//        die ("cURL error:".curl_error($curl)."\n");
//    }
//    return $result;


    $ch = curl_init();
    echo "URL = $url <br>n";
    curl_setopt($ch, CURLOPT_VERBOSE, 1);
    //curl_setopt ($ch, CURLOPT_HTTPPROXYTUNNEL, TRUE);
    curl_setopt ($ch, CURLOPT_PROXYTYPE, CURLPROXY_HTTP);
    curl_setopt ($ch, CURLOPT_PROXY,'http://213.136.79.122:80');
    curl_setopt ($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
    curl_setopt ($ch, CURLOPT_URL, $url);
    curl_setopt ($ch, CURLOPT_TIMEOUT, 120);
    $result = curl_exec ($ch);
    echo "<hr><br>n";
    echo "Errors: " . curl_errno($ch) . " " . curl_error($ch) . "<br><br>";
    echo "<hr><br>n";
    curl_close ($ch);
    print "result - $result";
    echo "<hr><br>n";

    return $result;
}

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

function downloadSiteFile($url){
    
}
//functions basic END

//functions tablica-rejestracyjna.pl

function downloadPhotoNLP($pathPhotoToSave, $NLP){
    @$content = file_get_contents("http://tablica-rejestracyjna.pl/images/tablice/$NLP.png");
    if($content === false)
        return false;
    $fp = fopen("NLP/$NLP.png", "w");
    fwrite($fp, $content);
    fclose($fp);
}

//CREATE NLP
function getTableNNNNN($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableNNNNL($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];

    return implode('',$tab);
}
function getTableNNNLL($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];

    return implode('',$tab);
}
function getTableLNNNNN($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableLLNNNN($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}

function getTableLNNN($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableNNLL($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];

    return implode('',$tab);
}
function getTableNLNN($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableNNLN($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableNLLN($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableLLNN($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
//CREATE NLP END



function generationNLPPolish(){
    $result = "KRA";

    $tableN = array( 0 => '0', 1 => '1', 2 => '2', 3 => '3', 4 => '4', 5 => '5', 6 => '6', 7 => '7', 8 => '8', 9 => '9');
    $tableL = array( 1 => 'A', 1 => 'B', 2 => 'C', 3 => 'D', 4 => 'E', 5 => 'F', 6 => 'G', 7 => 'H', 8 => 'I', 9 => 'J', 10 => 'K', 11 => 'L', 12 => 'M', 13 => 'N', 14 => 'O', 15 => 'P', 16 => 'R', 17 => 'S', 18 => 'T', 19 => 'U', 20 => 'V', 21 => 'W', 22 => 'X', 23 => 'Y', 24 => 'Z');

    $typNLP = rand(6, 11);
    //for NLP begin from two letters
    if($typNLP == 1) $result .= getTableNNNNN($tableN, $tableL);
    if($typNLP == 2) $result .= getTableNNNNL($tableN, $tableL);
    if($typNLP == 3) $result .= getTableNNNLL($tableN, $tableL);
    if($typNLP == 4) $result .= getTableLNNNNN($tableN, $tableL);
    if($typNLP == 5) $result .= getTableLLNNNN($tableN, $tableL);

    //for NLP begin from three letters
    if($typNLP == 6) $result .= getTableLNNN($tableN, $tableL);
    if($typNLP == 7) $result .= getTableNNLL($tableN, $tableL);
    if($typNLP == 8) $result .= getTableNLNN($tableN, $tableL);
    if($typNLP == 9) $result .= getTableNNLN($tableN, $tableL);
    if($typNLP == 10) $result .= getTableNLLN($tableN, $tableL);
    if($typNLP == 11) $result .= getTableLLNN($tableN, $tableL);

    return $result;
}



//function tablica-rejestracyjna.pl END


//main program
print_r(downloadSite($url));
    /*
     * http://darmowe-proxy.pl/browse.php
     * /OsZACGux/OAAuMdKx/1eE9O71O/9jyrIviz/nbSRrMbV/OIiR_2F5/4_3D/b7/fnorefer
     * KRA61KA
     */























