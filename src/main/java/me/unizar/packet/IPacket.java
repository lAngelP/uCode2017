package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONObject;

public interface IPacket {
	
	boolean handle(PrintWriter ctx, JSONObject object);
	
	void send(PrintWriter ctx,JSONObject object);

}
