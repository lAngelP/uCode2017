package me.unizar.sql;

import me.unizar.UCode2017;
import me.unizar.packet.ManagerPacket;

public class SQLHelper {
	
	public static int getUsersWithName(String user){
		return UCode2017.getSql().runAsyncNumRows("users", "`name` = '"+user+"'");
	}
	
	public static int getNetworkCount(int id, String user){
		return UCode2017.getSql().runAsyncNumRows("social_networks", "`id` = '"+id+"' AND `user` = '"+user+"'");
	}

	public static int getFilterCount(int id, String user) {
		return UCode2017.getSql().runAsyncNumRows("filters", "`filterId` = '"+id+"' AND `user` = '"+user+"'");
	}
	public static int getFilterCount(String filter, String user, int mode){
		return getFilterCount(filter, user, ManagerPacket.getTwitterMode(mode));
	}
	public static int getFilterCount(String filter, String user, String mode) {
		return UCode2017.getSql().runAsyncNumRows("filters", "`filter` = '"+filter+"' AND `user` = '"+user+"' AND `mode` = '"+mode+"'");
	}

}
