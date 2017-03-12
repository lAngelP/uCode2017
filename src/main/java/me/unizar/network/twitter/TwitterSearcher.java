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
                .setOAuthConsumerKey("TVOvXzJopg25XLao1SPBSqIgi")
                .setOAuthConsumerSecret("teE7QTTcg94k9UbK0O8Ho88ESejhdraWGdbKXs2KRCtxV3i5Qk")
                .setOAuthAccessToken("2510636970-y3ImfObqKC6Q8Z7d80QUo43WUmd17cBaM5sCADE")
                .setOAuthAccessTokenSecret("dkuGpfGASezJ6tM33eNBwj2IfyizeHhxoKOSNdYVBsusz");
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
