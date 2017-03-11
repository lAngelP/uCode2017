package me.unizar.packet;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import me.unizar.UCode2017;
import me.unizar.crypt.Crypter;
import me.unizar.sql.MySQLConnection;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketLogin implements IPacket{
	
	public static final int PACKET_ID = 1;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		String user, pass;
		try{
			user = object.getString("user");
			pass = object.getString("pass");
		}catch(JSONException ex){
			ManagerPacket.sendErrorMessage(ctx, "Malformed login packet.");
			return;
		}
		
		if(user.isEmpty() || pass.isEmpty()){
			ManagerPacket.sendErrorMessage(ctx, "Malformed login packet.");
			return;
		}
		
		if(SQLHelper.getUsersWithName(user) <= 0){
			ManagerPacket.sendErrorMessage(ctx, "Invalid username/password.");
			return;
		}
		
		String hashedPW = null;
		
		ResultSet set = UCode2017.getSql().runAsyncQuery(SQLRegisterBase.GET_PASSWORD, new SQLParameterString(user));
		try {
			set.first();
			hashedPW = set.getString("pass");
		} catch (SQLException e) {
		}finally {
			MySQLConnection.closeStatement(set);
		}
		
		if(hashedPW == null){
			ManagerPacket.sendErrorMessage(ctx, "Unknown error happened.");
			return;
		}
		
		if(Crypter.checkPassword(pass, hashedPW)){
			ManagerPacket.sendSuccessMessage(ctx, "You logged in successfully.");
			return;
		}else{
			ManagerPacket.sendErrorMessage(ctx, "Invalid username/password.");
			return;
		}
	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {}

}
