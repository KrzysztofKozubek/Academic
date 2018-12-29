<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 22:27
 */
$ip = 'http://' . $_SERVER ["SERVER_NAME"];
?>

<hr>

<footer>
    <p>&copy; All right reserved. Create by Kris Kozubek</p>
</footer>

</div><!--/.container-->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<script src="<?php echo "$ip"; ?>/static/js/ie8-responsive-file-warning.js"></script>
<![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<?php echo "$ip"; ?>/static/js/bootstrap.min.js"></script>

<script src="<?php echo "$ip"; ?>/static/js/ie-emulation-modes-warning.js"></script>
<script src="<?php echo "$ip"; ?>/static/js/offcanvas.js"></script>
<script src="<?php echo "$ip"; ?>/static/js/bootstrap-select.js"></script>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="<?php echo "$ip"; ?>/static/js/ie10-viewport-bug-workaround.js"></script>


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