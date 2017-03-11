<?php
include 'lib.php';

$sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
socket_connect($sock, '127.0.0.1', 8023); 

if(!$sock){
	echo getJSONMessage(true, "Cannot connect to server.");
	socket_close($sock);
	exit(1);
}

?>
