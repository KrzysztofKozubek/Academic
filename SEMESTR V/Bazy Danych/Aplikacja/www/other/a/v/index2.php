
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Off Canvas Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="css/offcanvas.css">
    <link rel="stylesheet" href="css/bootstrap-select.css">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap-select.js"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
</head>

<body>
<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Szukaj Filmu</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Baza Filmów</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#">Home</a></li>
                <li><a href="#about">Przeglądaj</a></li>
                <li><a href="#contact">Kontakt</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right text-right">
                <li><a href="#">Logowanie</a></li>
                <li><a href="#about">Rejestracja</a></li>
            </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</nav><!-- /.navbar -->

<div class="container">

    <div class="row">

        <div class="jumbotron text-center form-inline">
            <form class="form-inline" role="form">

                <div class="form-group form-inline">
                    <span class="btn-lg">Fraza</span>
                    <label class="sr-only" for="phrase">Fraza</label>
                    <input type="text" class="form-control" id="phrase" placeholder="Szukaj">

                    <button type="submit" class="btn btn-default">Wyślij</button>
                </div>

                <div class="form-inline">
                    <div class="form-group">
                        <span>Garunek</span>
                        <label class="sr-only" for="grade">Gatunek</label>
                        <select class="form-control selectpicker show-menu-arrow show-tick" id="grade" placeholder="Gatunek" data-size="7">
                            <option data-content="<span class='label label-default'>All</span>">All</option>
                            <option data-content="<span class='label label-success'>Akcja</span>">Akcja</option>
                            <option data-content="<span class='label label-success'>Komedia</span>">Komedia</option>
                            <option data-content="<span class='label label-success'>Horror</span>">Horror</option>
                            <option data-content="<span class='label label-success'>Akcja</span>">Akcja</option>
                            <option data-content="<span class='label label-success'>Komedia</span>">Komedia</option>
                            <option data-content="<span class='label label-success'>Horror</span>">Horror</option>
                            <option data-content="<span class='label label-success'>Akcja</span>">Akcja</option>
                            <option data-content="<span class='label label-success'>Komedia</span>">Komedia</option>
                            <option data-content="<span class='label label-success'>Horror</span>">Horror</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <span>Ocena</span>
                        <label class="sr-only" for="mark">Ocena</label>
                        <select class="form-control selectpicker show-menu-arrow show-tick" id="mark" placeholder="Ocena">
                            <option data-content="<span class='label label-default'>All</span>">All</option>
                            <option data-content="<span class='label label-warning'>9+</span>">9+</option>
                            <option data-content="<span class='label label-warning'>8+</span>">8+</option>
                            <option data-content="<span class='label label-warning'>7+</span>">7+</option>
                            <option data-content="<span class='label label-warning'>6+</span>">6+</option>
                            <option data-content="<span class='label label-warning'>5+</span>">5+</option>
                            <option data-content="<span class='label label-warning'>4+</span>">4+</option>
                            <option data-content="<span class='label label-warning'>3+</span>">3+</option>
                            <option data-content="<span class='label label-warning'>2+</span>">2+</option>
                            <option data-content="<span class='label label-warning'>1+</span>">1+</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <span>Sortuj</span>
                        <label class="sr-only" for="sort">Sortuj</label>
                        <select class="form-control selectpicker show-menu-arrow show-tick" id="sort" placeholder="Sortuj">
                            <option data-content="<span class='label label-default'>All</span>">All</option>
                            <optgroup label="Czas" data-max-options="2">
                                <option data-content="<span class='label label-danger'>Czas Najnowsze</span>">Najnowsze</option>
                                <option data-content="<span class='label label-danger'>Czas Najstarsze</span>">Najstarsze</option>
                            </optgroup>
                            <optgroup label="Ocena" data-max-options="2">
                                <option data-content="<span class='label label-danger'>Oceny Najlepszej</span>">Oceny Max</option>
                                <option data-content="<span class='label label-danger'>Oceny Nagorszej</span>">Oceny Min</option>
                            </optgroup>
                            <optgroup label="Alfabetycznie" data-max-options="2">
                                <option data-content="<span class='label label-danger'>Alfabetycznie A - Z</span>">A - Z</option>
                                <option data-content="<span class='label label-danger'>Alfabetycznie Z - A</span>">Z - A</option>
                            </optgroup>
                        </select>
                    </div>
                </div>
            </form>
        </div>

        <div class="row text-center">

            <div class="col-lg-2">
                <a href="#" class="thumbnail">
                    <div style="background-image:url(http://1.fwcdn.pl/po/08/62/862/7517878.3.jpg);" class="image"></div>
                    <h4 class="text-center">Zielona mila / The Green Mile / The Green Mile / The Green Mile / The Green Mile</h4>
                </a>
            </div><!--/.col-xs-6.col-lg-4-->
            <div class="col-lg-2">
                <a href="#" class="thumbnail">
                    <div style="background-image:url(http://1.fwcdn.pl/po/06/28/628/7685907.3.jpg);" class="image"></div>
                    <h4 class="text-center">Zielona mila / The Green Mile</h4>
                </a>
            </div><!--/.col-xs-6.col-lg-4-->
            <div class="col-lg-2">
                <a href="#" class="thumbnail">
                    <div style="background-image:url(http://1.fwcdn.pl/po/06/71/671/7016965.3.jpg);" class="image"></div>
                    <h4 class="text-center">Zielona mila / The Green Mile</h4>
                </a>
            </div><!--/.col-xs-6.col-lg-4-->
            <div class="col-lg-2">
                <a href="#" class="thumbnail">
                    <div style="background-image:url(http://1.fwcdn.pl/po/95/09/9509/7579423.3.jpg);" class="image"></div>
                    <h4 class="text-center">Zielona mila / The Green Mile</h4>
                </a>
            </div><!--/.col-xs-6.col-lg-4-->
            <div class="col-lg-2">
                <a href="#" class="thumbnail">
                    <div style="background-image:url(http://1.fwcdn.pl/po/91/13/299113/7332755.3.jpg);" class="image"></div>
                    <h4 class="text-center">Zielona mila / The Green Mile</h4>
                </a>
            </div><!--/.col-xs-6.col-lg-4-->
            <div class="col-lg-2">
                <a href="#" class="thumbnail">
                    <div style="background-image:url(http://1.fwcdn.pl/po/09/36/936/7472818.3.jpg);" class="image"></div>
                    <h4 class="text-center">Zielona mila / The Green Mile</h4>
                </a>
            </div><!--/.col-xs-6.col-lg-4-->

        </div><!--/row-->

        <nav>
            <ul class="pagination">
                <li>
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li>
                    <a href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>

    </div><!--/row-->

    <hr>

    <footer>
        <p>&copy; Company 2014</p>
    </footer>

</div><!--/.container-->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="js/ie10-viewport-bug-workaround.js"></script>

<script src="js/offcanvas.js"></script>

<script>
    $(document).ready(function () {
        var mySelect = $('#first-disabled2');

        $('#special').on('click', function () {
            mySelect.find('option:selected').prop('disabled', true);
            mySelect.selectpicker('refresh');
        });

        $('#special2').on('click', function () {
            mySelect.find('option:disabled').prop('disabled', false);
            mySelect.selectpicker('refresh');
        });

        $('#basic2').selectpicker({
            liveSearch: true,
            maxOptions: 1
        });
    });
</script>
</body>
</html>


<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-11-16
 * Time: 14:42
 */