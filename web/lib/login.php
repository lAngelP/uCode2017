<?php

include "client.php";

if(!isset($_POST["user"]) || !isset($_POST["pass"])){
	echo getJSONMessage(true, "Invalid parameters.");
	exit(1);
}

$str = getJSONLogin($_POST['user'], $_POST['pass']);
if(!socket_write($sock, $str)){
	echo getJSONMessage(true, "Cannot write to socket.");
	exit(1);
}
$str = socket_read($sock, 4096, PHP_NORMAL_READ); 
echo $str;
socket_close($sock);

?>