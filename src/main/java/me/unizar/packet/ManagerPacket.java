package me.unizar.packet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import me.unizar.UCode2017;

public class ManagerPacket {
	private static final List<Class<? extends IPacket>> PACKETS = new ArrayList<>();

	private static void registerPacket(int id, Class<? extends IPacket> packet) {
		if (id != PACKETS.size()) {
			UCode2017.getLogger().severe(packet.getName() + " couldn't be registered properly.");
			UCode2017.getLogger().severe("Ids don't match: " + PACKETS.size() + " expected, " + id + " got.");
		}

		PACKETS.add(packet);
	}

	public static IPacket getPacket(int id) {
		if (id < 0 || id >= PACKETS.size()) {
			return null;
		}

		try {
			return PACKETS.get(id).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}

	public static void sendErrorMessage(PrintWriter ctx, String msg) {
		sendPacket(ctx, new PacketResponse(msg, true));
	}

	public static void sendSuccessMessage(PrintWriter ctx, String msg) {
		sendPacket(ctx, new PacketResponse(msg, false));

	}

	public static void sendPacket(PrintWriter ctx, IPacket packet) {
		JSONObject json = new JSONObject();
		packet.send(ctx, json);
		System.err.println("Sending packet: " + json.toString());
		ctx.println(json.toString());
	}

	public static String getNetwork(int type) {
		switch (type) {
		case 0:
			return "TWITTER";
		case 1:
			return "FACEBOOK";
		default:
			return null;
		}
	}

	public static int getNetwork(String type) {
		switch (type) {
		case "TWITTER":
			return 0;
		case "FACEBOOK":
			return 1;
		default:
			return -1;
		}
	}
	
	public static String getTwitterMode(int type) {
		switch (type) {
		case 0:
			return "MIXED";
		case 1:
			return "RECENT";
		case 2:
			return "POPULAR";
		default:
			return null;
		}
	}

	public static int getTwitterMode(String type) {
		switch (type) {
		case "MIXED":
			return 0;
		case "RECENT":
			return 1;
		case "POPULAR":
			return 2;
		default:
			return -1;
		}
	}

	public static void register(){
		registerPacket(PacketResponse.PACKET_ID, PacketResponse.class);

		registerPacket(PacketLogin.PACKET_ID, PacketLogin.class);
		registerPacket(PacketRegister.PACKET_ID, PacketRegister.class);

		registerPacket(PacketAddNetwork.PACKET_ID, PacketAddNetwork.class);
		registerPacket(PacketRemoveNetwork.PACKET_ID, PacketRemoveNetwork.class);
		registerPacket(PacketGetNetworks.PACKET_ID, PacketGetNetworks.class);
		registerPacket(PacketGetNetworksResponse.PACKET_ID, PacketGetNetworksResponse.class);

		registerPacket(PacketSearch.PACKET_ID, PacketSearch.class);
		registerPacket(PacketSearchRequest.PACKET_ID, PacketSearchRequest.class);

		registerPacket(PacketAddFilter.PACKET_ID, PacketAddFilter.class);
		registerPacket(PacketRemoveFilter.PACKET_ID, PacketRemoveFilter.class);
		registerPacket(PacketGetFilters.PACKET_ID, PacketGetFilters.class);
		registerPacket(PacketGetFiltersResponse.PACKET_ID, PacketGetFiltersResponse.class);
		System.out.println("Reg");
	}
}
