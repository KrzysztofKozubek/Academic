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

        <div class="jumbotron text-center form-inline">
            <form class="form-inline" method="get" action="?">

                <input name="site" value="1" type="hidden"/>

                <div class="form-group form-inline">
                    <span class="btn-lg">Fraza</span>
                    <?php if(isset($_GET['search'])) $search = $_GET['search']; else $search = ""; ?>
                    <label class="sr-only" for="phrase">Fraza</label>
                    <input type="text" name="search" class="form-control" id="phrase" placeholder="Szukaj" value="<?php echo $search; ?>">

                    <button type="submit" class="btn btn-default">Szukaj</button>
                </div>

                <div class="form-inline">
                    <!--Genres-->
                    <div class="form-group">
                        <span>Garunek</span>
                        <label class="sr-only" for="grade">Gatunek</label>
                        <select name="genre" class="form-control selectpicker show-menu-arrow show-tick" id="grade" placeholder="Gatunek" data-size="7">
                            <option data-content="<span class='label label-default'>All</span>">All</option>
                            <?php
                            if(isset($_GET['genre']))
                                $genre = $_GET['genre'];
                            else
                                $genre = "";
                            foreach($this->get('genres') as $row){
                                $g = $row['genre'];
                                if($genre == $g) {
                                    echo "<option selected=\"selected\" data-content=\"<span class='label label-success'>$g</span>\">$g</option>";
                                }
                                else{
                                    echo "<option data-content=\"<span class='label label-success'>$g</span>\">$g</option>";
                                }
                            }
                            ?>
                        </select>
                    </div>
                    <!--Mark-->
                    <div class="form-group">
                        <span>Ocena</span>
                        <label class="sr-only" for="mark">Ocena</label>
                        <select name="mark" class="form-control selectpicker show-menu-arrow show-tick" id="mark" placeholder="Ocena">
                            <option data-content="<span class='label label-default'>All</span>">All</option>
                            <?php
                            if(isset($_GET['mark']))
                                $mark = $_GET['mark'];
                            else
                                $mark = "";
                            for($i = 9; $i > 0; $i--){
                                if($mark == $i){
                                    echo "<option selected=\"selected\" data-content=\"<span class='label label-warning'>$i+</span>\">$i</option>";
                                }else{
                                    echo "<option data-content=\"<span class='label label-warning'>$i+</span>\">$i</option>";
                                }
                            }
                            ?>
                        </select>
                    </div>
                    <!--Sort-->
                    <div class="form-group">
                        <span>Sortuj</span>
                        <?php
                            if(isset($_GET['sort']))
                                $sort = $_GET['sort'];
                            else
                                $sort = "";
                        ?>
                        <label class="sr-only" for="sort">Sortuj</label>
                        <select name="sort" class="form-control selectpicker show-menu-arrow show-tick" id="sort" placeholder="Sortuj">
                            <option data-content="<span class='label label-default'>All</span>">All</option>
                            <optgroup label="Czas" data-max-options="2">
                                <option <?php if($sort=="Najnowsze") echo "selected=\"selected\""; ?> data-content="<span class='label label-danger'>Czas Najnowsze</span>">Najnowsze</option>
                                <option <?php if($sort=="Najstarsze") echo "selected=\"selected\""; ?> data-content="<span class='label label-danger'>Czas Najstarsze</span>">Najstarsze</option>
                            </optgroup>
                            <optgroup label="Ocena" data-max-options="2">
                                <option <?php if($sort=="Oceny Max") echo "selected=\"selected\""; ?> data-content="<span class='label label-danger'>Oceny Najlepszej</span>">Oceny Max</option>
                                <option <?php if($sort=="Oceny Min") echo "selected=\"selected\""; ?> data-content="<span class='label label-danger'>Oceny Nagorszej</span>">Oceny Min</option>
                            </optgroup>
                            <optgroup label="Alfabetycznie" data-max-options="2">
                                <option <?php if($sort=="A - Z") echo "selected=\"selected\""; ?> data-content="<span class='label label-danger'>Alfabetycznie A - Z</span>">A - Z</option>
                                <option <?php if($sort=="Z - A") echo "selected=\"selected\""; ?> data-content="<span class='label label-danger'>Alfabetycznie Z - A</span>">Z - A</option>
                            </optgroup>
                        </select>
                    </div>
                </div>
            </form>
        </div>

        <div class="row text-center">

            <?php
            if($this->get('movies')){
                foreach($this->get('movies') as $movie){
                    ?>
                    <div class="col-lg-2">
                        <a href="<?php echo $ip; ?>/?id=<?php echo $movie['id']; ?>" class="thumbnail">
                            <div style="background-image:url(<?php echo $movie['poster']; ?>);" class="image"></div>
                            <h4 class="text-center"><?php echo $movie['title']; ?></h4>
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
                    echo"<li><a href=\"$ip/?site=$i&search=$search&genre=$genre&mark=$mark&sort=$sort\">$i</a></li>";
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