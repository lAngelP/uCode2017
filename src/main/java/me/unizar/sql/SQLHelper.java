package me.unizar.sql;

import me.unizar.UCode2017;

public class SQLHelper {
	
	public static int getUsersWithName(String user){
		return UCode2017.getSql().runAsyncNumRows("users", "`name` = '"+user+"'");
	}

}
