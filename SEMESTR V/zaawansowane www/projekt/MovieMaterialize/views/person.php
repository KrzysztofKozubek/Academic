<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 11:42
 */

include_once 'views/view.php';

class PersonView extends View{

    public function index($idPerson){

        $this->model = $this->loadModel('person');

        $model = $this->model;

        $this->set('person', $model->getPerson($idPerson));

        $this->set('beActor', $model->getMovieWhereBeActor($idPerson));

        $this->set('beCreator', $model->getMovieWhereBeCreator($idPerson));

        $this->set('awards', $model->getAwards($idPerson));

        $this->render('head');

        $this->render('person');

        $this->render('footer');

    }
}