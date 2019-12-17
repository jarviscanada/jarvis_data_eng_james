package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterService twitterService;
    private List<Tweet> tweets;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);

        //setup dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);

        //pass dependency
        TwitterDao dao = new TwitterDao(httpHelper);
        this.twitterService = new TwitterService(dao);
        tweets = new ArrayList<>();

        //create tweets
        for(int i = 0; i < 4; i++) {
            String text = "Test Twitter Service at" + System.currentTimeMillis();
            Double lon = -118.0;
            Double lat = 34.0;
            Tweet postTweet = TweetUtil.buildTweet(text,lon,lat);
            System.out.println(JsonUtil.toPrettyJson(postTweet));

            Tweet tweet = twitterService.postTweet(postTweet);

            assertEquals(text, tweet.getText());
            assertNotNull(tweet.getCooridinates());
            assertEquals(2,tweet.getCooridinates().getCoordinates().size());
            assertEquals(lon,tweet.getCooridinates().getCoordinates().get(0));
            assertEquals(lat,tweet.getCooridinates().getCoordinates().get(1));

            tweets.add(tweet);
        }
    }

    @Test
    public void showTweet() {
        for (Tweet post : tweets) {
            long id = post.getId();
            Tweet tweet = twitterService.showTweet(Long.toString(id), null);

            assertEquals(post.getText(), tweet.getText());
            assertNotNull(tweet.getCooridinates());
            assertEquals(2,tweet.getCooridinates().getCoordinates().size());
            assertEquals(post.getCooridinates().getCoordinates().get(0),tweet.getCooridinates().getCoordinates().get(0));
            assertEquals(post.getCooridinates().getCoordinates().get(1),tweet.getCooridinates().getCoordinates().get(1));

        }
    }

    @After
    public void deleteTweets() {
        String[] ids = new String[4] ;
        int index = 0;
        for (Tweet post : tweets) {
            ids[index] = Long.toString(post.getId());
            index++;
        }
        List<Tweet> deleted = twitterService.deleteTweets(ids);

        assertNotNull(deleted);
    }
}