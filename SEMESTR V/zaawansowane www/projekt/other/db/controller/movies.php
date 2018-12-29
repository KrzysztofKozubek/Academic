<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 14:45
 */

include 'controller/controller.php';

class MoviesController extends Controller{

    public function index(){
        $this->movies();
    }

    public function movies(){
        $view=$this->loadView('Movies');
        $view->index();
    }

    public function search($title, $releaseDate, $lenght, $ganre, $from=0, $to=15){
        $view=$this->loadView('Movies');
        $view->search($title, $releaseDate, $lenght, $ganre, $from, $to);
    }


}