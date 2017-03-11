package me.unizar.packet;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import me.unizar.UCode2017;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketGetNetworks implements IPacket {

	public static final int PACKET_ID = 5;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		String user;

		try {
			user = object.getString("user");
		} catch (JSONException e) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed GetNetworks packet.");
			return;
		}

		if (SQLHelper.getUsersWithName(user) <= 0) {
			ManagerPacket.sendErrorMessage(ctx, "Unknown username.");
			return;
		}

		ResultSet set = UCode2017.getSql().runAsyncQuery(SQLRegisterBase.GET_NETWORKS, new SQLParameterString(user));
		PacketGetNetworksResponse response = new PacketGetNetworksResponse();
		try {
			while(set.next()){
				response.addNetwork(set.getInt("id"), ManagerPacket.getNetwork(set.getString("network")), set.getString("value"));
			}
		} catch (SQLException e) {
			ManagerPacket.sendErrorMessage(ctx, "Unknown error.");
			return;
		}
		
		ManagerPacket.sendPacket(ctx, response);
	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {
	}

}
