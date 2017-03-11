package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketAddNetwork implements IPacket {

	public static final int PACKET_ID = 3;

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {
		String user, oauth, value, type;

		try {
			user = object.getString("user");
			oauth = object.getString("oauth");
			value = object.getString("value");
			type = ManagerPacket.getNetwork(object.getInt("type"));
		} catch (JSONException e) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return true;
		}
		
		if(user.isEmpty() || oauth.isEmpty() || value.isEmpty()){
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return true;
		}
		
		if(type == null){
			ManagerPacket.sendErrorMessage(ctx, "Invalid Network");
			return true;
		}
		
		if(SQLHelper.getUsersWithName(user) <= 0){
			ManagerPacket.sendErrorMessage(ctx, "Invalid username");
			return true;
		}

		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.ADD_NETWORK, new SQLParameterString(user),
				new SQLParameterString(type), new SQLParameterString(value),
				new SQLParameterString(oauth));
		ManagerPacket.sendSuccessMessage(ctx, "Network has been added!");
		return false;
	}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
	}

}
