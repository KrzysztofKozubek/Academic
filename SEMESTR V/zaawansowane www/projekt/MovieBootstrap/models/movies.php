<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 14:59
 */

include 'models/model.php';


class MoviesModel extends Model
{
//    number movies on a one site is 20

    public function index()
    {
        $cat = $this->loadModel('Movie');
        //$this->render('indexVategory');
    }

    public function getMovies($from = 0, $number = 20, $query = null)
    {
        $query = "SELECT id, poster, title, description FROM movie WHERE releaseDate <= CURDATE() ORDER BY releaseDate DESC LIMIT $from, $number";
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data;
    }

    public function getPersons($search="", $from = 0, $number = 20, $query = null)
    {
        if($search == "")
            $query = "SELECT * FROM person ORDER BY name DESC LIMIT $from, $number";
        else
            $query = "SELECT * FROM person WHERE name LIKE '%$search%' ORDER BY name DESC LIMIT $from, $number";

        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data;
    }

    public function getGenres()
    {
        $query = "SELECT genre FROM `movie` WHERE `genre` NOT LIKE '% %' AND `genre` NOT LIKE ' ' GROUP BY `genre` LIMIT 0, 50";
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data;
    }

    public function getCount($table = "movie"){

        $query = "SELECT COUNT(*) as count FROM `$table`";
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data[0][0] / 20;
    }


    public function getSearchCount($search, $genre, $mark){
        $query = MoviesModel::cmqCount($search, $genre, $mark);
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data[0][0] / 20;
    }

    public function getCountPerson($search){
        if($search == "")
            $query = "SELECT count(*) FROM person";
        else
            $query = "SELECT count(*) FROM person WHERE name LIKE '%$search%'";
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data[0][0] / 20;
    }

    public function getSearchMovies($search, $genre, $mark, $sort, $from = 0, $number = 20)
    {
        $query = MoviesModel::cmq($search, $genre, $mark, $sort, $from, $number);
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();
        return $data;
    }

    public static function cmq($search, $genre, $mark, $sort, $from = 0, $number = 20)
    {
        $movieE     = array(
            1 => "title",
            2 => "genre",
            3 => "mark"
        );
        $movieArg   = array(
            1 => $search,
            2 => $genre,
            3 => $mark
        );
        $movieIf    = array(
            1 => " LIKE '%",
            2 => " LIKE '%",
            3 => " >= "
        );
        $movieEndIf = array(
            1 => "%'",
            2 => "%'",
            3 => " "
        );
        $movieSort  = array(
            "Najnowsze"     => "releaseDate DESC",
            "Najstarsze"    => "releaseDate ASC",
            "Oceny Max"     => "title DESC",
            "Oceny Min"     => "title ASC",
            "A - Z"         => "title ASC",
            "Z - A"         => "title DESC"
        );
        $result     = null;

        $result = "SELECT id, title, poster FROM movie WHERE 1";
        for ($i = 1; $i < 4; $i++) {
            if (!empty($movieArg[$i]) && $movieArg[$i] != "All") {
                $result .= " AND $movieE[$i] $movieIf[$i]$movieArg[$i]$movieEndIf[$i]";
            }
        }
        if (!empty($sort) && $sort != "All") {
            $result .= " ORDER BY $movieSort[$sort]";
        }
        $result .= " LIMIT $from, $number";
//        echo $result;
        return $result;

    }

    public static function cmqCount($search, $genre, $mark)
    {
        $movieE     = array(
            1 => "title",
            2 => "genre",
            3 => "mark"
        );
        $movieArg   = array(
            1 => $search,
            2 => $genre,
            3 => $mark
        );
        $movieIf    = array(
            1 => " LIKE '%",
            2 => " LIKE '%",
            3 => " >= "
        );
        $movieEndIf = array(
            1 => "%'",
            2 => "%'",
            3 => " "
        );
        $result     = null;

        $result = "SELECT COUNT(*) FROM movie WHERE 1";
        for ($i = 1; $i < 4; $i++) {
            if (!empty($movieArg[$i]) && $movieArg[$i] != "All") {
                $result .= " AND $movieE[$i] $movieIf[$i]$movieArg[$i]$movieEndIf[$i]";
            }
        }
//        echo $result;
        return $result;
    }


    public function getAll()
    {
        $query = "SELECT * FROM movie LIMIT 0, 15";
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data;
    }

    public function createMysqlQuestion($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer, $from = 0, $to = 15)
    {
        $enum = array(
            1 => "name",
            2 => "description",
            3 => "type",
            4 => "premier",
            5 => "premier",
            6 => "time",
            7 => "time",
            8 => "rating",
            9 => "rating",
            10 => "Wersja: "
        );
        $enum1 = array(
            0 => "Nieznany typ",
            1 => "Orginał",
            2 => "Napisy",
            3 => "Lektor",
            4 => "Dubbing",
            5 => "HD orginał",
            6 => "HD Napisy",
            7 => "HD Lektor",
            8 => "HD Dubbing"
        );
        $enum2 = array(
            1 => $name,
            2 => $wor,
            3 => $typ,
            4 => $datF,
            5 => "$datT",
            6 => "$ratF",
            7 => "$ratT",
            8 => "$lonF",
            9 => "$lonT",
            10 => "$wer"
        );
        $enum3 = array(
            1 => " LIKE '%",
            2 => " LIKE '%",
            3 => " LIKE '%",
            4 => " >= ",
            5 => " <= ",
            6 => " >= ",
            7 => " <= ",
            8 => " <= ",
            9 => " <= ",
            10 => "Wersja: "
        );
        $result = "SELECT id, photo, name, description FROM movie WHERE 1 ";

        $i = 1;
        for (; $i < 4; $i++) {
            if (!empty($enum2[$i])) {
                $help = preg_split('/ /', $enum2[$i]);
                $lenght = count($help);
                for ($j = 0; $j < $lenght; $j++) {
                    $result .= " AND " . $enum[$i] . $enum3[$i] . $help[$j] . "%' ";
                }
            }
        }

        for (; $i < 10; $i++) {
            if (!empty($enum2[$i])) {
                $help = preg_split('/ /', $enum2[$i]);
                $lenght = count($help);
                for ($j = 0; $j < $lenght; $j++) {
                    $result .= " AND " . $enum[$i] . $enum3[$i] . $help[$j] . "%\" ";
                }
            }
        }

        return $result . " ORDER BY premier DESC LIMIT $from, $to";
    }

