package me.unizar.packet;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.UCode2017;
import me.unizar.crypt.Crypter;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterString;

public class PacketRegister implements IPacket {
	
	public static final int PACKET_ID = 2;

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {
		String user, pass, email;
		System.out.println("0");
		try {
			user = object.getString("user");
			pass = object.getString("pass");
			email = object.getString("email");
		} catch (JSONException ex) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed register packet.");
			return true;
		}
		System.out.println("1");
		
		if(user.isEmpty() || pass.isEmpty() || email.isEmpty()){
			ManagerPacket.sendErrorMessage(ctx, "Malformed register packet.");
			return true;
		}
		System.out.println("2");

		if (SQLHelper.getUsersWithName(user) > 0) {
			ManagerPacket.sendErrorMessage(ctx, "User already exists.");
			return true;
		}
		System.out.println("3");

		String hashedPW = Crypter.hashPassword(pass);

		UCode2017.getSql().runAsyncUpdate(SQLRegisterBase.REGISTER_USER, new SQLParameterString(user),
				new SQLParameterString(hashedPW), new SQLParameterString(email));
		System.out.println("4");
		
		ManagerPacket.sendSuccessMessage(ctx, "You've been registered successfully!");
		System.out.println("5");
		return false;
	}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {}

}
