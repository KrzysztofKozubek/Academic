<?php

include 'controller/controller.php';

class MovieController extends Controller
{

    public function movie()
    {
        $view = $this->loadView('Movie');
        $view->index();
    }

    public function search($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer, $from = 0, $to = 15)
    {
        $view = $this->loadView('Movie');
        $view->search($name, $wor, $typ, $datF, $datT, $ratF, $ratT, $lonF, $lonT, $wer, $from, $to);
    }


}