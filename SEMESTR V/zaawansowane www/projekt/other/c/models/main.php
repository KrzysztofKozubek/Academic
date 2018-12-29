<?php

include 'models/model.php';

class MainModel extends Model
{

    public function index()
    {
        $cat = $this->loadModel('main');
        $this->set('main', $cat->getAll());
        //$this->render('indexVategory');
    }

    public function getAll() {
        $query="SELECT id, photo, name FROM movie LIMIT 0, 12";
        $this->pdo->query ('SET NAMES utf8');
        $select=$this->pdo->query($query);
        foreach ($select as $row) {
            $data[]=$row;
        }
        $select->closeCursor();

        return $data;
    }

    public function getMovie($from = 0, $to = 18, $query = null){

        $query="SELECT id, photo, name, description FROM movie ORDER BY premier DESC LIMIT $from, $to";
        $this->pdo->query ('SET NAMES utf8');
        $select=$this->pdo->query($query);
        foreach ($select as $row) {
            $data[]=$row;
        }
        $select->closeCursor();

        return $data;
    }
}