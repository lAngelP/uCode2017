<?php

if(!isset($_POST["user"]))){
	echo getJSONMessage(true, "Invalid parameters.");
	exit(1);
}

include "client.php";

if(!fwrite($fp, getJSONGetFilters($_POST['user']))){
	echo getJSONMessage(true, "Cannot write to socket.");
	exit(1);
}

echo fgets($fp, 4096);

?>