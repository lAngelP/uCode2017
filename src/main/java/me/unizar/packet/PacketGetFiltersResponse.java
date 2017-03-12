package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

public class PacketGetFiltersResponse implements IPacket{
	
	public static final int PACKET_ID = 12;
	
	private JSONArray obj = new JSONArray();
	private int filters = 0;
	
	public void addFilter(int id, String value, int mode){
		JSONObject base = new JSONObject();
		base.append("id", id);
		base.append("value", value);
		base.append("mode", mode);
		
		obj.put(base);
		filters++;
	}

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {return true;}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
		object.append("pId", PACKET_ID);
		object.append("filterCount", filters);
		object.append("filters", obj);
	}
	

}
