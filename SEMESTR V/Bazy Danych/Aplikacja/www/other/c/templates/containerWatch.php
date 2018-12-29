<?php
$enum = array(
    0 => "Nieznany typ",
    1 => "Orginał",
    2 => "Napisy",
    3 => "Lektor",
    4 => "Dubbing",
    5 => "HD orginał",
    6 => "HD Napisy",
    7 => "HD Lektor",
    8 => "HD Dubbing",
    9 => "3D orginał",
    10 => "3D napisy",
    11 => "3D lektor",
    12 => "3D dubbing"
);

$h = 0;
$help = 0;
$data = $this->get('link');
$wersja = 0;
$hWersja = 0;
$co = 0;
?>




<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12  col-sm-9">

            <p class="pull-right visible-xs">
                <button type="button" class="btn btn-primary btn-lg" data-toggle="offcanvas">Info o filmie:</button>
            </p>
            <div class="list-group">
                <div id="example-one">
                    <div class="alert alert-danger alert-info" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <strong>WAŻNE!</strong> PONIŻEJ ZNAJDUJĄ SIĘ LISTY Z DOSTĘPNYMI PLAYERAMI DO WYBORU<BR>
                        PRZED ROZPOCZĘCIEM FILMU MOGĄ POJAWIĆ SIĘ REKLAMY, KTÓRE SĄ NAŻUCANE PRZEZ PLAYER<BR>
                        NALEŻY: KLIKAĆ, <strong>CLOUSE ADD</strong> LUB ZAMKNIJ REKLAMĘ.
                    </div>
                    <div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
                    <ul id="myTab" class="nav nav-tabs" role="tablist">

                        <?php

                        if (!empty($data)){
                        foreach ($data as $row) {
                        $co++;

                        $id = $row['id'];
                        $wersja = $enum[$row['version']];

                        if ($hWersja != $wersja || $hWersja == null) {
                        if ($hWersja != null) {
                        ?>
                            </li>
                            </ul>
                        <?php
                        }

                    $hWersja = $wersja;
                    ?>

                        <li role="presentation" class="dropdown">

                    <a href="#" id="<?php echo $wersja; ?>" class="dropdown-toggle" data-toggle="dropdown" aria-controls="myTabDrop1-contents"><strong><?php echo $wersja; ?></strong><span class="caret"></span></a>

                    <ul class="dropdown-menu" role="menu" aria-labelledby="<?php echo $wersja; ?>" id="myTabDrop1-contents">
                        <?php
                        }
                            ?>
                            <li><a href="#dropdown<?php echo $co; ?>" tabindex="-1" role="tab" id="<?php echo $id; ?>" data-toggle="tab" aria-controls="<?php echo $id; ?>"><?php echo WatchModel::getDomen($row['link']); ?></a></li>
                        <?php

                        }
                        }
                        if ($hWersja == $wersja) {
                        ?>
                        </li>
                    </ul>
                <?php
                } ?>
                    </ul>


                    <div id="myTabContent" class="tab-content">
                        <?php
                        $co = 0;
                        if (!empty($data)) {
                            foreach ($data as $row) {
                                $co++;
                                $id = $row['id'];
                                $wersja = $enum[$row['version']];

                                if($co == 1){
                                ?>

                            <div role="tabpanel" class="tab-pane fade in active" id="dropdown" aria-labelledBy="dropdown">
                                <p><?php echo WatchModel::showIframe($row['link']); ?></p>
                            </div>

                                    <?php
                                }
                                ?>

                                <div role="tabpanel" class="tab-pane fade" id="dropdown<?php echo $co; ?>" aria-labelledBy="dropdown<?php echo $co; ?>">
                                    <p><?php echo WatchModel::showIframe($row['link']); ?></p>
                                </div>

                            <?php }
                        } else { ?>

                            <div class="alert alert-danger alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <strong>NIESZCZĘŚCIE!</strong> NA NASZEJ STRONIE ZNAJDUJE SIĘ TEN FILM, LECZ NIE MA
                                JESZCZE DOSTĘPNYCH LINKÓW.<BR> PRZEPRASZAMY ZA PROBLEM I POLECAMY INNE FILMY
                            </div>
                        <?php } ?>
                    </div>
                </div>
                </div>
            </div>
        </div>
        <!--/.col-xs-12.col-sm-9-->


        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
            <div class="list-group list-group-item">


                <?php
                $enum = array(
                    0 => "",
                    1 => "",
                    2 => "",
                    3 => "Kategoria: ",
                    4 => "Premiera: ",
                    5 => "Ocena : ",
                    6 => "Czas: "
                );
                $enum1 = array(
                    0 => "photo",
                    1 => "name",
                    2 => "description",
                    3 => "type",
                    4 => "premier",
                    5 => "rating",
                    6 => "time"
                );
                $row = $this->get('info');
                ?>
                <div class="list-group-item-heading">
                    <H3><?php echo $row[0]['name']; ?></H3>
                </div>
                <img class="center-block" src="
                <?php echo $row[0]['photo']; ?>
                "/>
                <hr class="featurette-dividerNav">

                <?php
                for ($i = 3; $i < 7; $i++) { ?>
                    <p>
                        <?php
                        echo $enum[$i] . $row[0][$enum1[$i]]; ?>
                    </p>
                <?php } ?>
                <hr class="featurette-dividerNav">
                <p>
                    <?php echo $enum[2] . $row[0][$enum1[2]]; ?>
                </p>

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
