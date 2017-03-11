<?php

include "client.php";
if(!isset($_POST["user"]) || !isset($_POST["pass0"]) || !isset($_POST["pass1"]) || !isset($_POST["mail"]) || $_POST['pass0'] != $_POST['pass1']){
	echo getJSONMessage(true, "Invalid parameters.");
	socket_close($sock);
	exit(1);
}
$str = getJSONRegister($_POST['user'], $_POST['pass0'], $_POST['mail']);
if(!socket_write($sock, $str)){
	echo getJSONMessage(true, "Cannot write to socket.");
	socket_close($sock);
	exit(1);
}
$str = socket_read($sock, 4096, PHP_NORMAL_READ); 
/*
while($line != ""){
	$str .= $line;
	$line = socket_read($sock, 4096, PHP_NORMAL_READ);
}*/

echo $str;

socket_close($sock);
?>