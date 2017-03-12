package me.unizar.cache;

import me.unizar.packet.PacketSearchRequest;

public class CacheId {
	
	private long time = System.currentTimeMillis();
	private PacketSearchRequest req;
	
	public CacheId(PacketSearchRequest req) {
		this.req = req;
	}
	
	public boolean isExpired(){
		return System.currentTimeMillis() - time >= 600000;
	}
	
	public PacketSearchRequest getPacket(){
		return req;
	}

}
