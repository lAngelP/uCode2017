package me.unizar.packet;

import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;

public class PacketResponse implements IPacket{
	
	public static final int PACKET_ID = 0;
	
	private int error = 0;
	private String msg;
	
	public PacketResponse(String msg, boolean error) {
		this.msg = msg;
		this.error = error ? 1 : 0;
	}

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
		object.append("pId", PACKET_ID);
		object.append("error", error);
		object.append("message", msg);
	}

}
