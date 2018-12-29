<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-9">

            <div class="pull-right visible-xs" style="width: 100%; float: right; ">
                <button type="button" class="pull-right btn btn-primary btn-lg" data-toggle="offcanvas">Filtruj</button>
            </div>

            <div class="list-group">
                <?php
                $rowH = 0;
                $rowi = 3;
                $rowC = 0;
                foreach ($this->get('movies') as $row) {
                    $rowH++;
                    $rowC++;
                    $name = $row['name'];
                    $nameT = $name;
                    $name = substr($name, 0, 20);
                    $description = substr($row['description'], 0, 200);
                    ?>

                    <article class="text-center col-lg-3 col-md-4 col-sm-4 col-xs-5">
                        <div class="radio-inline text-center">
                            <a class="text-center" href="?id=<?php echo $row['id']; ?>" >
                                <!--<header>
                                    <h6><strong><?php echo $name; ?></strong></h6>
                                </header>
                                <!--<p>$description</p>-->
                                <img id="foto" class="img-thumbnail center-block bx-controls-auto text-center"
                                     data-toggle="tooltip"
                                     data-placement="right"
                                     src="<?php echo $row['photo']; ?>"
                                     alt="<?php echo $name . " " . '$description'; ?>"
                                     title="<?php echo $nameT . " " . $description; ?>"
                                     >
                            </a>
                            <!--
                            <p>

                             <a class="btn btn-default center-block btn btn-info" href="?id=<?php echo $row['id']; ?>"
                               role="button">
                                Oglądaj film &raquo;
                            </a>

                            </p>
                            -->
                        </div>
                        <!-- /.col-lg-4 -->
                    </article>
                    <?php
                }
                if ($rowC < 16 && $rowC % 3 != 0) {
                $rowH = 0; ?>

            <!-- /.row -->
            <?php
            }
            ?>
            <script>
                $(function () {
                    $('[data-toggle="tooltip"]').tooltip()
                })
            </script>
            <?php

            $site = @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],$_GET['sort'], $_GET['how']);
            $site .= "&site=";
            $how = 5;
            if (isset($_GET['site']))
                $nSite = $_GET['site'];
            else
                $nSite = 0;
            ?>
        </div>



            <div style="position: relative; width: 100%;float: left;" class="text-center">
                <ul class="pagination ">
                    <?php

                    ?>
                    <li><a href="<?php echo "" . $site . "" . ($nSite - 1); ?>">&laquo;</a></li>
                    <?php
                    for ($i = ($how * -1); $i <= $how; $i++) {
                        if ($nSite + $i > -1) {
                            ?>
                            <li><a href="<?php echo "" . $site . "" . ($nSite + $i); ?>"><?php echo $nSite + $i; ?></a>
                            </li>
                        <?php
                        }
                    }
                    ?>
                    <li><a href="<?php echo "" . $site . "" . ($nSite + 1); ?>">&raquo;</a></li>
                </ul>
            </div>


    <!--/.col-xs-12.col-sm-9-->

        </div>

    <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
        <div class="list-group list-group-item">

            <form action="?" method="get">
                <input name="s" type="hidden" value="search"/>
                <input name="type" type="hidden" value="<?php echo @$_GET['type']; ?>"/>
                <input name="name" type="hidden" value="<?php echo @$_GET['name']; ?>"/>
                <input name="wersion" type="hidden" value="<?php echo @$_GET['wersion']; ?>"/>
                <input name="sort" type="hidden" value="<?php echo @$_GET['sort']; ?>"/>
                <input name="how" type="hidden" value="<?php echo @$_GET['how']; ?>"/>

                <div class="list-group-item-heading">
                    <H3>Filtrowanie:</H3>
                </div>

                <?php echo @MovieModel::createFilt($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],$_GET['sort'], $_GET['how']); ?>


                <hr class="featurette-dividerNav">

                <div class="list-group-item-heading">
                    <H3>Kategorie:</H3>
                </div>

                <?php

                foreach ($this->get('category') as $row) { ?>
                    <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],$_GET['sort'], $_GET['how'], $row['type'], 3); ?>"
                       class="list-group-item-text">
                        <?php echo @$row['type']; ?>
                    </a>
                <?php } ?>

                <hr class="featurette-dividerNav">

                <div class="list-group-item-heading">
                    <H3>Zawiera słowa:</H3>
                </div>
                <input type="text" name="words" class="form-control" placeholder="Np. prywatnie jest genialnym"
                    <?php if (!empty($_GET["words"])) echo "value=\"$_GET[words]\""; ?>/>

                <hr class="featurette-dividerNav">

                <div class="list-group-item-heading">
                    <H3>Wersje:</H3>
                </div>

                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 0, 10); ?>"
                   class="list-group-item-text"> Nieznany typ </a> <br/>
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 1, 10); ?>"
                   class="list-group-item-text"> Orginał </a>,
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 2, 10); ?>"
                   class="list-group-item-text"> Napisy </a>,
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 3, 10); ?>"
                   class="list-group-item-text"> Lektor </a>,
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 4, 10); ?>"
                   class="list-group-item-text"> Dubbing </a> <br/>
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 5, 10); ?>"
                   class="list-group-item-text"> HD orginał </a>,
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 6, 10); ?>"
                   class="list-group-item-text"> HD Napisy </a>, <br/>
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 7, 10); ?>"
                   class="list-group-item-text"> HD Lektor </a>,
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 8, 10); ?>"
                   class="list-group-item-text"> HD Dubbing </a>, <br/>
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 9, 10); ?>"
                   class="list-group-item-text"> 3D orginał </a>,
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 10, 10); ?>"
                   class="list-group-item-text"> 3D Napisy </a>, <br/>
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 11, 10); ?>"
                   class="list-group-item-text"> 3D Lektor </a>,
                <a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], null,$_GET['sort'], $_GET['how'], 12, 10); ?>"
                   class="list-group-item-text"> 3D Dubbing </a>, <br/>

                <hr class="featurette-dividerNav">

                <div class="list-group-item-heading">


                    <div class="btn-group form-control">
                        <button class="btn btn-default btn-xs dropdown-toggle " type="button" data-toggle="dropdown" aria-expanded="false">
                            Sortuj   <span class="caret"></span>
                        </button>
                        <?php

                        if(isset($_GET['sort'])){
                            if($_GET['sort'] == 'name') echo " Tytuł filmu";
                            elseif($_GET['sort'] == 'views') echo " Obejrzenia filmu";
                            elseif($_GET['sort'] == 'rating') echo " Oceny filmu";
                            elseif($_GET['sort'] == 'time') echo " Czas filmu";
                            elseif($_GET['sort'] == 'premier') echo " Premiery filmu";

                        }
                        ?>

                        <ul class="dropdown-menu" role="menu">
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "DESC", "name", 11); ?>">Tytuł filmu<span class="caret" aria-hidden="true"></span></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "ASC", "name", 11); ?>">Tytuł filmu<span class="dropup"><span class="caret"></span></span></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "DESC", "views", 11); ?>">Obejrzenia<span class="caret" aria-hidden="true"></span></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "ASC", "views", 11); ?>">Obejrzenia<span class="dropup"><span class="caret"></span></span></a></li></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "DESC", "rating", 11); ?>">Oceny<span class="caret" aria-hidden="true"></span></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "ASC", "rating", 11); ?>">Oceny<span class="dropup"><span class="caret"></span></span></a></li></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "DESC", "premier", 11); ?>">Premiery<span class="caret" aria-hidden="true"></span></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "ASC", "premier", 11); ?>">Premiery<span class="dropup"><span class="caret"></span></span></a></li></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "DESC", "time", 11); ?>">Czasu<span class="caret" aria-hidden="true"></span></a></li>
                            <li><a href="<?php echo @MovieModel::createUrlFiltrCategory($_GET['name'], $_GET['words'], $_GET['type'], $_GET['dateFrom'], $_GET['dateTo'], $_GET['ratingFrom'], $_GET['ratingTo'], $_GET['longFrom'], $_GET['longTo'], $_GET['wersion'],null, "ASC", "time", 11); ?>">Czasu<span class="dropup"><span class="caret"></span></span></a></li></a></li>
                        </ul>
                    </div>

                </div>


                <hr class="featurette-dividerNav">





                <div class="list-group-item-heading">
                    <H3>Data premiery:</H3>
                </div>
                <input type="number" name="dateFrom" class="form-control" placeholder="Data od: Np. 1998" min="1800"
                       max="2015"
                    <?php if (!empty($_GET["dateFrom"])) echo "value=\"$_GET[dateFrom]\""; ?>/>
                <input type="number" name="dateTo" class="form-control" placeholder="Data do: Np. 2015" min="1800"
                       max="2015"
                    <?php if (!empty($_GET["dateTo"])) echo "value=\"$_GET[dateTo]\""; ?>/>

                <hr class="featurette-dividerNav">

                <div class="list-group-item-heading">
                    <H3>Ocena filmu:</H3>
                </div>
                <input type="number" name="ratingFrom" class="form-control" placeholder="Ocena od: Np. 2" min="0"
                       max="10"
                    <?php if (!empty($_GET["ratingFrom"])) echo "value=\"$_GET[ratingFrom]\""; ?>/>
                <input type="number" name="ratingTo" class="form-control" placeholder="Ocena od: Np. 5" min="0"
                       max="10"
                    <?php if (!empty($_GET["ratingTo"])) echo "value=\"$_GET[ratingTo]\""; ?>/>

                <hr class="featurette-dividerNav">

                <div class="list-group-item-heading">
                    <H3>Długość filmu:</H3>
                </div>
                <input type="number" name="longFrom" class="form-control" placeholder="Długość filmu od(min): Np 30"
                       min="0"
                       max="300"
                    <?php if (!empty($_GET["longFrom"])) echo "value=\"$_GET[longFrom]\""; ?>/>
                <input type="number" name="longTo" class="form-control" placeholder="Długość filmu do(min): Np 230"
                       min="0"
                       max="300"
                    <?php if (!empty($_GET["longTo"])) echo "value=\"$_GET[longTo]\""; ?>/>

                <br>
                <input type="submit" class="btn btn-default center-block btn btn-info form-control">
            </form>
        </div>
    </div>
    <!--/.sidebar-offcanvas-->

</div>
<!--/row-->

<hr>

<footer>
    <p>&copy; Company 2014</p>
</footer>

</div>
<!--/.container-->