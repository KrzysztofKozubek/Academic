<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 11:40
 */
include_once "controller.php";

class PersonController extends Controller{

    public function index($idPerson){
        $this->view=$this->loadView('person');
        $this->view->index($idPerson);
    }
}