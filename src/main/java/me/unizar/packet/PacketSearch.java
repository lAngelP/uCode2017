package me.unizar.packet;

import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;

public class PacketSearch implements IPacket{
	
	public static final int PACKET_ID = 6;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
		// TODO Auto-generated method stub
		
	}

}