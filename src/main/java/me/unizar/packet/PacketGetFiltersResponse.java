package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

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
	public boolean handle(PrintWriter ctx, JSONObject object) {return true;}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
		obj.append("pId", PACKET_ID);
		obj.append("filterCount", filters);
		object = obj;
	}
	

}
