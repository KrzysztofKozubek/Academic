<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-05
 * Time: 01:26
 */
include 'views/view.php';

class UserView extends View{

    public function index($idUser, $idFriend){

        $this->model = $this->loadModel('user');

        $this->set('user', $this->model->getInformation($idFriend));

        $this->set('comments', $this->model->getComments($idFriend));

        $this->set('friends', $this->model->getFriends($idFriend));

        $this->set('isFriend', $this->model->isFriends($idUser, $idFriend));

        $this->render('head');

        $this->render('../user/templates/user');

        $this->render('footer');

    }

    public function addFriend($idMy, $idFriend){

        $this->model = $this->loadModel('user');

        $this->model->addFriend($idMy, $idFriend);

        header("Location: ?id=$idFriend");
    }

    public function removeFriend($idMy, $idFriend){

        $this->model = $this->loadModel('user');

        $this->model->removeFriend($idMy, $idFriend);

        header("Location: ?id=$idFriend");
    }
}