<?php
/**
 * Created by PhpStorm.
 * User: Krzysztof
 * Date: 2015-12-01
 * Time: 22:27
 */
require 'ip.php';
?>

</div><!-- Parallax-->
<div class="parallax-container">
    <div class="parallax"><img src="<?php echo $ip; ?>/static/images/background_1.jpg"></div>
</div><!-- Footer-->
<footer id="contact" class="page-footer default_color scrollspy">
    <section class="container">
        <div class="col l6 s12">
            <form action="<?php echo $ip; ?>/" method="post" class="col s12">
                <dt class="row">
                    <article class="input-field col s6"><i
                            class="mdi-action-account-circle prefix white-text"></i><input id="icon_prefix"
                                                                                           name="InputName" type="text"
                                                                                           class="validate white-text"><label
                            for="icon_prefix" class="white-text">Podaj swoje imię</label></article>
                    <article class="input-field col s6"><i class="mdi-communication-email prefix white-text"></i><input
                            id="icon_email" name="InputEmail" type="email" class="validate white-text"><label
                            for="icon_email" class="white-text">Adres email</label></article>
                    <article class="input-field col s12"><i class="mdi-editor-mode-edit prefix white-text"></i><textarea
                            id="icon_prefix2" name="InputMessage"
                            class="materialize-textarea white-text"></textarea><label for="icon_prefix2"
                                                                                      class="white-text">Wiadomość</label>
                    </article>
                    <article class="col offset-s7 s5">
                        <button type="submit" class="btn waves-effect waves-light red darken-1">Wyślij<i
                                class="mdi-content-send right white-text"></i></button>
                    </article>
                </dt>
            </form>
        </div>
    </section>
    <section class="footer-copyright default_color">
        <div class="container">Made by<a href="http://krzysztof-kozubek.blogspot.com/" class="white-text">Krzysztof
                Kozubek</a>. Thanks to<a href="http://materializecss.com/" class="white-text"> materializecss</a></div>
    </section>
</footer><!-- Scripts-->
<script src="<?php echo $ip; ?>/static/js/plugin-min.js"></script>
<script src="<?php echo $ip; ?>/static/js/custom-min.js"></script>
</body>
</html>