<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-05
 * Time: 01:26
 */
include_once 'models/model.php';


class UserModel extends Model{


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

    public function registion($name, $email, $age, $sex, $password){
        $query = "INSERT INTO `user` (`name`, `email`, `age`, `sex`, `password`) SELECT '$name', '$email', '$age', '$sex', '$password' FROM dual WHERE NOT EXISTS (SELECT `email` FROM `user` WHERE `email` LIKE '$email')";
        $this->login($email, $password);
    }

    public function getInformation($idUser){
        $query = "SELECT * FROM `user` WHERE id = '$idUser'";
        $result = $this->resultQuestion($query);

        return $result[0];
    }

    public function getComments($idUser){
        $query = "SELECT m.title, m.poster, m.id, c.description, c.mark FROM `commentmovie` cm, `comment` c, `movie` m  WHERE cm.idUser = '$idUser' AND c.id = cm.idComment AND m.id = cm.idMovie";
        $result = $this->resultQuestion($query);

        return $result;
    }

    public function getFriends($idUser){
        //$query = "SELECT * FROM `user` u WHERE u.id = '$idUser'";


        $query = "SELECT * FROM `user` u, `friendUser` fu WHERE fu.idUser = '$idUser' AND fu.idFriend = u.id";
        $result = $this->resultQuestion($query);

        return $result;
    }

    public function isFriends($idMy, $idUser){
        $query = "SELECT * FROM `friendUser` WHERE idUser = '$idMy' AND idFriend = '$idUser'";
        $result = $this->resultQuestion($query);

        return $result;
    }

    public function addFriend($idMy, $idFriend){
        $query = "INSERT INTO `friendUser`(`idUser`, `idFriend`) SELECT '$idMy', '$idFriend' FROM dual WHERE NOT EXISTS (SELECT `idUser`, `idFriend` FROM `friendUser` WHERE `idUser` LIKE '$idMy' AND `idFriend` = '$idFriend')";
        $this->executeQuery($query);
    }

    public function removeFriend($idMy, $idFriend){
        $query = "DELETE FROM `friendUser` WHERE `idUser` = '$idMy' AND `idFriend` = '$idFriend'";
        echo $query;
        $this->executeQuery($query);
    }
}