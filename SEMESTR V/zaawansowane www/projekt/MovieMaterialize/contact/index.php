<?php
session_start();

$ip = 'http://' . $_SERVER ["SERVER_NAME"];
include_once('../templates/head.php');
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-02
 * Time: 01:30
 */
if (isset($_POST["InputMessage"]) && isset($_POST["InputEmail"]) && isset($_POST["InputMessage"])){

    $topic = $_POST['InputName'] . " FROM " . $_POST["InputEmail"];
    $message = $_POST['InputMessage'];

    if (mail("krzysztof.kozubek135@gmail.com", $topic, $message))
        echo "<div class=\"alert alert-success\" role=\"alert\">Wiadomość została wysłana :) </div>";
    else
        echo "<div class=\"alert alert-danger\" role=\"alert\">Wiadomość nie została wysłana :(</div>";


}
?>

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="alert alert-success"><strong><span class="glyphicon glyphicon-send"></span> Contact
                        Us</strong></div>
            </div>
            <form role="form" action="<?php echo $ip . '/contact/'; ?>" method="post">
                <div class="col-lg-6">
                    <div class="form-group">
                        <label for="InputName">Your Name</label>

                        <div class="input-group">
                            <input type="text" class="form-control" name="InputName" id="InputName"
                                   placeholder="Enter Name" required>
                            <span class="input-group-addon"></span></div>
                    </div>
                    <div class="form-group">
                        <label for="InputEmail">Your Email</label>

                        <div class="input-group">
                            <input type="email" class="form-control" id="InputEmail" name="InputEmail"
                                   placeholder="Enter Email" required>
                            <span class="input-group-addon"></span></div>
                    </div>
                    <div class="form-group">
                        <label for="InputMessage">Message</label>

                        <div class="input-group">
                            <textarea name="InputMessage" id="InputMessage" class="form-control" rows="5"
                                      required></textarea>
                            <span class="input-group-addon"></span></div>
                    </div>

                    <input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right">
                </div>
            </form>
            <hr class="featurette-divider hidden-lg">
            <div class="col-lg-5 col-md-push-1">
                <address>

                    <p class="lead">
                        Please note that any contact messages that are requesting movies will be ignored.</p>

                    <p class="lead">
                        If you want to send a DMCA take down request please keep in mind these will take 2-4 working
                        days to process.</p>
                </address>
            </div>
        </div>

    </div>
<?php
include_once('../templates/footer.php');
?>