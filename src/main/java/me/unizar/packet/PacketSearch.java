package me.unizar.packet;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.UCode2017;
import me.unizar.cache.Cache;
import me.unizar.network.facebook.FacebookSearcher;
import me.unizar.network.twitter.TwitterSearcher;
import me.unizar.sql.MySQLConnection;
import me.unizar.sql.SQLHelper;
import me.unizar.sql.SQLRegisterBase;
import me.unizar.sql.parameters.SQLParameterInteger;

public class PacketSearch implements IPacket {
	
	private static final FacebookSearcher fbSearcher = new FacebookSearcher();
	private static final TwitterSearcher twSearcher = new TwitterSearcher();
	private static final Cache cache = new Cache();

	public static final int PACKET_ID = 7;

	@Override
	public boolean handle(PrintWriter ctx, JSONObject object) {
		String user, search = null;
		int fId = -1, mode = 0;
		try {
			user = object.getString("user");
			if (!object.has("filterId")) {
				search = object.getString("search");
				if(object.has("mode")){
					mode = object.getInt("mode");
				}
			} else {
				fId = object.getInt("filterId");
			}
		} catch (JSONException e) {
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			return true;
		}

		if(cache.isInCache(fId)){
			ManagerPacket.sendPacket(ctx, cache.getRequest(fId));
			return false;
		}
		
		if (search == null) {
			if (fId < 0 || SQLHelper.getFilterCount(fId, user) <= 0) {
				ManagerPacket.sendErrorMessage(ctx, "Unknown filter.");
				return true;
			}

			ResultSet set = UCode2017.getSql().runAsyncQuery(SQLRegisterBase.GET_FILTER, new SQLParameterInteger(fId));

			try {
				set.first();
				search = set.getString("filter");
				mode = ManagerPacket.getTwitterMode(set.getString("mode"));
			} catch (SQLException e) {
				ManagerPacket.sendErrorMessage(ctx, "Unknown error.");
				MySQLConnection.closeStatement(set);
				return true;
			}finally {
				MySQLConnection.closeStatement(set);
			}
		}

		PacketSearchRequest packet = new PacketSearchRequest();

		fbSearcher.search(packet, search);
		twSearcher.search(packet, search, mode);

		ManagerPacket.sendPacket(ctx, packet);
		cache.addToCache(fId, packet);
		return false;
	}

	@Override
	public void send(PrintWriter ctx, JSONObject object) {
		// TODO Auto-generated method stub

	}

}
