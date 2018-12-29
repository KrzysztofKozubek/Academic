<?php
	/*
		UserCake Version: 1.0
		http://usercake.com
		

	*/
	include("models/config.php");
	
	//Prevent the user visiting the logged in page if he/she is not logged in
	if(!isUserLoggedIn()) { header("Location: login.php"); die(); }
?>

<?php
$error = null;

if(!empty($_POST)){
	
	if(@$_POST['film']){
	
		if(@$_POST['tekst']){
		
			$website = $_POST['film'];
			$links = $_POST['tekst'];
			if(strpos($website, "ttp://www.filmweb.pl/") == 1 || strpos($website, "ttp://filmweb.pl/") == 1 ){
			
				if(strpos($links, "ttp://hd3d.cc/file,embed,") == 1 || 
				strpos($links, "ttp://putlocker.com/embed/") == 1 || 
				strpos($links, "ttp://embed.novamov") == 1 || 
				strpos($links, "ttp://embed.divxstage.eu/embed.") == 1 || 
				strpos($links, "ttp://firedrive.com") == 1 || 
				strpos($links, "ttp://www.hd3d.cc/file,embed,") == 1 || 
				strpos($links, "ttp://www.putlocker.com/embed/") == 1 || 
				strpos($links, "ttp://www.embed.novamov") == 1 || 
				strpos($links, "ttp://www.embed.divxstage.eu/embed.") == 1 || 
				strpos($links, "ttp://www.firedrive.com") == 1){
					
					
					$question = "INSERT INTO `poczekalnia` (`id`, `film`, `link`) 
				VALUES ('', \"$website\", \"$links\", '".date('Y-m-d')."')";
				$error = "Film zosta&#322; dodany do bazy. Dziekujemy :)";
				
					include("../include/config.php");		
				$insert = $mysqli->query($question);
					
				} else $error = "Podany link do filmu jest nieprawid&#322;owy";			
			} else $error = "Podany adres do filmu jest nieprawid&#322;owy";
		} else $error = "Podaj linki film&#243;w z obs&#322;ugiwanych player&#243;w";
	} else $error = "Podaj link filmu z strony filmweb";
}
else $error = "Uzupe&#322;nij pola";
?>
<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta charset=\"utf-8\">
<title> ADD FILM </title>
<?php require_once("head_inc.php"); ?>

</head>
<body>
<?php require_once("navbar.php"); ?>

<div id="content">
<div class="modal-ish" style="width: 400px;">
  <div class="modal-body">

		
		<br/>
            <form name="changePass" action="<?php echo $_SERVER['PHP_SELF'] ?>" method="post">
            <?php
			echo "
                <p>
                    <label>Link do filmweb: </label>
                    <input value=\"".@$_POST['film']."\" min=\"25\" max=\"255\" name=\"film\" placeholder=\"Podaj link z filmweb tego filmu\"  autocomplete=off required   id=\"film\" style=\"width:320px; height:30px;\"/>
                </p>
                
               <p>
                    <label>Link do filmu: </label>
                    <input value=\"".@$_POST['tekst']."\" min=\"25\" max=\"255\" name=\"tekst\" placeholder=\"Podaj link filmu (player)\"  autocomplete=off required   id=\"tekst\" style=\"width:320px; height:30px;\"/>
					
                </p>
                ";
				?>
				<div id="errors">
				<?php echo $error; ?>
				</div>  
            </div>    

  <div class="modal-footer">
<input type="submit" class="btn btn-primary" name="new" id="newfeedform" value="Dodaj film" />
  </div>

  
</div>

                </form>
                <BR/>Obs&#322;ugujemy: HD3D, PUTLOCKER, DIVXSTAGE, FIREDRIVE, TINYMOV, VIDEOWEED, VSHARE, DWN
        
            <div class="clear"></div>

</div>  
</body>
</html>