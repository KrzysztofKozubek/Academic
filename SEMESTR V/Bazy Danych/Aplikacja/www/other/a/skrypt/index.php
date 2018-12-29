<?php
$ip                 = "http://".$_SERVER ["SERVER_NAME"];
$urlNextSite        = $ip . $_SERVER ["PHP_SELF"];

if(empty($_GET['site']) || empty($_GET['movie'])){
    $siteURL        = 1;
    $movieURL       = 1;
}
else {
    $siteURL        = $_GET['site'];
    $movieURL       = $_GET['movie'];
}

if($movieURL >= 10){
    $siteURLNext    = $siteURL + 1;
    $movieURLNext   = 1;
}else{
    $siteURLNext    = $siteURL;
    $movieURLNext   = $movieURL + 1;
}

?>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<?php
echo "<meta http-equiv=\"refresh\" content=\"1; URL=$urlNextSite?site=$siteURLNext&&movie=$movieURLNext\">";
?>
<head></head>
<body>
<?php

//value database
$host       = "localhost";
$login      = "root";
$password   = "";
$db         = "film";
$mysql      = connectWithDataBase($host, $login, $password, $db);
//END value database

//value download site
$url        = "http://www.filmweb.pl/search/film?q=&type=&startYear=&endYear=&countryIds=&genreIds=&startRate=&endRate=&startCount=&endCount=&sort=COUNT&sortAscending=false&c=portal&page=$siteURL";
//$html       = downloadSite($url);
//END value download site

//value movie
$title          = "";
$premier        = "";
$description    = "";
$duration       = "";
$director       = "";
$scenarist      = "";
$genre          = "";
$photo          = "";
//end value movie


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

function cutFragmentText($html, $from, $to){
    $from   = strpos($html, $from);
    $to     = strpos($html, $to);

    return substr($html, $from, $to - $from);
}

function cutFragmentList($html, $typList){
    $from   = strpos($html, $typList);
    $to     = strrpos($html, $typList);

    return substr($html, $from, $to - $from);
}


//DATABASE
//    CHECK
function DBCheckExistPerson($name, $year, $mysql){

    $question = "SELECT id FROM `celebryta` WHERE `nazwisko` LIKE '$name' AND wiek = '$year'";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        return $row['id'];
    }else{
        return 0;
    }
}

function DBCheckExistMovie($name, $mysql){

    $question = "SELECT id FROM `film` WHERE `tytul` LIKE '$name'";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
            return $row['id'];
    }else{
        return 0;
    }
}

function DBCheckExistGenre($name, $mysql){

    $question = "SELECT id FROM `gatunek` WHERE `gatunek` LIKE '$name'";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
            return $row['id'];
    }else{
        return 0;
    }
}

function DBCheckExistGame($idMovie, $idPerson, $mysql){

    $question = "SELECT id FROM `gra` WHERE `idFilm` = $idMovie AND `idCelebryta` = $idPerson";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
            return $row['id'];
    }else{
        return 0;
    }
}

function DBCheckExistCountryProduction($name, $mysql){

    $question = "SELECT id, COUNT(*) result FROM `krajprodukcji` WHERE `kraj` LIKE '$name'";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        if($row['result'] >= 1){
            return $row['id'];
        }else{
            return 0;
        }
    }else{
        return 0;
    }
}

function DBCheckExistOfGenre($id, $idGenre, $mysql){

    $len = strlen($idGenre);
    if($len == 1){
        $question = "SELECT MG.id, MG.idGatunek FROM `magatunek` MG, `magatunek` MGG WHERE MG.id = MGG.id AND MG.idGatunek != MGG.idGatunek AND MG.id = $idGenre[0] AND MGG.id = $idGenre[0];";
    }elseif($len == 2){
        $question = "SELECT MG.id, MG.idGatunek FROM `magatunek` MG, `magatunek` MGG WHERE MG.id = MGG.id AND MG.idGatunek != MGG.idGatunek AND MG.id = $idGenre[0] AND MGG.id = $idGenre[0] AND MG.id = $idGenre[1] AND MGG.id = $idGenre[1];";
    }elseif($len == 3){
        $question = "SELECT MG.id, MG.idGatunek FROM `magatunek` MG, `magatunek` MGG WHERE MG.id = MGG.id AND MG.idGatunek != MGG.idGatunek AND MG.id = $idGenre[0] AND MGG.id = $idGenre[0] AND MG.id = $idGenre[1] AND MGG.id = $idGenre[1] AND MG.id = $idGenre[2] AND MGG.id = $idGenre[2];";
    }else{
        $question = "SELECT MG.id, MG.idGatunek FROM `magatunek` MG, `magatunek` MGG WHERE MG.id = MGG.id AND MG.idGatunek != MGG.idGatunek AND MG.id = $idGenre[0] AND MGG.id = $idGenre[0] AND MG.id = $idGenre[1] AND MGG.id = $idGenre[1] AND MG.id = $idGenre[2] AND MGG.id = $idGenre[2] AND MG.id = $idGenre[3] AND MGG.id = $idGenre[3];";
    }

    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        if($row['result'] >= 1){
            return $row['id'];
        }else{
            return 0;
        }
    }else{
        return 0;
    }
}

