package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketAddFilter implements IPacket {

	public static final int PACKET_ID = 9;

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {
		String user, filter;
		int mode = 0;

		try {
			user = object.getString("user");
			filter = object.getString("filter");
			
			if(object.has("mode")){
				mode = object.getInt("mode");
			}
		} catch (JSONException e) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return true;
		}
		
		if(user.isEmpty() || filter.isEmpty()){
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return true;
		}
		
		if(SQLHelper.getUsersWithName(user) <= 0){
			ManagerPacket.sendErrorMessage(ctx, "Invalid username");
			return true;
		}
		
		if(SQLHelper.getFilterCount(filter, user) > 0){
			ManagerPacket.sendErrorMessage(ctx, "Filter already exists.");
			return true;
		}

		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.ADD_FILTER, new SQLParameterString(user),
				new SQLParameterString(filter), new SQLParameterString(ManagerPacket.getTwitterMode(mode)));
		ManagerPacket.sendSuccessMessage(ctx, "Filter has been added!");
		return false;
	}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
	}

}
