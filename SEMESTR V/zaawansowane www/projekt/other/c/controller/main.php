<?php

include 'controller/controller.php';

class MainController extends Controller{

    public function index(){
        $view=$this->loadView('Main');
        $view->index();
    }


}