package me.unizar.packet;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import me.unizar.UCode2017;
import me.unizar.crypt.Crypter;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketRegister implements IPacket {
	
	public static final int PACKET_ID = 2;

	@Override
	public void handle(ChannelHandlerContext ctx, JSONObject object) {
		String user, pass, email;
		try {
			user = object.getString("user");
			pass = object.getString("pass");
			email = object.getString("email");
		} catch (JSONException ex) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed register packet.");
			return;
		}
		
		if(user.isEmpty() || pass.isEmpty() || email.isEmpty()){
			ManagerPacket.sendErrorMessage(ctx, "Malformed register packet.");
			return;
		}

		if (SQLHelper.getUsersWithName(user) > 0) {
			ManagerPacket.sendErrorMessage(ctx, "User already exists.");
			return;
		}

		String hashedPW = Crypter.hashPassword(pass);

		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.REGISTER_USER, new SQLParameterString(user),
				new SQLParameterString(hashedPW), new SQLParameterString(email));
		
		ManagerPacket.sendSuccessMessage(ctx, "You've been registered successfully!");

	}

	@Override
	public void send(ChannelHandlerContext ctx, JSONObject object) {}

}
