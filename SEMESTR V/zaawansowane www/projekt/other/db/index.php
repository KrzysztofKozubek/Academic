<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 14:41
 */

if (isset($_GET['s']) || isset($_GET['id'])) {

    if ((@$_GET['s']) == 'movie' || (@$_GET['s']) == 'search') {

        include 'controller/movie.php';

        $ob = new MovieController();

        //$ob->index(@$_GET['q']);



        @$ob->$_GET['s']($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'], $_GET['site'], $_GET['sort'], $_GET['how']);

    }

    elseif (@($_GET['s']) == 'watch' || isset($_GET['id'])) {

        include 'controller/watch.php';

        $ob = new WatchController();

        $ob->watch($_GET['id']);



    } else {

        include 'controller/main.php';

        $ob = new MainController();

        $ob->index();

    }

} else {

    include 'controller/movies.php';

    $ob = new MoviesController();

    $ob->index();

}

?>

