<?php

$ip = 'http://'.$_SERVER ["SERVER_NAME"];

require_once("../include/config.php");
include("../account/models/config.php");
include("../createQuestion.php");

$results = $mysqli->query("SELECT COUNT(*) as t_records FROM movie");
if($results){
$total_records = $results->fetch_object();
$total_groups = ceil($total_records->t_records/$items_per_group);
$results->close(); 
}
if(@$loggedInUser->display_username){

	showHeadDeffoult($ip); 
	showScrips($total_groups);
	showSignInOut($loggedInUser, $ip);
	showSliderPremier($mysqli, $ip, 0);
	showStartContent();

	echo <<< SKRYPT

	<div id="wyniki"></div>

					<h2>Polecane</h2>
						<ul class="latest_movies">
	<ol id="results" name="search">
	</ol>
	<div class="animation_image" style="display:none" align="center"><img src="../images/ajax-loader.gif" class="animation_image"></div>

SKRYPT;
	echo("				
						</ul>
					</article>
				<article class=\"grid_8\">
	");
					


	shwoPanelUser($loggedInUser, $ip);
	showFormSearch($mysqli, $ip);



	echo <<< SKRYPT

	<div id="wyniki"></div>

					
	<ol id="results">
	</ol>


	<div class="animation_image" style="display:none" align="center"></div>
SKRYPT;
	 echo("					
							</ul>
						</div>
	");

	showFooter($ip);
}
else{

	showHeadDeffoult($ip); 
	showScrips($total_groups);
	showSignInOut($loggedInUser, $ip);
	//showSliderPremier($mysqli, $ip);
	//showStartContent();

	echo <<< SKRYPT

	

					<p style="padding: 100px 0px 100px 0px;font-size:40px;text-align:center;">Zaloguj się aby móc korzystać z tej opcji</p>
						<ul class="latest_movies">
	

SKRYPT;
	echo("				
						</ul>
					</article>
				
	");
					


	shwoPanelUser($loggedInUser, $ip);
	//showFormSearch($mysqli, $ip);


	 echo("					
							</ul>
						</div>
	");

	showFooter($ip);
	}
?>
<a href="#" title="Do góry!" id="scroll-to-top"></a>

 
<button title="Na Dół!" id="scroll-to-bottom" class="scroll-to-bottom"><a href="#" title="Na Dół!" id="scroll-to-bottom"></a></button>

 
 <script>
 $(function(){
    var stt_is_shown = false;
    $(window).scroll(function(){
       var win_height = 300;
       var scroll_top = $(document).scrollTop(); 
       if (scroll_top > win_height && !stt_is_shown) {
          stt_is_shown = true;
          $("#scroll-to-top").fadeIn();
       } else if (scroll_top < win_height && stt_is_shown) {
          stt_is_shown = false;
          $("#scroll-to-top").fadeOut();
       }
   });
   $("#scroll-to-top").click(function(e){
      e.preventDefault();
      $('html, body').animate({scrollTop:200},250);
    });
 });
 </script>
</body>
</html>