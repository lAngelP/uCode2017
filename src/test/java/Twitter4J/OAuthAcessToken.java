/*
Using Twitter4J*
http://twitter4j.org/en/index.html
http://twitter4j.org/en/code-examples.html

Adapted By: Jorge Pinilla
*/
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OAuthAcessToken {

    public AccessToken GetAcessToken(Twitter twitter) throws IOException {
		/*twitter must be inicialized with a (TwitterFactory)tf.getInstance()
		tf must be build with a configurationBuilder cb set with Consumer and Secret key
		Example code:
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("Public Consumer Key")
				.setOAuthConsumerSecret("Private Consumer Key");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		*/

        RequestToken requestToken = null;
        AccessToken accessToken = null;
        try {
            requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");
            System.out.println("Request token: " + requestToken.getToken());
            System.out.println("Request token secret: " + requestToken.getTokenSecret());
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                String pin = br.readLine();
                try {
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken(requestToken);
                    }
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                }
            }
            System.out.println("Got access token.");
            System.out.println("Access token: " + accessToken.getToken());
            System.out.println("Access token secret: " + accessToken.getTokenSecret());
        } catch (TwitterException e) {
            System.out.println("Error getting OAuthRequest Token");
            e.printStackTrace();
        }
        return accessToken;
    }
}