function DBCheckExistMarking($idMovie, $idUser, $mysql){

    $question = "SELECT id, COUNT(*) result FROM `ocena` WHERE `idFilm` = $idMovie AND `idUzytkownik` = $idUser";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        if($row['result'] >= 1){
            return $row['id'];
        }else{
            return 0;
        }
    }else{
        return 0;
    }
}

function DBCheckExistFriend($idMe, $idFriend, $mysql){

    $question = "SELECT id, COUNT(*) result FROM `film` WHERE (`idMoje` = $idMe AND `idPrzyjaciela` = $idFriend) OR (`idMoje` = $idFriend AND `idPrzyjaciela` = $idMe)";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        if($row['result'] >= 1){
            return $row['id'];
        }else{
            return 0;
        }
    }else{
        return 0;
    }
}

function DBCheckExistShareMovie($title, $mysql){

    $question = "SELECT COUNT(*) result FROM `film` WHERE name LIKE '$title'";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        if($row['result'] >= 1){
            return 1;
        }else{
            return 0;
        }
    }else{
        return 0;
    }
}

function DBCheckExistUser($name, $mysql){

    $question = "SELECT id FROM `uzytkownik` WHERE nazwisko LIKE '$name'";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
            return $row['id'];
    }else{
        return 0;
    }
}

function DBGetIDUser($name, $mysql){
    return DBCheckExistUser($name, $mysql);
}
//    END CHECK

//    ADD
function DBAddPerson($name, $age, $photo, $mysql){

    if(DBCheckExistPerson($name, $age, $mysql)){
    }else{
        $sql = "INSERT INTO `film`.`celebryta` (`id`, `imie`, `nazwisko`, `wiek`, `zdjecie`) VALUES (NULL, '','$name' , '$age', '$photo');";
        $mysql->query($sql);
    }
}

function DBAddMovie($title, $premier, $description, $lenght, $intDirector, $intScenarist, $intPhoto, $intMaterial, $intScenographi, $intKostium, $intProduction, $intSound, $intGenre, $photo, $mysql){

    if(DBCheckExistMovie($title, $mysql)){}
    else{
        $sql = "INSERT INTO `film`.`film` (`id`, `tytul`, `premiera`, `opis`, `dlugosc`, `rezyser`, `scenariusz`, `zdjecia`, `materialy`, `scenografia`, `kostiumy`, `produkcja`, `dzwiek`, `gatunek`, `zdjecie`) VALUES (NULL, '$title', '$premier', '$description', '$lenght', '$intDirector', '$intScenarist', '$intPhoto', '$intMaterial', '$intScenographi', '$intKostium', '$intProduction', '$intSound', '$intGenre', '$photo');";
        $mysql->query($sql);
    }
}

function DBAddGenre($ganre, $mysql){

    if(DBCheckExistGenre($ganre, $mysql)){}
    else{
        $sql = "INSERT INTO `film`.`gatunenk` (`id`, `gatunek`) VALUES (NULL, '$ganre');";
        $mysql->query($sql);
    }
}

function DBAddOfGenre($ganre, $mysql){


}

function DBAddGame($intMovie, $intPerson, $intShare, $how, $mysql){
//intShare 1 = actor | 2 = creator
    if(DBCheckExistGame($intMovie, $intPerson, $mysql)){}
    else{
        $sql = "INSERT INTO `film`.`gra` (`id`, `idFilm`, `idCelebryta`, `udzialWFilmie`, `jako`) VALUES (NULL, '$intMovie', '$intPerson', '$intShare', '$how');";
        $mysql->query($sql);
    }
}

