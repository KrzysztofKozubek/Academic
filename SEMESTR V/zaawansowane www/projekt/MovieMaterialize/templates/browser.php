<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 22:26
 */
require 'ip.php';
?>
<div id="intro" class="section scrollspy">
    <section class="container">
        <dt class="row">
        <div class="col s12"><h2 class="center header text_h2">Znajdz film którego szukasz. Wyniki są ograniczone
                do<span class="span_h2"> 50</span> wyników.</h2></div>
        <article class="col s12 m4 l4">
            <section class="center promo promo-example">
                <ul id="dropdown1" class="dropdown-content">
                    <?php

                    foreach($this->get('genres') as $row){
                        $g = $row['genre'];
                        echo "<li><a href=\"$ip/?genre=$g\">$g<span class=\"badge\"></span></a></li>";
                    }
                    ?>
                </ul>
                <a href="#!" data-activates="dropdown1" class="btn dropdown-button">Kategoria<i class="right"></i></a>
            </section>
        </article>
        <article class="col s12 m4 l4">
            <section class="center promo promo-example">
                <ul id="dropdown2" class="dropdown-content">

                    <?php
                    for($i = 9; $i > 0; $i--){
                        echo "<li><a href=\"$ip/?mark=$i\">$i +</a></li>";
                    }
                    ?>
                </ul>
                <a href="#!" data-activates="dropdown2" class="btn dropdown-button">Ocena<i class="right"></i></a>
            </section>
        </article>
        <article class="col s12 m4 l4">
            <section class="center promo promo-example">
                <ul id="dropdown3" class="dropdown-content">
                    <li><a href="<?php echo $ip; ?>/?sort=Najnowsze">Najnowsze</a></li>
                    <li><a href="<?php echo $ip; ?>/?sort=Najstarsze">Najstarsze</a></li>
                    <li><a href="<?php echo $ip; ?>/?sort=Oceny Max">Oceny Max</a></li>
                    <li><a href="<?php echo $ip; ?>/?sort=Oceny Min">Oceny Min</a></li>
                    <li><a href="<?php echo $ip; ?>/?sort=A - Z">A - Z</a></li>
                    <li><a href="<?php echo $ip; ?>/?sort=Z - A">Z - A</a></li>
                </ul>
                <a href="#!" data-activates="dropdown3" class="btn dropdown-button">Sortuj po</a></section>
        </article>
        </dt></section>
</div>

<div id="work" class="section scrollspy">
    <section class="container"><h2 class="header text_b">Znalezione filmy</h2>
        <dt class="row">

            <?php
            if($this->get('movies')){
            foreach($this->get('movies') as $movie){
            ?>

            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="<?php echo $movie['poster']; ?>" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4"><?php echo $movie['title']; ?></span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4"><?php echo $movie['title']; ?><i
                                class="mdi-navigation-close right"></i></span>
                        <p><?php echo $movie['description']; ?></p></div>
                </article>
            </section>

            <?php
            }
            }
            ?>

        </dt>
    </section>

