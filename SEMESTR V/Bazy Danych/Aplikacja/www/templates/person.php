<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 12:21
 */
$ip = 'http://' . $_SERVER ["SERVER_NAME"];

$person      = $this->get('person');
$beActor     = $this->get('beActor');
$beCreator   = $this->get('beCreator');
$awards      = $this->get('awards');

$person     = $person[0];
?>


<div class="container">

    <div class="row">

        <div class="jumbotron form-inline">

            <h3 class="title"><?php echo $person['name']; ?></h3>


            <div class="row">

                <div class="information well">
                    <div class="col-lg-2">
                        <div style="background-image:url(<?php echo $person['photo']; ?>);" class="poster"></div>
                    </div>
                    <div class="gratePremier ">
                        <p class="label-success">Imię nazwisko: <?php echo $person['name']; ?></p>
                        <p class="label-danger">Data urodzenia: <?php echo $person['dateOfBirth']; ?></p>
                    </div>
                </div><!--/.col-xs-6.col-lg-4-->

            </div><!--/row-->

            <div class="bs-example">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#sectionA">Filmy w których grał</a></li>
                    <li><a data-toggle="tab" href="#sectionB">Filmy które tworzył</a></li>
                    <li><a data-toggle="tab" href="#sectionC">Nagrody</a></li>
                </ul>
                <div class="tab-content">
                    <div id="sectionA" class="tab-pane fade in active">
                        <?php
                        if(!empty($beActor)) {
                            ?>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Zdjęcie</th>
                                    <th>Tytuł filmu</th>
                                    <th>Postać w filmie</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php
                                foreach ($beActor as $actor) {
                                    echo "<tr>
                                        <td><a href='$ip/?id=$actor[id]'><img src='$actor[poster]' class='min-poster'/></a></td>
                                        <td><a href='$ip/?id=$actor[id]'>$actor[title]</a></td>
                                        <td>$actor[participation]</td>
                                        </tr>";
                                }
                                ?>
                                </tbody>
                            </table>
                            <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak filmów w których występował jako aktor</strong></div>";
                        }
                        ?>
                    </div>
                    <div id="sectionB" class="tab-pane fade">
                        <?php
                        if(!empty($beCreator)){
                            ?>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Zdjęcie</th>
                                    <th>Tytuł filmu</th>
                                    <th>Udział w filmie</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php
                                foreach($beCreator as $creator){
                                    echo "<tr>
                                        <td><a href='$ip/?id=$creator[id]'><img src='$creator[poster]' class='min-poster'/></a></td>
                                        <td><a href='$ip/?id=$creator[id]'>$creator[title]</a></td>
                                        <td>$creator[participation]</td>
                                        </tr>";
                                }
                                ?>
                                </tbody>
                            </table>
                            <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak filmów w których tworzył film</strong></div>";
                        }
                        ?>
                    </div>
                    <div id="sectionC" class="tab-pane fade">
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
                                    if(!empty($award['idMovie']))
                                        echo "<td><a href='$ip/?id=$award[idMovie]'>$award[info]</a></td>";
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
