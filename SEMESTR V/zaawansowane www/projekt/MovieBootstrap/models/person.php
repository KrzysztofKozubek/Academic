<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 11:44
 */

include_once 'models/model.php';


class PersonModel extends Model
{

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

    public function getPerson($idPerson){
        $query = "SELECT * FROM person WHERE id = $idPerson";
        return $this->resultQuestion($query);
    }

    public function getMovieWhereBeActor($idPerson){
        $query = "SELECT m.id, m.poster, m.title, c.participation  FROM `creator` c, `person` p, `movie` m WHERE p.id = $idPerson AND p.id = c.idPerson AND c.idMovie = m.id AND c.creator = 0";
        return $this->resultQuestion($query);
    }

    public function getMovieWhereBeCreator($idPerson){
        $query = "SELECT m.id, m.poster, m.title, c.participation FROM `creator` c, `person` p, `movie` m WHERE p.id = $idPerson AND p.id = c.idPerson AND c.idMovie = m.id AND c.creator = 1";
        return $this->resultQuestion($query);
    }

    public function getAwards($idPerson){
        $query = "SELECT * FROM `award` a WHERE a.idPerson = $idPerson ORDER BY a.date, a.won DESC";
        return $this->resultQuestion($query);
    }
}