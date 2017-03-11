package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterInteger;

public class PacketRemoveFilter implements IPacket{
	
	public static final int PACKET_ID = 10;

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {
		String user;
		int id;
		
		try{
			user = object.getString("user");
			id = object.getInt("id");
		}catch(JSONException e){
			ManagerPacket.sendErrorMessage(ctx, "Malformed RemoveFilter packet");
			return true;
		}
		
		if(user.isEmpty() || id < 0){
			ManagerPacket.sendErrorMessage(ctx, "Malformed RemoveFilter packet");
			return true;
		}
		
		if(SQLHelper.getFilterCount(id, user) <= 0){
			ManagerPacket.sendErrorMessage(ctx, "No Filter found");
			return true;
		}
		
		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.REMOVE_FILTER, new SQLParameterInteger(id));
		return false;
	}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {}

}
