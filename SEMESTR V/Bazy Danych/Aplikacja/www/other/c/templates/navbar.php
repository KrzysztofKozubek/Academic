<?php

$ip = 'http://' . $_SERVER ["SERVER_NAME"];
?>

<!-- NAVBAR
================================================== -->
<div class="navbar-wrapper">
    <div class="container">

        <nav class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                            aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>

                    </button>
                    <a class="navbar-brand" href="<?php echo $ip; ?>"> ZamulaczTV </a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav list-inline">

                        <li>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</li>

                        <li><a href="?s=faq"> FAQ </a></li>
                        <li><a href="?s=movie"> Film </a></li>
                        <li><a href="?s=search&name=&words=&type=&dateFrom=&dateTo=&ratingFrom=&ratingTo=&longFrom=&longTo=&wersion=9"> Film HD </a></li>
                        <li><a href="?s=search&name=&words=&type=Animowane%20&dateFrom=&dateTo=&ratingFrom=&ratingTo=&longFrom=&longTo=&wersion="> Dla Dzieci </a></li>
                        <li><a href="?s=archiwum"> Archiwum </a></li>
                        <li><a href="?s=recoment"> Polecane </a></li>
                        <li><a href="?s=best"> Najwyżej Oceniane </a></li>

                        <form method="get" action="?">
                            <div class="col-lg-3 pull-right">
                                <div class="input-group">
                                    <input type="text" name="name" class="form-control" placeholder="Szukaj filmu">
                                    <input type="hidden" name="s" value="search">
                                  <span class="input-group-btn">
                                    <button class="btn btn-default" type="submit">Szukaj</button>
                                  </span>
                                </div>
                                <!-- /input-group -->
                            </div>
                        </form>
                    </ul>
                </div>
                <!-- /.col-lg-6 -->
            </div>
        </nav>
    </div>  