    public function getTypeMovie($index)
    {
        $query = "SELECT `version` FROM `link` WHERE `id_film` = $index GROUP BY `version`";
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();

        return $data;
    }

    public function questionCategoryNOTLIKE($query)
    {
        $result = null;
        if ($query != null) {
            $lenght = count($query);
            for ($i = 0; $i < $lenght; $i++) {
                $result .= " NOT LIKE \"%" . $query[$i] . "%\"";
            }
        }
        return $result;
    }

    public static function createUrlFiltrCategory($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer, $var, $arg)
    {
        $enum = array(
            1 => "name",
            2 => "words",
            3 => "type",
            4 => "dateFrom",
            5 => "dateTo",
            6 => "ratingFrom",
            7 => "ratingTo",
            8 => "longFrom",
            9 => "longTo",
            10 => "wersion"
        );
        $enum2 = array(
            1 => $name,
            2 => $wor,
            3 => $typ,
            4 => $datF,
            5 => "$datT",
            6 => "$ratF",
            7 => "$ratT",
            8 => "$lonF",
            9 => "$lonT",
            10 => "$wer"
        );
        $result = "?s=search";
        $i = 1;
        for (; $i < 11 && $i < $arg; $i++) {
            $result .= "&" . $enum[$i] . "=" . $enum2[$i];
        }
        $result .= "&" . $enum[$i] . "=" . $var . " " . $enum2[$i];
        $i++;
        for (; $i < 11; $i++) {
            $result .= "&" . $enum[$i] . "=" . $enum2[$i];
        }
        return $result;
    }

    public static function  createUrlDeleteWithFilter($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer, $arg)
    {

        $enum = array(
            1 => "name",
            2 => "words",
            3 => "type",
            4 => "dateFrom",
            5 => "dateTo",
            6 => "ratingFrom",
            7 => "ratingTo",
            8 => "longFrom",
            9 => "longTo",
            10 => "wersion"
        );
        $enum2 = array(
            1 => $name,
            2 => $wor,
            3 => $typ,
            4 => $datF,
            5 => "$datT",
            6 => "$ratF",
            7 => "$ratT",
            8 => "$lonF",
            9 => "$lonT",
            10 => "$wer"
        );
        $result = "?s=search";
        $i = 1;
        for (; $i < 11 && $i < $arg; $i++) {
            $result .= "&" . $enum[$i] . "=" . $enum2[$i];
        }

        $result .= "&" . $enum[$i] . "=";

        $i++;
        for (; $i < 11; $i++) {
            $result .= "&" . $enum[$i] . "=" . $enum2[$i];
        }
        return $result;
    }

    public static function createFilt($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer)
    {

        $enum = array(
            1 => "Tytuł: ",
            2 => "Zawiera opis: ",
            3 => "Kategoria: ",
            4 => "Premiera od: ",
            5 => "Premiera do: ",
            6 => "Ocena od: ",
            7 => "Ocena do: ",
            8 => "Czas od: ",
            9 => "Czas do: ",
            10 => "Wersja: "
        );
        $enum1 = array(
            0 => "Nieznany typ",
            1 => "Orginał",
            2 => "Napisy",
            3 => "Lektor",
            4 => "Dubbing",
            5 => "HD orginał",
            6 => "HD Napisy",
            7 => "HD Lektor",
            8 => "HD Dubbing",
            9 => " HD "
        );
        $enum2 = array(
            1 => $name,
            2 => $wor,
            3 => $typ,
            4 => $datF,
            5 => "$datT",
            6 => "$ratF",
            7 => "$ratT",
            8 => "$lonF",
            9 => "$lonT",
            10 => "$wer"
        );
        $result = null;
        for ($j = 1; $j < 10; $j++) {
            if (!empty($enum2[$j])) {

                $result .= $enum[$j];
                $help = $enum2[$j];

                $help = preg_split('/ /', $enum2[$j]);
                $lenght = count($help);
                $result .= "<a href = \"";
                $result .= MovieModel::createUrlDeleteWithFilter($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer, $j);
                $result .= "\">";
                for ($i = 0; $i < $lenght; $i++) {
                    $result .= $help[$i] . " ";

                }
                $result .= "</a><br>";
            }
        }
        for ($i = 10; $i < 11; $i++) {
            if (!empty($enum2[$i])) {
                $result .= $enum[$i];
                $help = $enum[$i];

                $help = preg_split('/ /', $enum2[$i]);
                $lenght = count($help);
                $result .= "<a href = \"";
                $result .= MovieModel::createUrlDeleteWithFilter($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer, $i);
                $result .= "\">";
                for ($j = 0; $j < $lenght; $j++) {
                    $result .= $enum1[$help[$j]] . " ";
                }
                $result .= "</a><br>";
            }
        }

        return $result;
    }
}