package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterInteger;

public class PacketRemoveNetwork implements IPacket{
	
	public static final int PACKET_ID = 4;

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {
		String user;
		int id;
		
		try{
			user = object.getString("user");
			id = object.getInt("id");
		}catch(JSONException e){
			ManagerPacket.sendErrorMessage(ctx, "Malformed RemoveNetwork packet");
			return true;
		}
		
		if(user.isEmpty() || id < 0){
			ManagerPacket.sendErrorMessage(ctx, "Malformed RemoveNetwork packet");
			return true;
		}
		
		if(SQLHelper.getNetworkCount(id, user) <= 0){
			ManagerPacket.sendErrorMessage(ctx, "No network found");
			return true;
		}
		
		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.REMOVE_NETWORK, new SQLParameterInteger(id));
		return false;
	}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {}

}
