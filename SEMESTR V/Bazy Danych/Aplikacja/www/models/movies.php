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

    public function executeQuery($sql){
        $this->pdo->query($sql);
    }

    public function resultQuestion($sql){
        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($sql);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        $select->closeCursor();
        return $data;
    }

    public function index()
    {
        $cat = $this->loadModel('Movie');
        //$this->render('indexVategory');
    }

    public function getMovies($from = 0, $number = 20)
    {
        $query = "CALL `p_movies`($from, $number)";
        return $this->resultQuestion($query);
    }

    public function getPersons($search="", $from = 0, $number = 20)
    {
        if($search == "")
            $query = "CALL `p_persons`($from, $number)";
        else
            $query = "CALL `p_personsSearch`($from, $number, '$search');";
        return $this->resultQuestion($query);
    }

    public function getGenres()
    {
        $query = "CALL `p_genres`()";
        return $this->resultQuestion($query);
    }

    public function getCount($table = "movie"){

        $query = "SELECT COUNT(*) as count FROM `$table`";
        $data = $this->resultQuestion($query);

        return $data[0][0] / 20;
    }


    public function getSearchCount($search, $genre, $mark){
        $query = MoviesModel::cmqCount($search, $genre, $mark);
        $data = $this->resultQuestion($query);

        return $data[0][0] / 20;
    }

    public function getCountPerson($search){
        if($search == "")
            $query = "SELECT count(*) FROM person";
        else
            $query = "SELECT count(*) FROM person WHERE name LIKE '%$search%'";
        $data = $this->resultQuestion($query);

        return $data[0][0] / 20;
    }

    public function getSearchMovies($search, $genre, $mark, $sort, $from = 0, $number = 20)
    {
        $query = MoviesModel::cmq($search, $genre, $mark, $sort, $from, $number);
        return $this->resultQuestion($query);
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
}