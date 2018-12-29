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
        echo "index";
    }

    public function movies($from = 0, $number = 20){
        $this->view=$this->loadView('movies');
        $this->view->movie($from, $number);
        echo "movies";
    }

    public function persons($search="", $from = 0, $number = 20){
        $this->view=$this->loadView('movies');
        $this->view->persons($search, ($from - 1) * $number, $number);
        echo "persons";
    }

    public function search($search, $genre, $mark, $sort, $from=0, $number=20){
        $this->view=$this->loadView('movies');
        $this->view->search($search, $genre, $mark, $sort, $from, $number);
        echo "search";
    }


}