package me.unizar.packet;

import org.json.JSONArray;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;

public class PacketGetNetworksResponse implements IPacket{
	
	public static final int PACKET_ID = 6;
	
	private JSONObject obj = new JSONObject();
	private int networks = 0;
	
	public void addNetwork(int id, int type, String name){
		if(!obj.has("networks")){
			obj.append("networks", new JSONArray());
		}
		
		JSONObject base = new JSONObject();
		base.append("id", id);
		base.append("type", type);
		base.append("name", name);
		
		obj.getJSONArray("networks").put(base);
		networks++;
	}

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
		obj.append("pId", PACKET_ID);
		obj.append("networks", networks);
		object = obj;
	}
	

}
