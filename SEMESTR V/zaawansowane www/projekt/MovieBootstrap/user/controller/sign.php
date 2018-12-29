<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-03
 * Time: 23:59
 */
include_once "controller.php";

class SignController extends Controller{

    public function sign(){
        $view=$this->loadView('sign');
        $view->sign();
    }

    public function registion($name, $email, $age, $sex, $password){
        $view=$this->loadView('sign');
        $view->registion($name, $email, $age, $sex, $password);
    }

    public function login($email, $password){
        $view=$this->loadView('sign');
        $view->login($email, $password);
    }
}