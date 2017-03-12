package me.unizar.packet;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.UCode2017;
import me.unizar.sql.MySQLConnection;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketGetNetworks implements IPacket {

	public static final int PACKET_ID = 5;

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {
		String user;

		try {
			user = object.getString("user");
		} catch (JSONException e) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed GetNetworks packet.");
			return true;
		}

		if (SQLHelper.getUsersWithName(user) <= 0) {
			ManagerPacket.sendErrorMessage(ctx, "Unknown username.");
			return true;
		}

		ResultSet set = UCode2017.getSql().runAsyncQuery(SQLRegisterBase.GET_NETWORKS, new SQLParameterString(user));
		PacketGetNetworksResponse response = new PacketGetNetworksResponse();
		try {
			while(set.next()){
				response.addNetwork(set.getInt("id"), ManagerPacket.getNetwork(set.getString("network")), set.getString("value"));
			}
		} catch (SQLException e) {
			ManagerPacket.sendErrorMessage(ctx, "Unknown error.");
			MySQLConnection.closeStatement(set);
			return true;
		}finally {
			MySQLConnection.closeStatement(set);
		}
		
		ManagerPacket.sendPacket(ctx, response);
		return false;
	}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
	}

}
