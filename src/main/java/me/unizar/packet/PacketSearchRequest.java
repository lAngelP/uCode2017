package me.unizar.packet;

import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import me.unizar.network.Network;
import me.unizar.network.Network.NetworkType;

public class PacketSearchRequest implements IPacket{
	
	public static final int PACKET_ID = 8;
	
	private Network fb = new Network(NetworkType.FACEBOOK);
	private Network tw = new Network(NetworkType.TWITTER);

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {}

	public void addToNetwork(NetworkType type, String user, String post){
		switch (type) {
		case FACEBOOK:
			fb.addNetworkData(user, post);
			break;
		case TWITTER:
			tw.addNetworkData(user, post);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
		object.append("pId", PACKET_ID);
		fb.generateJSON(object);
		tw.generateJSON(object);
	}

}
