<?php
include "client.php";

if(!isset($_POST["user"]) || !isset($_POST["search"])){
	echo getJSONMessage(true, "Invalid parameters.");
	exit(1);
}

$str = getJSONSearch($_POST['user'], $_POST['search']);
if(!socket_write($sock, $str)){
	echo getJSONMessage(true, "Cannot write to socket.");
	exit(1);
}
$str = socket_read($sock, 4096, PHP_NORMAL_READ); 

echo $str;

socket_close($sock);

?>