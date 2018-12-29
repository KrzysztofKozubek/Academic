<?php
session_start();
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 14:41
 */

if(isset($_GET['search'])){

    $search = ($_GET['search'] );

    if(isset($_GET['site']))    $site   = ($_GET['site'] - 1) * 20;
    else                        $site   = 0;
    if(isset($_GET['genre']))   $genre  = ($_GET['genre']);
    else                        $genre  = "All";
    if(isset($_GET['mark']))    $mark   = ($_GET['mark']);
    else                        $mark   = "All";
    if(isset($_GET['sort']))    $sort   = ($_GET['sort']);
    else                        $sort   = "All";

    include 'controller/movies.php';

    $ob = new MoviesController();

    $ob->search($search, $genre, $mark, $sort, $site, 50);
}else {
    if (isset($_GET['genre'])) {

        $search = "";

        if (isset($_GET['site'])) $site = ($_GET['site'] - 1) * 20;
        else                        $site = 0;
        if (isset($_GET['genre'])) $genre = ($_GET['genre']);
        else                        $genre = "All";
        if (isset($_GET['mark'])) $mark = ($_GET['mark']);
        else                        $mark = "All";
        if (isset($_GET['sort'])) $sort = ($_GET['sort']);
        else                        $sort = "All";

        include 'controller/movies.php';

        $ob = new MoviesController();

        $ob->search($search, $genre, $mark, $sort, $site, 50);
    } else {
        if (isset($_GET['mark'])) {

            $search = "";

            if (isset($_GET['site'])) $site = ($_GET['site'] - 1) * 20;
            else                        $site = 0;
            if (isset($_GET['genre'])) $genre = ($_GET['genre']);
            else                        $genre = "All";
            if (isset($_GET['mark'])) $mark = ($_GET['mark']);
            else                        $mark = "All";
            if (isset($_GET['sort'])) $sort = ($_GET['sort']);
            else                        $sort = "All";

            include 'controller/movies.php';

            $ob = new MoviesController();

            $ob->search($search, $genre, $mark, $sort, $site, 50);
        } else {
            if (isset($_GET['sort'])) {

                $search = "";

                if (isset($_GET['site'])) $site = ($_GET['site'] - 1) * 20;
                else                        $site = 0;
                if (isset($_GET['genre'])) $genre = ($_GET['genre']);
                else                        $genre = "All";
                if (isset($_GET['mark'])) $mark = ($_GET['mark']);
                else                        $mark = "All";
                if (isset($_GET['sort'])) $sort = ($_GET['sort']);
                else                        $sort = "All";

                include 'controller/movies.php';

                $ob = new MoviesController();

                $ob->search($search, $genre, $mark, $sort, $site, 50);
            }
        }
    }
}
?>

