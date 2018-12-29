<?php

include 'views/view.php';

class MainView extends View{

    public function index(){
        $art=$this->loadModel('Main');
        $this->set('carousel', $art->getAll());
        $this->render('header');
        $this->render('navbar');
        $this->render('carousel');
        $this->set('contaner', $art->getMovie());
        $this->render('contaner');
        $this->render('skrypt');
    }
}