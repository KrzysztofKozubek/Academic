<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 11:40
 */
include_once "controller.php";

class MovieController extends Controller{

    public function movie($id){
        $this->view=$this->loadView('movie');
        $this->view->movie($id);
    }

    public function addComment($idUser, $idMovie, $mark, $comment){
        $this->view->addComment($idUser, $idMovie, $mark, $comment);
    }
}