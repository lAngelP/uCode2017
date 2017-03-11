<?php
include "client.php";

if(!isset($_POST["user"]) || !isset($_POST["search"])){
	echo getJSONMessage(true, "Invalid parameters.");
	exit(1);
}

if(!fwrite($fp, getJSONSearch($_POST['user'], $_POST['search']))){
	echo getJSONMessage(true, "Cannot write to socket.");
	exit(1);
}

echo fgets($fp, 4096);

?>