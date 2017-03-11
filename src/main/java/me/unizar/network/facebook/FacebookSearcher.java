package me.unizar.network.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import me.unizar.network.Network.NetworkType;
import me.unizar.packet.PacketSearchRequest;

public class FacebookSearcher {

	private Facebook facebook;

	public FacebookSearcher() {
		// Make the configuration builder
		ConfigurationBuilder confBuilder = new ConfigurationBuilder();
		confBuilder.setDebugEnabled(true);

		// Set application id, secret key and access token
		// Get from developers.facebook.com by just creating a new app.
		confBuilder.setOAuthAppId("686574691515191");
		confBuilder.setOAuthAppSecret("a4440d5428aeb5ffe53777d90113a98e");
		confBuilder.setOAuthAccessToken(
				"EAAJwb6CZAezcBABGMGiIthKXq4kE94uzuMFSfKyGbI3ExHvZBcHseszlTUf2Q4ETZApME5lpi1GQdZCamKdl9rLVKnfBATjx2kgBYchXvUZCk2UBzKx1uRqDkJCWtS59DLm0hWdT2rFTdwAelQZCAGYWVIhkYjotZCQZAQsqfc6rjhkHi0j2ILHdLwfm1c26Y6cZD");

		// Set permission
		confBuilder.setOAuthPermissions("email,publish_stream, id, name, first_name, last_name, generic");
		confBuilder.setUseSSL(true);
		confBuilder.setJSONStoreEnabled(true);

		// Create configuration object
		Configuration configuration = confBuilder.build();

		// Create facebook instance
		FacebookFactory ff = new FacebookFactory(configuration);
		facebook = ff.getInstance();
	}

	public void search(PacketSearchRequest req, String search) {
		ResponseList<Post> results;
		try {
			results = facebook.getPosts(search);
		} catch (FacebookException e) {
			return;
		}
		int i = 0;
		for (Post post : results) {
			String postId = post.getId();
			String[] data = postId.split("_");

			req.addToNetwork(NetworkType.FACEBOOK, data[0], data[1]);
			
			i++;
			if (i >= 10) {
				break;
			}
		}
	}

}