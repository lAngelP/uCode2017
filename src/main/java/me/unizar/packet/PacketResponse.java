package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONObject;

public class PacketResponse implements IPacket{
	
	public static final int PACKET_ID = 0;
	
	private int error = 0;
	private String msg;
	
	public PacketResponse(String msg, boolean error) {
		this.msg = msg;
		this.error = error ? 1 : 0;
	}

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {return true;}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
		object.append("pId", PACKET_ID);
		object.append("error", error);
		object.append("message", msg);
	}

}