function DBAddMark($commend, $mark, $data, $mysql){

    $sql = "INSERT INTO `film`.`ocena` (`id`, `ocena`, `komentarz`, `data`) VALUES (NULL, '$mark', '$commend', '$data');";
    $mysql->query($sql);
    $sql = "SELECT id FROM `film`.`ocena` WHERE `ocena` = '$mark' AND `data` = '$data'";
    $result = $mysql->query($sql);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        return $row['id'];
    }else{
        return 0;
    }
}

function DBAddMarking($intMovie, $intUser, $intMark, $data, $mysql){

    if(DBCheckExistMarking($intMovie, $intUser, $mysql)){}
    else{
        $sql = "INSERT INTO `film`.`oceniac` (`idFilm`, `idUzytkownik`, `idOcena`, `data`) VALUES ('$intMovie', '$intUser', '$intMark', '$data');";
        $mysql->query($sql);
    }
}

function DBAddUser($name, $age, $mail, $sex, $password, $mysql){
//sex  1 = man  | 2 = women
    if(DBCheckExistUser($name, $mysql)){
    }else{
        $sql = "INSERT INTO `film`.`uzytkownik` (`id`, `imie`, `nazwisko`, `email`, `wiek`, `plec`, `haslo`) VALUES (NULL, '','$name', '$mail', '$age', '$sex','$password');";
        $mysql->query($sql);
    }
}
//    END ADD


//END DATABASE



// FW = filmweb.pl
function FWfindLinkInList($html, $expresionFrom, $exprestionTo){

    $expresion = '@' . $expresionFrom . '([^<]+?)' . $exprestionTo . '@u';
    preg_match_all($expresion, $html, $result);
    return $result;
}

function FWDownloadListLinkToFilm($html){

    $expresionFrom = "span id=\"filmRecommendPageFB\" class=\"hide\">";
    $exprestionTo = "/userRecommends#recomm-list</span>";
    $listHref = FWfindLinkInList($html, $expresionFrom, $exprestionTo);
    return $listHref;
}

function FWGetTitle($html){

    $expresion = '@og:title" content="([^<]+?)"><meta property=@u';
    preg_match_all($expresion, $html, $result);
    return $result[1][0];
}

function FWGetPremier($html){

    $expresion = '@/dates"> <span> ([^<]+?) </span>@u';
    preg_match_all($expresion, $html, $result);

    $resultOut = $result[1][0];
    $change         = false;
    $month          = Array(" stycznia ", " lutego ", " marca ", " kwietnia ", " maja ", " czerwca ", " lipca ", " sierpnia ", " września ", " października ", " listopada ", " grudnia ");
    $monthCorrect   = Array("-01-", "-02-", "-03-", "-04-", "-05-" ,"-06-", "-07-", "-08-", "-09-", "-10-", "-11-", "-12-",);
    for($i = 0; $i < 12; $i++){
        $resultH = str_replace($month[$i], $monthCorrect[$i], $resultOut);
        if($resultH != $resultOut){
            $change     = true;
            $resultOut  = $resultH;
        }
    }

    if(!$change){
        $month = Array("styczń ", "luty ", "marzec ", "kwiecień ", "maj ", "czerwiec ", "lipiec ", "sierpń ", "wrzesień ", "październik ", "listopad ", "grudzień ");

        for($i = 0; $i < 12; $i++){
            $resultOut  = str_replace($month[$i], $monthCorrect[$i], $resultOut);
        }

        preg_match('@-([^*]+?)-([^*]+?)-@u', $resultOut."-", $resultData);
        $resultOut = $resultData[2] . "-" . $resultData[1] . "-01";
    }
    else{
        preg_match('@([^<]+?)-([^<]+?)-([^*]+?)-@u', $resultOut."-", $resultData);
        $resultOut = $resultData[3] . "-" . $resultData[2] . "-" . $resultData[1];
    }

    return $resultOut;
}

function FWGetDescription($html){

    $expresion = '@<p class="text">([^*]+?)</p></div>@u';
    preg_match_all($expresion, $html, $result);
    return $result[1][0];
}

function FWGetDuration($html){

    $expresion = '@duration:"([^<]+?)"@u';
    preg_match_all($expresion, $html, $result);
    return $result[1][0];
}

function FWGetDirector($html){

    $expresion = '@title="([^<]+?)" rel="v:directedBy"@u';
    preg_match_all($expresion, $html, $result);
    return $result[1][0];
}

