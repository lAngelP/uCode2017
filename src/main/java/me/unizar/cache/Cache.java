package me.unizar.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import me.unizar.packet.PacketSearchRequest;

public class Cache {
	
	private Map<Integer, CacheId> reqs = new TreeMap<>();
	
	private int id = 0;
	
	public boolean isInCache(int filterId){
		if(id >= 20){
			List<Integer> clean = new ArrayList<>();
			for (Integer i : reqs.keySet()) {
				if(reqs.get(i).isExpired()){
					clean.add(i);
				}
			}
			id = 0;
			for (Integer integer : clean) {
				reqs.remove(integer);
			}
		}
		
		return reqs.containsKey(filterId);
	}
	
	public void addToCache(int id, PacketSearchRequest req){
		reqs.put(id, new CacheId(req));
	}
	
	public PacketSearchRequest getRequest(int id){
		return reqs.get(id).getPacket();
	}

}
