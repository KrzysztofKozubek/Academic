<div class="container marketing center-block">
    <!-- Three columns of text below the carousel -->

        <?php
        $rowH = 0;
        $inrow = 24;

        foreach ($this->get('contaner') as $row) {

            $name = $row['name'];
            //$name = substr($name, 0, 20);
            $description = $row['description'];
            ?>
            <article class="text-center col-lg-2 col-md-3 col-sm-3 col-xs-6 ">
                <div class="">
                    <a href="?id=<?php echo $row['id']; ?>">
                        <img class="img-thumbnail"
                             data-toggle="tooltip"
                             data-placement="right"
                             src="<?php echo $row['photo']; ?>"
                             alt="<?php echo $name . " " . '$description'; ?>"
                             title="<?php echo $name . "\n\n" . $description; ?>"
                             style="width: 140px; height: 140px;"/>
                    </a>
                </div>
                <!--<header >
            <h3>$name</h3>
        </header >
        <p>$description</p>

        <p>
            <a class="btn btn-default center-block btn btn-info" href="?id=<?php echo $row['id']; ?>" role="button">
                Oglądaj film &raquo;
            </a>
        </p>-->

                <!-- /.col-lg-4 -->
            </article>

        <?php
        }
        ?>
    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    </script>
</div>
<br>




<p><a class="btn btn-default center-block btn btn-info" href="?s=movie" role="button"><br><strong>Zobacz
            więcej filmów &raquo;</strong><br>&middot;</a></p>


<hr class="featurette-divider">

<!-- /END THE FEATURETTES -->