<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 12:21
 */
require 'ip.php';

$movie      = $this->get('movie');
$awords     = $this->get('awards');
$actors     = $this->get('actors');
$creators   = $this->get('creators');
$comments   = $this->get('comments');
$awards      = $this->get('awards');


$movie      = $movie[0];
$awords     = $actors[0];

?>

<div class="container">

    <div class="row">

        <div class="jumbotron form-inline">

            <h3 class="title"><?php echo $movie['title']; ?></h3>


            <div class="row">

                <div class="information well">
                    <div class="col-lg-2">
                        <div style="background-image:url(<?php echo $movie['poster']; ?>);" class="poster"></div>
                    </div>
                    <div class="gratePremier ">
                        <p class="label-success">Gatunek: <?php echo $movie['genre']; ?></p>
                        <p class="label-danger">Premiera: <?php echo $movie['releaseDate']; ?></p>
                        <p class="label-warning">Ocena: <?php echo $movie['mark']; ?></p>
                        <p class="label-info">Czas trwania: <?php echo $movie['length']; ?> min</p>
                    </div>
                </div><!--/.col-xs-6.col-lg-4-->

            </div><!--/row-->

            <div class="bs-example">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#sectionA">Opis</a></li>
                    <li><a data-toggle="tab" href="#sectionB">Aktorzy</a></li>
                    <li><a data-toggle="tab" href="#sectionC">Twórcy</a></li>
                    <li><a data-toggle="tab" href="#sectionD">Komentarze</a></li>
                    <li><a data-toggle="tab" href="#sectionE">Nagrody</a></li>
                </ul>
                <div class="tab-content">
                    <div id="sectionA" class="tab-pane fade in active">
                        <h3><?php echo $movie['title']; ?></h3>
                        <p><?php echo $movie['description']; ?></p>
                    </div>
                    <div id="sectionB" class="tab-pane fade">
                        <?php
                        if(!empty($actors)) {
                            ?>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Zdjęcie</th>
                                    <th>Imie nazwisko</th>
                                    <th>Postać w filmie</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php
                                foreach ($actors as $actor) {
                                    echo "<tr>
                                        <td><a href='$ip/?person=$actor[id]'><img src='$actor[photo]' class='min-poster'/></a></td>
                                        <td><a href='$ip/?person=$actor[id]'>$actor[name]</a></td>
                                        <td>$actor[participation]</td>
                                        </tr>";
                                }
                                ?>
                                </tbody>
                            </table>
                            <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak aktorów</strong></div>";
                        }
                        ?>
                    </div>
                    <div id="sectionC" class="tab-pane fade">
                        <?php
                        if(!empty($creators)){
                        ?>
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Zdjęcie</th>
                                <th>Imie nazwisko</th>
                                <th>Udział w filmie</th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            foreach($creators as $creator){
                                echo "<tr>
                                        <td><a href='$ip/?person=$creator[id]'><img src='$creator[photo]' class='min-poster'/></a></td>
                                        <td><a href='$ip/?person=$creator[id]'>$creator[name]</a></td>
                                        <td>$creator[participation]</td>
                                        </tr>";
                            }
                            ?>
                            </tbody>
                        </table>
                        <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak twórców</strong></div>";
                        }
                        ?>
                    </div>
                    <div id="sectionD" class="tab-pane fade">
                        <?php
                        if(!empty($comments)) {
                            ?>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Urzytkownik</th>
                                    <th>Ocena</th>
                                    <th>Komentarz</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php

                                foreach ($comments as $comment) {
                                    echo "<tr>
                                        <td><a href='$ip/user/?id=$comment[id]'>$comment[name]</a></td>
                                        <td>$comment[mark]</td>
                                        <td>$comment[description]</td>
                                        </tr>";
                                }

                                ?>
                                </tbody>
                            </table>
                            <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak komentzarzy</strong></div>";
                        }

                        if(isset($_SESSION['id'])){
                            echo "<form action=\"$ip/?id=$_GET[id]\" method=\"POST\">
                                    <div class=\"form-group\">
                                        <label for=\"sel0\">Dodaj komentarz:</label><br>
                                        <input type=\"hidden\" name=\"idUser\" value=\"$_SESSION[id])\" />
                                        <input type=\"hidden\" name=\"idMovie\" value=\"$_GET[id]\" />
                                        <label for=\"sel1\">Ocena:</label>
                                        <select name=\"mark\" class=\"form-control\" id=\"sel1\">
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                            <option>6</option>
                                            <option>7</option>
                                            <option>8</option>
                                            <option>9</option>
                                            <option>10</option>
                                        </select>
                                        <br>
                                        <label for=\"comment\">Komentarz:</label><br>
                                        <textarea name='comment' class=\"form-control col-lg-12\" rows=\"4\" cols=\"70\" id=\"comment\"></textarea><br>
                                        <input type='submit' value='Dodaj komentarz' class='btn btn-primary'/>
                                    </div>
                                </form>";
                        }
                        ?>
                    </div>
                    <div id="sectionE" class="tab-pane fade">
                        <?php
                        if(!empty($awards)){
                            ?>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Rok</th>
                                    <th>Categoria</th>
                                    <th>Dodatkowe informacje</th>
                                    <th>Wygrał</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php
                                foreach($awards as $award){
                                    echo "<tr>
                                        <td>$award[date]</td>
                                        <td>$award[description]</td>";
                                    if(!empty($award['idPerson']))
                                        echo "<td><a href='$ip/?person=$award[idPerson]'>$award[info]</a></td>";
                                    else
                                        echo "<td>$award[info]</td>";
                                        echo "<td>$award[won]</td>
                                        </tr>";
                                }
                                ?>
                                </tbody>
                            </table>
                            <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak nagród oraz nominacji</strong></div>";
                        }
                        ?>
                    </div>
                </div>
            </div>



        </div>
    </div><!--/row-->
