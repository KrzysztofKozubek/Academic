<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 11:42
 */

include_once 'views/view.php';

class MovieView extends View{

    public function movie($id){

        $this->model = $this->loadModel('movie');

        $model = $this->model;

        $this->set('movie',     $model->getMovie($id));

        $this->set('actors',    $model->getActorMovie($id));

        $this->set('creators',  $model->getCreatorMovie($id));

        $this->set('comments',  $model->getComments($id));

        $this->set('awards',    $model->getAwards($id));

        $this->render('head');

        $this->render('movie');

        $this->render('footer');

    }

    public function addComment($idUser, $idMovie, $mark, $comment){
        $this->model->addComment($idUser, $idMovie, $mark, $comment);
    }
}