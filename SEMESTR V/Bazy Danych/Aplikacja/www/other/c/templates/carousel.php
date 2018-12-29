<!-- Carousel
================================================== -->
<div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->

    <div class="carousel-inner" role="listbox">

        <?php
        $rowH = 0;
        $date = $this->get('carousel');
        $t = 5;
        $n = 6;
        $enumMovie = array(
            0=>"Filmy: KOMEDIA",
            1=>"Filmy: HORROR",
            2=>"Filmy: AKCJI",
            3=>"Filmy: ANIMOWANE",
            4=>"Filmy: THRILLER"
        );

        for($i = 0; $i < $t; $i++) {

        if ($rowH == 0) { ?>
        <div class="item active">

            <div class="text-center col-lg-2 col-md-3 hidden-xs hidden-sm" style="height: auto;width: 100%; font-size: 25px; margin-bottom: -80px;">

                    <?php echo $enumMovie[$i]; ?>

            </div>

            <?php $rowH++; } else { ?>
            <div class="item">

                <div class="text-center col-lg-2 col-md-3 hidden-xs hidden-sm" style="height: auto;width: 100%; font-size: 25px; margin-bottom: -80px;">

                    <?php echo $enumMovie[$i]; ?>

                </div>

            <?php }

            for($j = $i * $n; $j < ($i * $n)+$n; $j++){
            ?>


                <div class="text-center col-lg-2 col-md-3 hidden-xs hidden-sm">
                    <div class="radio-inline text-center" style="margin-top: 10%;">
                        <a href="?id=<?php echo $date[$j][0]; ?>">
                            <img id="foto" class="img-thumbnail"
                                 src="<?php echo $date[$j][1]; ?>"
                                 alt="<?php echo $date[$j][2]; ?>"
                                 title="<?php echo $date[$j][2]; ?>"
                                >
                        </a>

                    </div>
                </div>
        <?php } ?> </div> <?php } ?>


        </div>
        <a class="left carousel-control hidden-xs hidden-sm" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control hidden-xs hidden-sm" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <!-- /.carousel -->