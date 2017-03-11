package me.unizar.packet;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterInteger;

public class PacketRemoveNetwork implements IPacket{
	
	public static final int PACKET_ID = 4;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		String user;
		int id;
		
		try{
			user = object.getString("user");
			id = object.getInt("id");
		}catch(JSONException e){
			ManagerPacket.sendErrorMessage(ctx, "Malformed RemoveNetwork packet");
			return;
		}
		
		if(user.isEmpty() || id < 0){
			ManagerPacket.sendErrorMessage(ctx, "Malformed RemoveNetwork packet");
			return;
		}
		
		if(SQLHelper.getNetworkCount(id, user) <= 0){
			ManagerPacket.sendErrorMessage(ctx, "No network found");
			return;
		}
		
		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.REMOVE_NETWORK, new SQLParameterInteger(id));
	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {}

}