function FWGetScenarist($html){

    $expresion = '@scenariusz:([^?]+?)">([^<]+?)</a></li></ul>@u';
    preg_match_all($expresion, $html, $result);
    return $result[2][0];
}

function FWGetGenre($html){
    preg_match_all('@gatunek([^\']+?)produkcja@u', $html, $r);
    $html = $r[1][0];
    $expresion = '@<li>([^>]+?)">([^<]+?)</a></li>@u';
    preg_match_all($expresion, $html, $result);
    return $result[2];
}

function FWGetPhoto($html){

    $expresion = '@meta property="og:image" content="([^<]+?)">@u';
    preg_match_all($expresion, $html, $result);
    return $result[1][0];
}

function FWGetActors($html){

    preg_match_all('@<tr id="role([^A]+?)"><td><a href="([^A]+?)" class=([^A]+?)rel="v:starring">([^<]+?)</a></td>([^A]+?)>jako:</span></td>([^A]+?)<span>([^<]+?)</span></td>([^A]+?)@u', $html, $result);
    $actor  = $result[4];
    $role   = $result[7];
    $link   = $result[2];


    $resultOut[0] = $actor;
    $resultOut[1] = $role;
    $resultOut[2] = $link;

    return $resultOut;
}

function FWGetCreators($url){

    $html = downloadSite($url."/cast/crew");
    $expresion = '@<h2([^A]+?)">([^A]+?)</h2>([^A]+?)={pn:"([^A]+?)",pl:"([^A]+?)",rt:([^A]+?)</div@u';
    preg_match_all($expresion, $html, $result);

    $resultOut[0] = $result[4];
    $resultOut[1] = $result[2];
    $resultOut[2] = $result[5];

    return $resultOut;
}

function FWGetComments($html){

    $expresion = '@id="topic([^A]+?)autor:([^A]+?)">([^<]+?)</a>([^A]+?)title="([^<]+?)">([^A]+?)ten film na: ([^<]+?) <i class="([^A]+?)normal">([^A]+?)</p([^A]+?)<div class="boxContainer@u';
    preg_match_all($expresion, $html, $result);

    $user           = $result[3];
    $time           = $result[5];
    $mark           = $result[7];
    $opinion        = $result[9];

    $resultOut[0]   = $user;
    $resultOut[1]   = $mark;
    $resultOut[2]   = $opinion;
    $resultOut[3]   = $time;

    return $resultOut;
}

function FWGetPerson($url){

    $html = downloadSite("http://www.filmweb.pl" . $url);

    $result[1][0] = $result[3][0] = $result[5][0] = "";
    $expresion = '@viewed">([^*]+?)</a>([^*]+?)data urodzenia:</th><td>([^*]+?)<script([^*]+?)<img rel="v:image" src="([^*]+?)" alt@u';
    preg_match_all($expresion, $html, $result);

    $resultOut[0] = $resultOut[1] = $resultOut[2] = "";
    if(!empty($result[1][0]))
        $resultOut[0]   = $result[1][0];
    else
        $resultOut[0]   = "";
    if(!empty($result[3][0])){
        $resultOut[1]   = $result[3][0];
        $resultOut[1]   = substr($resultOut[1], 0, 10);
        $resultOut[1]   = str_replace("</td>", "", $resultOut[1]);
        $resultOut[1]   = str_replace("</tr>", "", $resultOut[1]);
        $resultOut[1]   = str_replace("<tr><", "", $resultOut[1]);
        $resultOut[1]   = str_replace("th>da", "", $resultOut[1]);
    }
    else
        $resultOut[1]   = "";
    if(!empty($result[5][0]))
        $resultOut[2]   = $result[5][0];
    else
        $resultOut[2]   = "";
//    print_r($resultOut);
    return $resultOut;
}

function FWGetIdUser($user, $mysql){
    $question = "SELECT id FROM `uzytkownik` WHERE `name` LIKE '$user'";
    $result = $mysql->query($question);
    if (@$result->num_rows > 0){
        $row = $result->fetch_assoc();
        return $row['id'];
    }else{
        return 0;
    }
}
// END FW = filmweb.pl


//FW-DB-Add
function FWSplineGenre($genre){
    $result = "";
    for($i = 0; $i < count($genre); $i++){
        if($i < count($genre)-1)
            $result .= $genre[$i] . " ";
        else
            $result .= $genre[$i];
    }
    return $result;
}

function FWDBGetIdPerson($person, $idArray, $mysql){
    $personInfo = FWGetPerson($person[2][$idArray]);
    return DBCheckExistPerson($personInfo[0], $personInfo[1], $mysql);
}

