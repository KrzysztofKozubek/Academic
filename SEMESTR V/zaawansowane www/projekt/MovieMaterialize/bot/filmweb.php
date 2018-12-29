<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 13:28
 */

    $ip                 = "http://".$_SERVER ["SERVER_NAME"];
    $urlNextSite        = $ip . $_SERVER ["PHP_SELF"];


    //create URL site
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
//connection with database
    require_once('../config/sql.php');
    $mysql = connectWithDataBase($host, $user, $pass, $dbase);

//url filmweb site = $siteURL
    $url        = "http://www.filmweb.pl/search/film?q=&type=&startYear=&endYear=&countryIds=&genreIds=&startRate=&endRate=&startCount=&endCount=&sort=COUNT&sortAscending=false&c=portal&page=$siteURL";

//what will be download
//basic information
    $title          = "";
    $releaseDate    = "";
    $description    = "";
    $length         = "";
    $mark           = "";
    $genre          = "";
    $poster         = "";
//person
    $actors         = "";
    $creators       = "";
//other
    $award          = "";
    $comments       = ""; // in this will be join users

    $numberOfPerson = 10; // how much people would download with one movie

//number downloader
$downloadSiteNumber     = 0;
$downloadActorNumber    = 0;
$downloadCreatorNumber  = 0;
$downloadCommnetNumber  = 0;


