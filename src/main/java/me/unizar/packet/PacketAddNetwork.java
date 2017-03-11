package me.unizar.packet;

import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;

public class PacketAddNetwork implements IPacket{
	
	public static int PACKET_ID = 3;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
		// TODO Auto-generated method stub
		
	}

}
