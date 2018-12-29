<?php

include 'controller/controller.php';

class WatchController extends Controller
{


    public function movie()
    {
        watch();
    }

    public function watch($id = null)
    {
        if($id == null || !is_numeric($id)) $id = rand(1, 28448);
        $view = $this->loadView('Watch');
        $view->watch($id);
    }


}

?>