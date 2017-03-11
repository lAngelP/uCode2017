package me.unizar.packet;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketAddFilter implements IPacket {

	public static final int PACKET_ID = 9;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		String user, filter;

		try {
			user = object.getString("user");
			filter = object.getString("filter");
		} catch (JSONException e) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return;
		}
		
		if(user.isEmpty() || filter.isEmpty()){
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return;
		}
		
		if(SQLHelper.getUsersWithName(user) <= 0){
			ManagerPacket.sendErrorMessage(ctx, "Invalid username");
			return;
		}

		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.ADD_FILTER, new SQLParameterString(user),
				new SQLParameterString(filter));
		ManagerPacket.sendSuccessMessage(ctx, "Filter has been added!");
	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
	}

}
