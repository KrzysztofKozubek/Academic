<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-05
 * Time: 01:26
 */
$ip = 'http://' . $_SERVER ["SERVER_NAME"];

$user       = $this->get('user');
$comments   = $this->get('comments');
$friends    = $this->get('friends');
$isFriend   = $this->get('isFriend');
$isFriend   = $isFriend[0]['idFriend'];

?>

<div class="container">

    <div class="row">

        <div class="jumbotron form-inline">

            <h3 class="title"><?php echo $user['name']; ?></h3>


            <div class="row">

                <div class="information well">
                    <div class="gratePremier ">
                        <p class="label-success">Nazwa: <?php echo $user['name']; ?></p>
                        <p class="label-danger">Email: <?php echo $user['email']; ?></p>
                        <p class="label-warning">Wiek: <?php echo $user['age']; ?></p>
                        <?php
                        if(isset($_GET['id']))
                            if(empty($isFriend))
                                echo "<p class=\"label-info\">Dodaj do obserwowanych: <a href=\"$ip/user/?id=$_GET[id]&add\">Dodaj</a></p>";
                            else
                                echo "<p class=\"label-info\">Usuń z obserwowanych: <a href=\"$ip/user/?id=$_GET[id]&remove\">Usuń</a></p>";

                        ?>
                    </div>
                </div><!--/.col-xs-6.col-lg-4-->

            </div><!--/row-->


            <div class="bs-example">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#sectionA">Komentarze</a></li>
                    <li><a data-toggle="tab" href="#sectionB">Znajomi</a></li>
                </ul>
                <div class="tab-content">
                    <div id="sectionA" class="tab-pane fade in active">
                        <?php
                        if(!empty($comments)) {
                            ?>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Plakat</th>
                                    <th>Film</th>
                                    <th>Ocena</th>
                                    <th>Komentarz</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php
                                foreach ($comments as $comment) {
                                    echo "<tr>
                                        <td><a href='$ip/?id=$comment[id]'> <img src='$comment[poster]' class='min-poster'/></a></a></td>
                                        <td><a href='$ip/?id=$comment[id]'> $comment[title]</a></td>
                                        <td>$comment[mark]</td>
                                        <td>$comment[description]</td>
                                        </tr>";
                                }
                                ?>
                                </tbody>
                            </table>
                            <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak Ocenianych Filmów</strong></div>";
                        }
                        ?>
                    </div>
                    <div id="sectionB" class="tab-pane fade">
                        <?php
                        if(!empty($friends)){
                            ?>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Nazwa</th>
                                    <th>Wiek</th>
                                    <th>Email</th>
                                </tr>
                                </thead>
                                <tbody>
                                <?php
                                foreach($friends as $friend){
                                    echo "<tr>
                                        <td><a href='$ip/user/?id=$friend[id]'>$friend[name]</a></td>
                                        <td>$friend[age]</td>
                                        <td>$friend[email]</td>
                                        </tr>";
                                }
                                ?>
                                </tbody>
                            </table>
                            <?php
                        }else{
                            echo "<div class=\"alert alert-warning\"><strong><span class=\"glyphicon glyphicon-send\"></span>Brak Znajomych</strong></div>";
                        }
//                        ?>
                    </div>
                </div>
            </div>



        </div>
    </div><!--/row-->
