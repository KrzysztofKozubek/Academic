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
        $query = "CALL p_person($idPerson)";
        return $this->resultQuestion($query);
    }

    public function getMovieWhereBeActor($idPerson){
        $query = "CALL p_movieWhereBeActor($idPerson)";
        return $this->resultQuestion($query);
    }

    public function getMovieWhereBeCreator($idPerson){
        $query = "CALL p_movieWhereBeCreator($idPerson)";
        return $this->resultQuestion($query);
    }

    public function getAwards($idPerson){
        $query = "CALL p_personAwards($idPerson)";
        return $this->resultQuestion($query);
    }
}