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
echo "<meta http-equiv=\"refresh\" content=\"1; URL=$urlNextSite?NumberLicencePlate=$NumberLicencePlateNext\">";
?>
    <head></head>
    <body>
<?php

//what will be download
$NLP               = $NumberLicencePlate;
$photoNLPFormatPNG = "";


//functions basic
function getmicrotime(){
    $microtime = explode(' ', microtime());
    return $microtime[1] . substr($microtime[0], 1);
}

function downloadSite($url) {
    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, "$url");
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($curl, CURLOPT_TIMEOUT, 15);
    $result = curl_exec($curl);
    curl_close($curl);
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

function downloadSiteFile($url, $port){
    $ch = curl_init();
    echo "URL = $url <br>n";
    curl_setopt($ch, CURLOPT_VERBOSE, 1);
    //curl_setopt ($ch, CURLOPT_HTTPPROXYTUNNEL, TRUE);
    curl_setopt ($ch, CURLOPT_PROXYTYPE, CURLPROXY_HTTP);
    curl_setopt ($ch, CURLOPT_PROXY,'http://proksiak.pl/:$port');
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

function downloadSiteNLP($nlp){
    $url = 'http://platesmania.com/pl/informer';
    //KR = 22377 && KRA = 22378
    $data = array('ctype' => '1', 'posted' => '1', 'region' => '22377', 'b1' => '', 'nomerpl' => "$nlp", 'fon' => '1');

// use key 'http' even if you send the request to https://...
    $options = array(
        'http' => array(
            'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
            'method'  => 'POST',
            'content' => http_build_query($data),
        ),
    );
    $context  = stream_context_create($options);
    $result = file_get_contents($url, false, $context);
    if ($result === FALSE) { echo "ERROR";/* Handle error */ }

    //var_dump($result);
    echo $result;
    //return $result;
}

function getURLPhotoNLP($html){
    $expresion = '@<img src="([^<]+?)"></a>@u';
    preg_match_all($expresion, $html, $result);
    return $result[1][0];
}


//functions basic END

//functions tablica-rejestracyjna.pl
function downloads($url){
    @$content = file_get_contents("http://proksiak.pl/show.php/40ARm_2FI_2BRwUaN1Dc3_2FnEiVQZt4rcrSlzeTvUGhIz0BhDwe8ht9l45HUW2LY_2BITKIv3zIKjs_3D/b61/fnorefer/");
    print_r( $content);
}

function downloadPhotoNLP($url, $NLP){
    @$content = file_get_contents($url, true);
    if($content === false)
        return false;
    $fp = fopen("NLPP/KR$NLP.png", "w");
    @fwrite($fp, $content);
    @fclose($fp);
}

//CREATE NLP
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
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];

    return implode('',$tab);
}
function getTableNLNNN($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableNLLNN($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}
function getTableLLNNN($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}

function getTableNNNNN($tableN, $tableL){

    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];

    return implode('',$tab);
}

function getTableLNNN($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
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
function getTableLNNL($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];

    return implode('',$tab);
}
function getTableLNLL($tableN, $tableL){

    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableN[rand(0, 9)];
    $tab[] = $tableL[rand(1, 24)];
    $tab[] = $tableL[rand(1, 24)];

    return implode('',$tab);
}
//CREATE NLP END

function anagramy($wyraz) {
    $tmp = null;
    for ($i=0;$i<strlen($wyraz);$i++) {
        $znak=$wyraz[$i];
        $ile=count($tmp);
        if ($ile==0) $tmp[]=$znak;
        else {
            for($k=0;$k<$ile;$k++) {
                $ciag=$tmp[$k];
                for($j=0;$j<=strlen($ciag);$j++) {
                    $new[]= substr($ciag,0,$j).$znak.substr($ciag,$j);
                }
            }
            $tmp=$new;
            $new="";
        }
    }
    return $tmp;
}

function generationNLPPolish(){
    $result = "";

    $tableN = array( 0 => '0', 1 => '1', 2 => '2', 3 => '3', 4 => '4', 5 => '5', 6 => '6', 7 => '7', 8 => '8', 9 => '9');
    $tableL = array( 1 => 'A', 1 => 'B', 2 => 'C', 3 => 'D', 4 => 'E', 5 => 'F', 6 => 'G', 7 => 'H', 8 => 'I', 9 => 'J', 10 => 'K', 11 => 'L', 12 => 'M', 13 => 'N', 14 => 'O', 15 => 'P', 16 => 'R', 17 => 'S', 18 => 'T', 19 => 'U', 20 => 'V', 21 => 'W', 22 => 'X', 23 => 'Y', 24 => 'Z');

    $typNLP = rand(1, 5); // example KR LU WR
    //$typNLP = rand(1, 13); // example KRA SZY
    //for NLP begin from two letters
    if($typNLP == 1) $result .= getTableNLLNN($tableN, $tableL);
    if($typNLP == 2) $result .= getTableNNNNL($tableN, $tableL);
    if($typNLP == 3) $result .= getTableNNNLL($tableN, $tableL);
    if($typNLP == 4) $result .= getTableNLNNN($tableN, $tableL);

    //for NLP begin from three letters
    if($typNLP == 5) $result .= getTableNNNNN($tableN, $tableL);

    if($typNLP == 6) $result .= getTableLNNN($tableN, $tableL);
    if($typNLP == 7) $result .= getTableNNLL($tableN, $tableL);
    if($typNLP == 8) $result .= getTableNLNN($tableN, $tableL);
    if($typNLP == 9) $result .= getTableNNLN($tableN, $tableL);
    if($typNLP == 10) $result .= getTableNLLN($tableN, $tableL);
    if($typNLP == 11) $result .= getTableLLNN($tableN, $tableL);
    if($typNLP == 12) $result .= getTableLNNL($tableN, $tableL);
    if($typNLP == 13) $result .= getTableLNLL($tableN, $tableL);

    return $result;
}



//function tablica-rejestracyjna.pl END


    $nlp = generationNLPPolish();
    //echo $nlp . "<BR>";
    $tnlp = anagramy($nlp);

    for($j = 0; $j < sizeof($tnlp); $j++){
        if(strlen($nlp) <= strlen($tnlp[$j])){
            $urlPhoto = getURLPhotoNLP(downloadSiteNLP($tnlp[$j]));
            downloadPhotoNLP($urlPhoto, $tnlp[$j]);
        }
    }

    $urlPhoto = getURLPhotoNLP(downloadSiteNLP($nlp));
    //echo $urlPhoto . "<BR>";
    downloadPhotoNLP($urlPhoto, $nlp);
















?>




























