<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-04
 * Time: 00:01
 */
include 'views/view.php';

class SignView extends View{

    public function sign(){

        $this->render('head');

        $this->render('../user/templates/sign');

        $this->render('footer');

    }

    public function registion($name, $email, $age, $sex, $password){
        $model=$this->loadModel('sign');
        $model->registion($name, $email, $age, $sex, $password);
    }

    public function login($email, $password){
        $model=$this->loadModel('sign');
        $model->login($email, $password);
    }
}