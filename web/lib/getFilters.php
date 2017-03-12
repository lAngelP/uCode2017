<?php
include "client.php";

if(!isset($_POST["user"])){
	echo getJSONMessage(true, "Invalid parameters.");
	exit(1);
}
$str = getJSONGetFilters($_POST['user']);
if(!socket_write($sock, $str)){
	echo getJSONMessage(true, "Cannot write to socket.");
	socket_close($sock);
	exit(1);
}

$str = socket_read($sock, 4096, PHP_NORMAL_READ);
echo $str;
socket_close($sock);
?>