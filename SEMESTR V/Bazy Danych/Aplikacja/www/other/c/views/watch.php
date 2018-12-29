<?php

include 'views/view.php';

class WatchView extends View{

    public function index($id){
        $this->movie($id);
    }

    public function watch($id){
        $art=$this->loadModel('Watch');
        $this->set('info', $art->getInformation($id));
        $this->set('link', $art->getLinkMovie($id));
        $this->render('header');
        $this->render('navbarMovie');
        $this->render('containerWatch');
        $this->render('skrypt');
    }
}