package me.unizar.network.twitter;

import java.util.List;

import me.unizar.network.Network.NetworkType;
import me.unizar.packet.PacketSearchRequest;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSearcher {
	
	private Twitter twitter;
	
	public TwitterSearcher() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("App Consumer Key") //https://apps.twitter.com/
                .setOAuthConsumerSecret("Secret App Consumer Key") //https://apps.twitter.com/
                .setOAuthAccessToken("OAuth Token")
                .setOAuthAccessTokenSecret("OAuth Private Token");
				/*
				Make a OAuth autentification and get a token, once you get a token you can set
				it with cb.setOAuthAcessToken(token) and cb.setOAuthAcessTokenSecret(token)
				More information about accesTokens: https://dev.twitter.com/oauth/overview/application-owner-access-tokens
				Code Example of OAuth using Twitter4J: \uCode2017\src\test\java\Twitter4J\OAuthAcessToken.java	
				*/
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
	}
	
	public void search(PacketSearchRequest req, String search, int mode){
		try {
            Query query = new Query(search);
            if(mode == 0){
            	query.setResultType(Query.MIXED);
            }else if(mode == 1){
            	query.setResultType(Query.RECENT);
            }else if(mode == 2){
            	query.setResultType(Query.POPULAR);
            }
            query.setCount(50);
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            int i = 0;
            for (Status tweet : tweets) {
                req.addToNetwork(NetworkType.TWITTER, String.valueOf(tweet.getUser().getId()), String.valueOf(tweet.getId()));
                i++;
                if(i >= 10){
                	break;
                }
            }

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
	}

}
