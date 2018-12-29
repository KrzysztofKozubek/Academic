<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 22:26
 */
require 'ip.php';
?>

<div class="container">

    <div class="row">

        <div class="row text-center">

            <div class="jumbotron text-center form-inline">
                <form class="form-inline" method="get" action="?person&&">

                    <input name="site" value="1" type="hidden"/>

                    <div class="form-group form-inline">
                        <span class="btn-lg">Szukaj aktora</span>
                        <?php if(isset($_GET['search'])) $search = $_GET['search']; else $search = ""; ?>
                        <label class="sr-only" for="phrase">Fraza</label>
                        <input type="text" name="search" class="form-control" id="phrase" placeholder="Wpisz imię lub nazwisko aktora" value="<?php echo $search; ?>">
                        <input type="hidden" name="person" value="">
                        <button type="submit" class="btn btn-default">Szukaj</button>
                    </div>

                </form>
            </div>

            <?php
            if($this->get('persons')){
                foreach($this->get('persons') as $person){
                    ?>
                    <div class="col-lg-2">
                        <a href="<?php echo $ip; ?>/?person=<?php echo $person['id']; ?>" class="thumbnail">
                            <div style="background-image:url(<?php echo $person['photo']; ?>);" class="image"></div>
                            <h4 class="text-center"><?php echo $person['name']; ?></h4>
                        </a>
                    </div><!--/col-lg-2-->
                    <?php
                }
            }
            ?>
        </div><!--/row-->

        <nav>
            <ul class="pagination">
<!--                <li>-->
<!--                    <a href="#" aria-label="Previous">-->
<!--                        <span aria-hidden="true">&laquo;</span>-->
<!--                    </a>-->
<!--                </li>-->

                <?php
                $number = ceil($this->get('site'));
                if(isset($_GET['site']))
                    $i = $_GET['site'] - 5;
                else
                    $i = 1;
                if($i < 1) $i = 1;
                $j = $i + 10;
                for(; $i <= $number && $i <= $j; $i++){
                    echo"<li><a href=\"$ip/?person&&site=$i&search=$search\">$i</a></li>";
                }
                ?>
<!--                <li>-->
<!--                    <a href="#" aria-label="Next">-->
<!--                        <span aria-hidden="true">&raquo;</span>-->
<!--                    </a>-->
<!--                </li>-->
            </ul>
        </nav>

    </div><!--/row-->