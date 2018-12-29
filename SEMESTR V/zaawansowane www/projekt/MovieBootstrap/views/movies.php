<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 14:54
 */

include 'views/view.php';

class MoviesView extends View{

    public function index(){
        $this->movie();
    }

    public function movie($from = 0, $number = 20){

        $art=$this->loadModel('movies');

        $this->set('movies', $art->getMovies($from, $number));

        $this->set('genres', $art->getGenres());

        $this->set('site', $art->getCount());

        $this->render('head');

        $this->render('browser');

        $this->render('footer');

    }

    public function persons($search="", $from = 0, $number = 20){

        $art=$this->loadModel('movies');

        $this->set('persons', $art->getPersons($search, $from, $number));

        $this->set('site', $art->getCountPerson($search, "person"));

        $this->render('head');

        $this->render('persons');

        $this->render('footer');

    }

    public function search($search, $genre, $mark, $sort, $from = 0, $number = 20){

        $art=$this->loadModel('movies');

        $this->set('movies', $art->getSearchMovies($search, $genre, $mark, $sort, $from, $number));

        $this->set('genres', $art->getGenres());

        $this->set('site', $art->getSearchCount($search, $genre, $mark));

        $this->render('head');

        $this->render('browser');

        $this->render('footer');

    }
}