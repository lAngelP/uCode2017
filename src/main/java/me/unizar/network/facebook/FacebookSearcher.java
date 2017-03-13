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
		confBuilder.setOAuthAppId("Your App Id"); //https://developers.facebook.com/apps
		confBuilder.setOAuthAppSecret("Your App Secret ID"); //https://developers.facebook.com/apps
		confBuilder.setOAuthAccessToken("OAuth Token");
		/*
		Make a OAuth autentification and get a token, once you get a token you can set
		it with der.setOAuthAcessToken(token)
		More information about accesTokens: https://developers.facebook.com/docs/facebook-login/access-tokens
		*/

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
			System.out.println("FB " + postId);
			req.addToNetwork(NetworkType.FACEBOOK, data[0], data[1]);
			
			i++;
			if (i >= 10) {
				break;
			}
		}
	}

}
