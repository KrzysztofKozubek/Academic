<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-04
 * Time: 00:02
 */
include_once 'models/model.php';


class SignModel extends Model{


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
        if($data==null)
            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Nie udało się utworzyć konta lub zalogować sprawdz dane i spróbuj jeszcze raz :)</strong></div>";
        return $data;
    }

    public function registion($name, $email, $age, $sex, $password){
        $query = "INSERT INTO `user` (`name`, `email`, `age`, `sex`, `password`) SELECT '$name', '$email', '$age', '$sex', '$password' FROM dual WHERE NOT EXISTS (SELECT `email` FROM `user` WHERE `email` LIKE '$email')";
        $this->executeQuery($query);
        $this->login($email, $password);
    }

    public function login($email, $password){
        $query = "SELECT id, name FROM `user` WHERE email = '$email' AND password = '$password'";
        $result = $this->resultQuestion($query);

        if(isset($result[0]['name']) && isset($result[0]['id'])){
            $date   = date('Y-m-d H:i:s');
            $id     = $result[0]['id'];
            $query = "INSERT INTO `userlog`(`idUser`, `date`) VALUES ('$id','$date')";
            $this->executeQuery($query);
            $_SESSION['id']     = $result[0]['id'];
            $_SESSION['name']   = $result[0]['name'];
        }
    }

}