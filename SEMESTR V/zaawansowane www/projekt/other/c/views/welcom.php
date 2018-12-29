<?php



/**
 * Created by PhpStorm.
 * User: kris_
 * Date: 2015-01-03
 * Time: 03:40
 */
class welcom
{


    public static function showSite($obiekt)
    {
        $ip = 'http://' . $_SERVER ["SERVER_NAME"];
        ?>
        <!DOCTYPE html >
        <html lang="en">
        <head>
            <meta charset="utf-8">
            <meta http - equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <meta name="description" content="">
            <meta name="author" content="">
            <link rel="icon" href="<?php echo "$obiekt->ip"; ?>/image/favicon.ico">

            <title> ZamulaczTV</title>

            <!--Bootstrap core CSS-->
            <link href="<?php echo "$obiekt->ip"; ?>/css/bootstrap.min.css" rel="stylesheet">

            <!--Just for debugging purposes . Don't actually copy these 2 lines! -->
            <!--[if lt IE 9]>
            <script src="<?php echo " $obiekt->ip
            ";?>/js/ie8-responsive-file-warning.js" ></script><![endif]-->
            <script src="<?php echo "$obiekt->ip"; ?>/js/ie-emulation-modes-warning.js"></script>

            <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
            <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
            <![endif]-->

            <!-- Custom styles for this template -->
            <link href="<?php echo "$obiekt->ip"; ?>/css/carousel.css" rel="stylesheet">
        </head>
        <!-- NAVBAR
        ================================================== -->
        <body>
        <div class="navbar-wrapper">
            <div class="container">

                <nav class="navbar navbar-inverse navbar-static-top">
                    <div class="container">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                    data-target="#navbar"
                                    aria-expanded="false" aria-controls="navbar">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>

                            </button>
                            <a class="navbar-brand" href="<?php $obiekt->url = 'welcom'; ?>"> ZamulaczTV </a>
                        </div>
                        <div id="navbar" class="navbar-collapse collapse">
                            <ul class="nav navbar-nav list-inline">

                                <li>
                                    &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</li>

                                <li><a href="<?php echo "$obiekt->ip";
                                    $obiekt->url = 'faq'; ?>/faq/"> FAQ </a></li>
                                <li><a href="<?php echo "$obiekt->ip";
                                    $obiekt->url = 'movie'; ?>/movie/"> Film </a></li>
                                <li><a href="<?php echo "$obiekt->ip";
                                    $obiekt->url = 'movieHD'; ?>/movie/"> Film HD </a></li>
                                <li><a href="<?php echo "$obiekt->ip";
                                    $obiekt->url = 'anime'; ?>/animation/"> Dla Dzieci </a></li>
                                <li><a href="<?php echo "$obiekt->ip";
                                    $obiekt->url = 'archiwum'; ?>/archiwum/"> Archiwum </a></li>
                                <li><a href="<?php echo "$obiekt->ip";
                                    $obiekt->url = 'recomend'; ?>/recomend/"> Polecane </a></li>
                                <li><a href="<?php echo "$obiekt->ip";
                                    $obiekt->url = 'rating'; ?>/best/"> Najwyżej Oceniane </a></li>

                                <form method="get" action="<?php echo "$obiekt->ip"; ?>/search/">
                                    <div class="col-lg-3 pull-right">
                                        <div class="input-group">
                                            <input type="text" class="form-control" placeholder="Szukaj filmu">
                                  <span class="input-group-btn">
                                    <button class="btn btn-default" type="submit">Szukaj</button>
                                  </span>
                                        </div>
                                        <!-- /input-group -->
                                </form>
                        </div>
                        <!-- /.col-lg-6 -->
                        </ul>
                    </div>
                </nav>
            </div>
        </div>


        <!-- Carousel
        ================================================== -->
        <div id="myCarousel" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
                <li data-target="#myCarousel" data-slide-to="3"></li>
                <li data-target="#myCarousel" data-slide-to="4"></li>
                <li data-target="#myCarousel" data-slide-to="5"></li>
                <li data-target="#myCarousel" data-slide-to="6"></li>
                <li data-target="#myCarousel" data-slide-to="7"></li>
                <li data-target="#myCarousel" data-slide-to="8"></li>
                <li data-target="#myCarousel" data-slide-to="9"></li>
                <li data-target="#myCarousel" data-slide-to="10"></li>
                <li data-target="#myCarousel" data-slide-to="11"></li>
            </ol>
            <div class="carousel-inner" role="listbox">

                <?php
                $mysqli = require_once("include/config.php");
                $row = 0;
                $results = $mysqli->query("SELECT * FROM movie ORDER BY premier DESC LIMIT 0, 12");
                while ($ro = $results->fetch_assoc()) {

                    $name = $ro['name'];
                    $description = substr($ro['description'], 0, 200);
                    if ($row == 0) {
                        echo "<div class=\"item active\">";
                        $row++;
                    } else
                        echo "<div class=\"item\">";

                    echo "
            <img src=\"$ro[photo]\"
                 alt=\"First slide\">

            <div class=\"container\">
                <div class=\"carousel-caption\">


                    <h1><a class=\"btn btn-lg btn-primary\" href=\"$ip/watch/?$ro[id]\" role=\"button\">$name<br> OGLĄDAJ JUŻ TERAZ ONLINE</a></h1>
                </div>
            </div>
        </div>
        ";
                }
                ?>

            </div>
            <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        <!-- /.carousel -->

        <!-- Marketing messaging and featurettes
        ================================================== -->
        <!-- Wrap the rest of the page in another container to center all the content. -->

        <div class="container marketing">

            <?php
            $row = 0;
            $results = $mysqli->query("SELECT * FROM movie ORDER BY premier DESC LIMIT 0, 18");
            while ($ro = $results->fetch_assoc()) {

                $row++;
                $inrow = 6;
                $name = $ro['name'];
                $description = substr($ro['description'], 0, 200);

                if ($row == $inrow)
                    echo "
            <!-- Three columns of text below the carousel -->
            <div class=\"row\">
            ";
                echo "

            <article>
                <div class=\"col-lg-2\">

                    <img class=\"img-circle\"
                         src=\"$ro[photo]\"
                         alt=\"$name, $description\" style=\"width: 140px; height: 140px;\">
                    <!--<header >
                        <h3>$name</h3>
                    </header >
                    <p>$description</p>
                    -->
                    <p><a class=\"btn btn-default center-block btn btn-info\" href=\"$ip/watch/?$ro[id]\" role=\"button\">Oglądaj film &raquo;</a></p>
                </div>
                <!-- /.col-lg-4 -->
            </article >
            ";
                if ($row == $inrow) {

                    echo "
            </div>
            <!-- /.row -->
            ";
                    $row = 0;
                }
            }
            ?>

            <br>

            <p><a class="btn btn-default center-block btn btn-info" href="<?php echo $ip; ?>/movie/?"
                  role="button"><br><strong>Zobacz
                        więcej filmów &raquo;</strong><br>&middot;</a></p>


            <hr class="featurette-divider">

            <!-- /END THE FEATURETTES -->


            <!-- FOOTER -->
            <footer>
                <p class="pull-right"><a href="#">Back to top</a></p>

                <p>&copy; 2014 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
            </footer>

        </div>
        <!-- /.container -->


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="<?php echo "$ip"; ?>/js/bootstrap.min.js"></script>
        <script src="<?php echo "$ip"; ?>/js/docs.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="<?php echo "$ip"; ?>/js/ie10-viewport-bug-workaround.js"></script>

        </body>
        </html>
        }
        }
    <?php
    }
}
    ?>
