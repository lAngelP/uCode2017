<?php

if(!isset($_POST["user"]) || !isset($_POST["pass"])){
	echo getJSONMessage(true, "Invalid parameters.");
	exit(1);
}

include "client.php";

if(!fwrite($fp, getJSONLogin($_POST['user'], $_POST['pass']))){
	echo getJSONMessage(true, "Cannot write to socket.");
	exit(1);
}

echo fgets($fp, 4096);

?>