function FWGetIdPerson($person, $idArray, $mysql){
    $personInfo = $person[3][$idArray];
    return DBCheckExistPerson($personInfo[0], $personInfo[1], $mysql);
}

function FWDBAddPerson($persons, $mysql){
    for($i = 0; $i < count($persons[2]); $i++){
        $person = FWGetPerson($persons[2][$i]);
        $persons[3][$i] = $person;
        DBAddPerson($person[0], $person[1], $person[2], $mysql);
    }
    return $persons;
    //print_r($person);
}

function FWDBAddMmovie($m, $mysql){

    DBAddMovie($m[0], $m[1], $m[2], $m[3], FWGetIdPerson($m[9], 0, $mysql), FWGetIdPerson($m[9], 1, $mysql), FWGetIdPerson($m[9], 2, $mysql), FWGetIdPerson($m[9], 3, $mysql),FWGetIdPerson($m[9], 4, $mysql),FWGetIdPerson($m[9], 5, $mysql), FWGetIdPerson($m[9], 6, $mysql),FWGetIdPerson($m[9], 7, $mysql), FWSplineGenre($m[6]), $m[7], $mysql);
    return DBCheckExistMovie($m[0], $mysql);
}

function FWDBConnectActorWithMovie($idMovie, $actors, $creators, $mysql){

    for($i = 0; $i < count($actors[1]); $i++){
        $idP = FWGetIdPerson($actors, $i, $mysql);
        $how = $actors[1][$i];
        DBAddGame($idMovie, $idP, "1", $how, $mysql);
    }
    for($i = 0; $i < count($creators[1]); $i++){
        $idP = FWGetIdPerson($creators, $i, $mysql);
        $how = $creators[1][$i];
        DBAddGame($idMovie, $idP, "2", $how, $mysql);
    }
}

function FWDBAddUsersAndMarks($marks, $idMovie, $mysql){

    $number = count($marks[0]);
    for($i = 0; $i < $number; $i++){
        if(!DBCheckExistUser($marks[0][$i], $mysql)){
            DBAddUser($marks[0][$i], "", "", "", "", $mysql);
        }
        $idUser = DBGetIdUser($marks[0][$i], $mysql);
        $idMark = DBAddMark($marks[2][$i], $marks[1][$i], $marks[3][$i], $mysql);

        DBAddMarking($idMovie, $idUser, $idMark, $marks[3][$i], $mysql);
    }
}

$time_start = getmicrotime();

$listLinkToFilm = FWDownloadListLinkToFilm(downloadSite($url));

for($i = $movieURL-1; $i < $movieURL ; $i++){
    $site = downloadSite($listLinkToFilm[1][$i]);

//  INFORMATION ABOUT MOVIE

    $movie[0]   =   FWGetTitle($site);
    $movie[1]   =   FWGetPremier($site);
    $movie[2]   =   FWGetDescription($site);
    $movie[3]   =   FWGetDuration($site);
//    $movie[4]   =   FWGetDirector($site); contain in FWGetCreators
//    $movie[5]   =   FWGetScenarist($site); contain in FWGetCreators
    $movie[6]   =   FWGetGenre($site);
    $movie[7]   =   FWGetPhoto($site);
    $movie[8]   =   FWGetActors($site);
    $movie[9]   =   FWGetCreators($listLinkToFilm[1][$i]);
    $comments   =   FWGetComments($site);


    echo "<br>
    ";
    echo $movie[0];
    echo "<br>
    ";
    echo "<img src=\"$movie[7]\" width='150' height='200'/>";
    echo "<br>
    ";

//Add Actors Movie
    $movie[8]   = FWDBAddPerson($movie[8], $mysql);
//Add Creators Movie
    $movie[9]   = FWDBAddPerson($movie[9], $mysql);
//Add Movie
    $idAddMovie = FWDBAddMmovie($movie, $mysql);
//Connect Actors And Creators With Movie
    FWDBConnectActorWithMovie($idAddMovie, $movie[8], $movie[9], $mysql);

//Add user and commenads to movie
    FWDBAddUsersAndMarks($comments, $idAddMovie, $mysql);

//  END INFORMATION ABOUT MOVIE
}



















$time_stop = getmicrotime();
$roznica = $time_stop - $time_start;
echo '<h1>Czas: ' . $roznica . "</h1><br/>";
?>



</body>
</html>