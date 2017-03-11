package me.unizar.packet;

import org.json.JSONArray;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;

public class PacketGetFiltersResponse implements IPacket{
	
	public static final int PACKET_ID = 12;
	
	private JSONObject obj = new JSONObject();
	private int filters = 0;
	
	public void addFilter(int id, String value){
		if(!obj.has("filters")){
			obj.append("filters", new JSONArray());
		}
		
		JSONObject base = new JSONObject();
		base.append("id", id);
		base.append("value", value);
		
		obj.getJSONArray("filters").put(base);
		filters++;
	}

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
		obj.append("pId", PACKET_ID);
		obj.append("filterCount", filters);
		object = obj;
	}
	

}
