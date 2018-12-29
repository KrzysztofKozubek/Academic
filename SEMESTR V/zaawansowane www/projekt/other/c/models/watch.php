<?php

include 'models/model.php';

class WatchModel extends Model
{

    public function index()
    {
        $cat = $this->loadModel('Movie');
        //$this->render('indexVategory');
    }

    public function resultQuery($query)
    {

        $this->pdo->query('SET NAMES utf8');
        $select = $this->pdo->query($query);
        $data = null;
        foreach ($select as $row) {
            $data[] = $row;
        }
        return $data;
    }

    public function getInformation($id)
    {
        $data = $this->resultQuery("SELECT `movie`.`name`, `movie`.`description`, `movie`.`photo`, `movie`.`type`, `movie`.`premier`, `movie`.`time`,`movie`.`rating` FROM movie, link WHERE (`movie`.`id` = '$id') AND (`link`.`id_film` = '$id') LIMIT 0, 1");
        if (empty($data)) {
            $data = $this->resultQuery("SELECT `movie`.`name`, `movie`.`description`, `movie`.`photo`, `movie`.`type`, `movie`.`premier`, `movie`.`time`,`movie`.`rating` FROM movie, link WHERE (`movie`.`id` = '$id') LIMIT 0, 1");
        }
        return $data;
    }

    public function getLinkMovie($id)
    {
        $data = null;
        $data = $this->resultQuery("SELECT `link`.`version`,`link`.`link`, `link`.`id`, `link`.`version`, `link`.`date`  FROM movie, link WHERE `movie`.`id` = '$id' AND `link`.`id_film` = '$id' ORDER BY `link`.`version` DESC");

        return $data;
    }

    public static function getDomen($address)
    {

        if (strstr($address, "hd3d")) {
            return "hd3d";
        }
        if (strstr($address, "novamov")) {
            return "novamov";
        }
        if (strstr($address, "divxstage")) {
            return "divxstage";
        }
        if (strstr($address, "tinymov")) {
            return "tinymov";
        }
        if (strstr($address, "firedrive")) {
            return "firedrive";
        }
        if (strstr($address, "videoweed")) {
            return "videoweed";
        }
        if (strstr($address, "putlocker")) {
            return "putlocker";
        }
        if (strstr($address, "vshare")) {
            return "vshare";
        }
        if (strstr($address, "nowvideo")) {
            return "nowvideo";
        }
        if (strstr($address, "dwn.so")) {
            return "dwn";
        }
        if (strstr($address, "maxupload")) {
            return "maxupload";
        }
        if (strstr($address, "vidzer.net")) {
            return "vidzer";
        }
        if (strstr($address, "played.to")) {
            return "played";
        }
        return "inne";

    }

    public static function showIframe($address)
    {

        if (strstr($address, "hd3d")) {

            echo "
		<div class=\"tab\" id=\"hd3d\">
			<iframe src=\"" . $address . "\"  style=\"width:100%;height:500px;border:0px;\" scrolling=\"no\" >	</iframe>
		</div>
		";
        }
        if (strstr($address, "novamov")) {

            echo "
		<div class=\"tab\" id=\"novamov\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:600px; height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "divxstage")) {

            echo "
		<div class=\"tab\" id=\"divxstage\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "tinymov")) {

            echo "
		<div class=\"tab\" id=\"tinymov\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "firedrive")) {

            echo "
		<div class=\"tab\" id=\"firedrive\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }

        if (strstr($address, "videoweed")) {

            echo "
		<div class=\"tab\" id=\"videoweed\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }

        if (strstr($address, "putlocker")) {

            echo "
		<div class=\"tab\" id=\"putlocker\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }

        if (strstr($address, "vshare")) {

            echo "
		<div class=\"tab\" id=\"vshare\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "nowvideo")) {
            echo "
		<div class=\"tab\" id=\"nowvideo\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" frameborder=\"0\">	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "dwn.so")) {
            echo $address;
            echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"" . $address . "\" width=\"500\" height=\"350\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" frameborder=\"0\">	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "maxupload")) {

            echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "vidzer.net")) {

            echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" scrolling=\"no\" >	</iframe></center>
		</div>
		";
        }
        if (strstr($address, "played.to")) {

            $address = substr($address, strpos($address, "to/") + 3);
            $address = "http://played.to/embed-" . $address . "-940x500.html";
            //echo $address;
            echo "
		<div class=\"tab\" id=\"dwn\">
			<center><iframe src=\"" . $address . "\" style=\"overflow: hidden; border: 0; width:100%;height:500px; \" FRAMEBORDER=0 MARGINWIDTH=0 MARGINHEIGHT=0 SCROLLING=NO WIDTH=850 HEIGHT=500>	</iframe></center>
		</div>
		";
        }
    }


}