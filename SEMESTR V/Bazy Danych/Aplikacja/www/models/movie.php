<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 11:44
 */

include_once 'models/model.php';


class MovieModel extends Model
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

    public function getMovie($id){
        $query = "CALL `p_movie`($id)";
        return $this->resultQuestion($query);
    }

    public function getActorMovie($id){
        $query = "CALL `p_actorMovie`($id)";
        return $this->resultQuestion($query);
    }

    public function getCreatorMovie($id){
        $query = "CALL `p_creatorMovie`($id)";
        return $this->resultQuestion($query);
    }

    public function getComments($id){
        $query = "CALL `p_comments`($id)";
        return $this->resultQuestion($query);
    }

    public function getAwards($idMovie){
        $query = "CALL `p_movieAwards`($idMovie)";
        return $this->resultQuestion($query);
    }

    public function addComment($idUser, $idMovie, $mark, $description){
        $date = date("Y/m/d H:i");
        //echo $date;

        $query = "INSERT INTO `comment` (`mark`, `description`, `date`) SELECT '$mark', '$description', '$date' FROM dual WHERE NOT EXISTS (SELECT * FROM `comment` WHERE `description` = '$description')";
        $this->executeQuery($query);

        $query  = "SELECT id FROM `comment` WHERE description = '$description'";
        $result = $this->resultQuestion($query);
        $idComment = $result[0]['id'];
        $query = "INSERT INTO `commentmovie`(`idMovie`, `idUser`, `idComment`) SELECT '$idMovie', '$idUser', '$idComment' FROM dual WHERE NOT EXISTS (SELECT * FROM `commentmovie` WHERE `idMovie` = '$idMovie' AND `idUser` = '$idUser' AND `idComment` = '$idComment')";
        $this->executeQuery($query);
    }
}