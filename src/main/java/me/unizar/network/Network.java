package me.unizar.network;

import org.json.JSONArray;
import org.json.JSONObject;

public class Network {
	
	private NetworkType type;
	private int networks = 0;
	private JSONArray data = new JSONArray();
	
	public Network(NetworkType type){
		this.type = type;
	}
	
	public void addNetworkData(String user, String post){
		JSONObject json = new JSONObject();
		json.append("user", user);
		json.append("post", post);
		data.put(json);
		networks++;
	}
	
	public void generateJSON(JSONObject object){
		object.append(type.getName() + "Count", networks);
		object.append(type.getName(), data);
	}

	public static enum NetworkType {
		TWITTER("tw"),
		FACEBOOK("fb");
		
		private String name;
		private NetworkType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
