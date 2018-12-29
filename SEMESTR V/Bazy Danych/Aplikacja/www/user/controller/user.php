<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-05
 * Time: 01:26
 */
include_once "controller.php";

class UserController extends Controller{

    public function index($idUser, $idFriend){
        $this->view = $this->loadView('user');
        $this->view->index($idUser, $idFriend);
    }

    public function addFriend($idMy, $idFriend){
        $this->view = $this->loadView('user');
        $this->view->addFriend($idMy, $idFriend);
    }

    public function removeFriend($idMy, $idFriend){
        $this->view = $this->loadView('user');
        $this->view->removeFriend($idMy, $idFriend);
    }
}