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

    public function movie(){

        $art=$this->loadModel('Movies');

        $this->set('movies', $art->getMovie());

        $this->set('category', $art->getGenres());

        $this->render('head');

        $this->render('navbarMovie');

        $this->render('container');

        $this->render('skrypt');

    }

    public function search($title, $releaseDate, $lenght, $ganre, $from, $to){

        $art=$this->loadModel('Movie');

        $this->set('movies', $art->getSearchMovies($title, $releaseDate, $lenght, $ganre, $from, $to));

        $this->set('category', $art->getCategory());

        $this->render('header');

        $this->render('navbarMovie');

        $this->render('container');

        $this->render('skrypt');

    }
}