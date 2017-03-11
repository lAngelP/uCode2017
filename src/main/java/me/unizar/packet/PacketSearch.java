package me.unizar.packet;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterInteger;

public class PacketSearch implements IPacket{
	
	public static final int PACKET_ID = 7;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		String user, search = null;
		int fId = -1;
		try{
			user = object.getString("user");
			if(!object.has("filter")){
				search = object.getString("search");
			}else{
				fId = object.getInt("filterId");
			}
		}catch(JSONException e){
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return;
		}
		
		if(search == null){
			if(fId < 0 || SQLHelper.getFilterCount(fId, user) <= 0){
				ManagerPacket.sendErrorMessage(ctx, "Unknown filter.");
				return;
			}
			
			ResultSet set = UCode2017.getSql().runAsyncQuery(SQLRegisterBase.GET_FILTER, new SQLParameterInteger(fId));
			
			try {
				set.first();
				search = set.getString("filter");
			} catch (SQLException e) {
				ManagerPacket.sendErrorMessage(ctx, "Unknown error.");
				return;
			}
		}
		
		PacketSearchRequest packet = new PacketSearchRequest();
		
		//TODO LAUNCH FB AND TW SEARCH
		
		ManagerPacket.sendPacket(ctx, packet);
	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
		// TODO Auto-generated method stub
		
	}

}