//functions basic
    function getmicrotime(){
        $microtime = explode(' ', microtime());
        return $microtime[1] . substr($microtime[0], 1);
    }

    function downloadSite($url) {
        $GLOBALS['downloadSiteNumber'] += 1;
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

//functions basic END


//functions Filmweb.pl

//basic information about movie
    function FWSearchFilmListLink($html){
        $expresionFrom = "span id=\"filmRecommendPageFB\" class=\"hide\">";
        $exprestionTo = "/userRecommends#recomm-list</span>";
        $listHref = '@' . $expresionFrom . '([^<]+?)' . $exprestionTo . '@u';
        preg_match_all($listHref, $html, $result);
        return $result[1];
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

    function FWGetMark($html){
        $expresion = '@property="v:average">([^<]+?)<@u';
        preg_match_all($expresion, $html, $result);
        return $result[1][0];
    }

    function FWGetGenre($html){
        preg_match_all('@gatunek([^\']+?)produkcja@u', $html, $r);
        $html = $r[1][0];
        $expresion = '@<li>([^>]+?)">([^<]+?)</a></li>@u';
        preg_match_all($expresion, $html, $result);
        return FWSplineGenre($result[2]);
    }

    function FWGetPhoto($html){

        $expresion = '@meta property="og:image" content="([^<]+?)">@u';
        preg_match_all($expresion, $html, $result);
        return $result[1][0];
    }
//basic informaction about movie END

//information person
//[0] <- name, [1] <- participation, [2] <- link to person on FW.pl

    function FWGetCreators($link){
        $html = downloadSite($link."/cast/crew");
        $expresion = '@<h2([^A]+?)">([^A]+?)</h2>([^A]+?)={pn:"([^A]+?)",pl:"([^A]+?)",rt:([^A]+?)</div@u';
        preg_match_all($expresion, $html, $result);

        $resultOut[0] = $result[4];
        $resultOut[1] = $result[2];
        $resultOut[2] = $result[5];

        return $resultOut;
    }

    function FWGetActors($link){
        $html = downloadSite($link."/cast/actors");
        $expresion = '@pn:"([^A]+?)",pl:"([^A]+?)",rt:"([^"]*?)",pr:"@u';
        preg_match_all($expresion, $html, $result);

        $resultOut[0] = $result[1];
        $resultOut[1] = $result[3];
        $resultOut[2] = $result[2];

        return $resultOut;
    }

    function FWGetPerson($link){

        $html = downloadSite("http://www.filmweb.pl" . $link);

        $result[1][0] = $result[3][0] = $result[5][0] = "";
        $expresion = '@viewed">([^*]+?)</a>([^*]+?)data urodzenia:</th><td>([^*]+?)<script([^*]+?)<img rel="v:image" src="([^*]+?)" alt@u';
        preg_match_all($expresion, $html, $result);

        $resultOut[0] = $resultOut[1] = $resultOut[2] = "";
        if(!empty($result[1][0])){
            $resultOut[0]   = $result[1][0];
        }else{
            $resultOut[0]   = "";
        }
        if(!empty($result[3][0])){
            $resultOut[1]   = $result[3][0];
            $resultOut[1]   = substr($resultOut[1], 0, 10);
            $resultOut[1]   = str_replace("</td>", "", $resultOut[1]);
            $resultOut[1]   = str_replace("</tr>", "", $resultOut[1]);
            $resultOut[1]   = str_replace("<tr><", "", $resultOut[1]);
            $resultOut[1]   = str_replace("th>da", "", $resultOut[1]);
        }else{
            $resultOut[1]   = "";
        }
        if(!empty($result[5][0])){
            $resultOut[2]   = $result[5][0];
        }else{
            $resultOut[2]   = "";
        }

        return $resultOut;
    }

//information person END

//other functions

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

function FWGetComments($html){

    $expresion = '@id="topic([^A]+?)autor:([^A]+?)">([^<]+?)</a>([^A]+?)title="([^<]+?)">([^A]+?)ten film na: ([^<]+?) <i class="([^A]+?)normal">([^A]+?)</p([^A]+?)<div class="boxContainer@u';
    preg_match_all($expresion, $html, $result);

    if(empty($result[3]) || empty($result[5]) || empty($result[7]) || empty($result[9])){
        $expresion = '@id="topic([^A]+?)autor:([^A]+?)">([^<]+?)</a>([^A]+?)">([^<]+?)<@u';
        preg_match_all($expresion, $html, $result);
    }
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

function FWGetAwards($html){

}

//other functions END


//functions Filmweb.pl END


//functions DataBase
//functions basic

    function executeQuery($sql, $mysql){
        $mysql->query($sql);
    }

    function returnQuery($sql, $mysql){
        $result = $mysql->query($sql);
        if (@$result->num_rows > 0){
            $row = $result->fetch_assoc();
            return $row;
        }else{
            return 0;
        }
    }

//functions basic END

//function Add

    function AddMovie($title, $releaseDate, $description, $length, $mark, $genre, $poster, $mysql){
        $sql = "INSERT INTO `movie`(`title`, `releaseDate`, `description`, `length`, `mark`, `genre`, `poster`) SELECT '$title', '$releaseDate', '$description', '$length', '$mark', '$genre', '$poster' FROM dual WHERE NOT EXISTS (SELECT `title`, `releaseDate`, `description`, `length`, `mark`, `genre`, `poster` FROM `movie` WHERE `title` LIKE '$title' AND `releaseDate` = '$releaseDate' AND `poster` = '$poster')";
        executeQuery($sql, $mysql);
    }

    function AddPerson($name, $dateOfBirth, $photo, $mysql){
        $sql = "INSERT INTO `person`(`name`, `dateOfBirth`, `photo`) SELECT '$name', '$dateOfBirth', '$photo' FROM dual WHERE NOT EXISTS (SELECT `name`, `dateOfBirth`, `photo` FROM `person` WHERE `name` LIKE '$name' AND `dateOfBirth` = '$dateOfBirth' AND `photo` = '$photo')";
        executeQuery($sql, $mysql);
    }

    function AddCreator($idMovie, $idPerson, $creator, $participation, $mysql){
        $sql = "INSERT INTO `creator`(`idMovie`, `idPerson`, `creator`, `participation`) SELECT '$idMovie', '$idPerson', '$creator', '$participation' FROM dual WHERE NOT EXISTS (SELECT * FROM `creator` WHERE `idMovie` = '$idMovie' AND `creator` = '$creator' AND `idPerson` = '$idPerson')";
        executeQuery($sql, $mysql);
    }

    function AddAward($idMovie, $idPerson, $description, $date, $mysql){
        $sql = "INSERT INTO `award`(`idMovie`, `idPerson`, `description`, `date`) SELECT '$idMovie', '$idPerson', '$description', '$date' FROM dual WHERE NOT EXISTS (SELECT * FROM `award` WHERE `idMovie` = '$idMovie' AND `idPerson` = '$idPerson' AND `date` = '$date')";
        executeQuery($sql, $mysql);
    }

    function AddComment($mark, $description, $date, $mysql){
        $sql = "INSERT INTO `comment`(`mark`, `description`, `date`) SELECT '$mark', '$description', '$date' FROM dual WHERE NOT EXISTS (SELECT * FROM `comment` WHERE `mark` = '$mark' AND `description` = '$description' AND `date` = '$date')";
        executeQuery($sql, $mysql);
    }

    function AddCommentmovie($idMovie, $idUser, $idComment, $mysql){
        $sql = "INSERT INTO `commentmovie`(`idMovie`, `idUser`, `idComment`) SELECT '$idMovie', '$idUser', '$idComment' FROM dual WHERE NOT EXISTS (SELECT * FROM `commentmovie` WHERE `idMovie` = '$idMovie' AND `idUser` = '$idUser')";
        executeQuery($sql, $mysql);
    }

    function AddFrienduser($idMovie, $idFriend, $mysql){
        $sql = "INSERT INTO `frienduser`(`idMovie`, `idFriend`) SELECT '$idMovie', '$idFriend' FROM dual WHERE NOT EXISTS (SELECT * FROM `frienduser` WHERE `idMovie` = '$idMovie' AND `idFriend` = '$idFriend')";
        executeQuery($sql, $mysql);
    }

    function AddUser($name, $email, $age, $sex, $password, $mysql){
        $sql = "INSERT INTO `user`(`name`, `email`, `age`, `sex`, `password`) SELECT '$name', '$email', '$age', '$sex', '$password' FROM dual WHERE NOT EXISTS (SELECT * FROM `user` WHERE `name` = '$name')";
        executeQuery($sql, $mysql);
    }

    function AddUserLog($idUser, $date, $mysql){
        $sql = "INSERT INTO `userlog`(`idUser`, `date`) SELECT '$idUser', '$date' FROM dual WHERE NOT EXISTS (SELECT * FROM `userlog` WHERE `idUser` = '$idUser' AND `date` = '$date')";
        executeQuery($sql, $mysql);
    }

//function Add END

//functions get id

    function getIdMovie($title, $releaseDate, $mysql){
        $sql = "SELECT id FROM movie WHERE title = '$title' AND releaseDate = '$releaseDate' LIMIT 1";
        $id = returnQuery($sql, $mysql);
        return $id['id'];
    }

    function getIdPerson($name, $date, $mysql){
    $sql = "SELECT id FROM person WHERE name = '$name' AND dateOfBirth = '$date' LIMIT 1";
    $id = returnQuery($sql, $mysql);
    return $id['id'];
}

    function getIdComment($description, $date, $mysql){
        $sql = "SELECT id FROM comment WHERE description = '$description' AND date = '$date' LIMIT 1";
        $id = returnQuery($sql, $mysql);
        return $id['id'];
    }

    function getIdUser($name, $mysql){
        $sql = "SELECT id FROM user WHERE name = '$name' LIMIT 1";
        $id = returnQuery($sql, $mysql);
        return $id['id'];
    }

//functions get id END

//functions DataBase END


$time_start = getmicrotime();


$listLinkToFilm = FWSearchFilmListLink(downloadSite($url));

for($j = $movieURL - 1; $j < $movieURL; $j++){
//download site specific movie

    $movieHTML      = downloadSite($listLinkToFilm[$j]);

//pick up with text html information on movie
    $title          = FWGetTitle($movieHTML);
    $releaseDate    = FWGetPremier($movieHTML);
    $description    = FWGetDescription($movieHTML);
    $length         = FWGetDuration($movieHTML);
    $mark           = FWGetMark($movieHTML);
    $genre          = FWGetGenre($movieHTML);
    $poster         = FWGetPhoto($movieHTML);
//pick up with link to actors and creators information about people participation in movie
    $actors         = FWGetActors($listLinkToFilm[$j]);
    $creators       = FWGetCreators($listLinkToFilm[$j]);

//pick up other information
    $comments       = FWGetComments($movieHTML);

//pick up detail person actors
    for($i = 0; $i < sizeof($actors[0]) & $i < $numberOfPerson; $i++){
        $actors[3][$i]  = FWGetPerson($actors[2][$i]);
    }
//pick up detail person creators
    for($i = 0; $i < sizeof($creators[0]) & $i < $numberOfPerson; $i++){
        $creators[3][$i] = FWGetPerson($creators[2][$i]);
    }


//add to database movie
    AddMovie($title, $releaseDate, $description, $length, $mark, $genre, $poster, $mysql);
    $idMovie = getIdMovie($title, $releaseDate, $mysql);

//add to database comments
    for($i = 0; $i < sizeof($comments[0]); $i++){
        AddComment($comments[1][$i], $comments[2][$i], $comments[3][$i], $mysql);
        $idComment  = getIdComment($comments[2][$i], $comments[3][$i], $mysql);
        AddUser($comments[0][$i], $comments[0][$i]."gmail.com", rand(15, 70), rand(0, 1), "0", $mysql);
        $idUser     = getIdUser($comments[0][$i], $mysql);
        AddCommentmovie($idMovie, $idUser, $idComment, $mysql);

        $downloadCommnetNumber++;
    }

//add to databse actors
    for($i = 0; $i < sizeof($actors[0]) & $i < $numberOfPerson; $i++){
        if(!empty($actors[3][$i][2])){
            AddPerson($actors[0][$i], $actors[3][$i][1], $actors[3][$i][2], $mysql);
            $idPerson = getIdPerson($actors[0][$i], $actors[3][$i][1], $mysql);
            AddCreator($idMovie, $idPerson, '0', $actors[1][$i], $mysql);
            $downloadActorNumber++;
        }
    }

//add to databse creators
    for($i = 0; $i < sizeof($creators[0]) & $i < $numberOfPerson; $i++){
        if(!empty($creators[3][$i][2])) {
            AddPerson($creators[0][$i], $creators[3][$i][1], $creators[3][$i][2], $mysql);
            $idPerson = getIdPerson($creators[0][$i], $creators[3][$i][1], $mysql);
            AddCreator($idMovie, $idPerson, '1', $creators[1][$i], $mysql);
            $downloadCreatorNumber++;
        }
    }

}

$time_stop = getmicrotime();
$roznica = $time_stop - $time_start;
echo '<h1>Czas: ' . $roznica . "</h1><br/>
<h1>$title</h1>
<h1>I $downloadSiteNumber pobranych stron!</h1>
<h1>actor: $downloadActorNumber</h1>
<h1>creator: $downloadCreatorNumber</h1>
<h1>comment: $downloadCommnetNumber</h1>";
?>



</body>
</html>




































































