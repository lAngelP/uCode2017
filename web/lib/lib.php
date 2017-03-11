<?php

error_reporting(E_ALL);
ini_set("display_errors", 1);

const RESPONSE_ID = 0;
const LOGIN_ID = 1;
const REGISTER_ID = 2;
const SEARCH_ID = 7;
const SEARCH_RESP_ID = 8;
const ADD_FILTER_ID = 9;
const REMOVE_FILTER_ID = 10;
const GET_FILTERS_ID = 11;
const GET_FILTERS_RESP_ID = 12;

function getJSONMessage($error, $msg){
	$arr = array();
	$arr["pId"] = RESPONSE_ID;
	$arr["error"] = $error ? 1 : 0;
	$arr["message"] = $msg;
	return json_encode($arr);
}

function getJSONRegister($user, $pass, $email){
	$arr = array();
	$arr["pId"] = REGISTER_ID;
	$arr["user"] = $user;
	$arr["pass"] = $pass;
	$arr["email"] = $email;
	return json_encode($arr);
}

function getJSONLogin($user, $pass){
	$arr = array();
	$arr["pId"] = LOGIN_ID;
	$arr["user"] = $user;
	$arr["pass"] = $pass;
	return json_encode($arr);
}

function getJSONAddFilter($user, $filter){
	$arr = array();
	$arr["pId"] = ADD_FILTER_ID;
	$arr["user"] = $user;
	$arr["filter"] = $filter;
	return json_encode($arr);
}

function getJSONRemoveFilter($user, $filter){
	$arr = array();
	$arr["pId"] = REMOVE_FILTER_ID;
	$arr["user"] = $user;
	$arr["id"] = $filter;
	return json_encode($arr);
}

function getJSONGetFilters($user){
	$arr = array();
	$arr["pId"] = GET_FILTERS_ID;
	$arr["user"] = $user;
	return json_encode($arr);
}

function getJSONSearch($user, $search){
	$arr = array();
	$arr["pId"] = SEARCH_ID;
	$arr["user"] = $user;
	$arr["search"] = $search;
	return json_encode($arr);
}

function getJSONSearchFilter($user, $filter){
	$arr = array();
	$arr["pId"] = SEARCH_ID;
	$arr["user"] = $user;
	$arr["filterId"] = $filter;
	return json_encode($arr);
}

?>