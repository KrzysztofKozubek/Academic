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
        $query = "SELECT * FROM movie WHERE id = $id";
        return $this->resultQuestion($query);
    }

    public function getAvartMovie($id){
        $query = "SELECT * FROM award WHERE idMovie = $id";
        return $this->resultQuestion($query);
    }

    public function getActorMovie($id){
        $query = "SELECT * FROM `creator` c, `person` p WHERE c.idPerson = p.id AND c.creator = 0 and c.idMovie = $id";
        return $this->resultQuestion($query);
    }

    public function getCreatorMovie($id){
        $query = "SELECT * FROM `creator` c, `person` p WHERE c.idPerson = p.id AND c.creator = 1 and c.idMovie = $id";
        return $this->resultQuestion($query);
    }

    public function getComments($id){
        $query = "SELECT * FROM `commentmovie` cm, `comment` c, `user` u WHERE cm.idMovie = '$id' AND cm.idUser = u.id AND cm.idComment = c.id";
        return $this->resultQuestion($query);
    }

    public function addComment($idUser, $idMovie, $mark, $description){
        $date = date("Y/m/d H:i");

        $query = "INSERT INTO `comment` (`mark`, `description`, `date`) SELECT '$mark', '$description', '$date' FROM dual WHERE NOT EXISTS (SELECT * FROM `comment` WHERE `description` = '$description')";
        $this->executeQuery($query);

        $query  = "SELECT id FROM `comment` WHERE description = '$description'";
        $result = $this->resultQuestion($query);
        $idComment = $result[0]['id'];
        $query = "INSERT INTO `commentmovie`(`idMovie`, `idUser`, `idComment`) SELECT '$idMovie', '$idUser', '$idComment' FROM dual WHERE NOT EXISTS (SELECT * FROM `commentmovie` WHERE `idMovie` = '$idMovie' AND `idUser` = '$idUser')";
        $this->executeQuery($query);
    }

    public function getAwards($idMovie){
        $query = "SELECT * FROM `award` a WHERE a.idMovie = $idMovie ORDER BY a.date, a.won DESC";
        return $this->resultQuestion($query);
    }

    public function getVideos($idMovie){
        $query = "SELECT * FROM `video` v WHERE v.idMovie = $idMovie ORDER BY v.version ASC";
        return $this->resultQuestion($query);
    }

    public function getScens($idMovie){
        $query = "SELECT * FROM `galleryscene` s WHERE s.idMovie = $idMovie ORDER BY s.id ASC";
        return $this->resultQuestion($query);
    }
}