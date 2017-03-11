package me.unizar.packet;

import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;

public interface IPacket {
	
	void handle(ChannelHandlerContext ctx, JSONObject object);
	
	void send(ChannelHandlerContext ctx,JSONObject object);

}
