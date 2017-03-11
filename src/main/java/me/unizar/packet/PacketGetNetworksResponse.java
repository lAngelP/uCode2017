package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

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
	public boolean handle(PrintWriter ctx, JSONObject object) {return true;}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
		obj.append("pId", PACKET_ID);
		obj.append("networkCount", networks);
		object = obj;
	}
	

}
