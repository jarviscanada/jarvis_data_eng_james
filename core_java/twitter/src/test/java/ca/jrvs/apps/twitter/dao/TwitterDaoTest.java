package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterDaoTest {

    private TwitterDao dao;
    private Tweet post;

    @Before
    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);

        //setup dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);

        //pass dependency
        this.dao = new TwitterDao(httpHelper);

        //testing create function

        String text = "test create!" + System.currentTimeMillis();
        Double lon = -118.0;
        Double lat = 34.0;
        Tweet postTweet = TweetUtil.buildTweet(text,lon,lat);

        Tweet tweet = dao.create(postTweet);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCooridinates());
        assertEquals(2,tweet.getCooridinates().getCoordinates().size());
        assertEquals(lon,tweet.getCooridinates().getCoordinates().get(0));
        assertEquals(lat,tweet.getCooridinates().getCoordinates().get(1));
        post = tweet;
    }

    @Test
    public void findById() {
        long id = post.getId();
        Tweet tweet = dao.findById(Long.toString(id));

        assertEquals(post.getText(), tweet.getText());
        assertNotNull(tweet.getCooridinates());
        assertEquals(2,tweet.getCooridinates().getCoordinates().size());
        assertEquals(post.getCooridinates().getCoordinates().get(0),tweet.getCooridinates().getCoordinates().get(0));
        assertEquals(post.getCooridinates().getCoordinates().get(1),tweet.getCooridinates().getCoordinates().get(1));
    }

    @After
    public void deleteById() {
        long id = post.getId();
        Tweet tweet = dao.deleteById(Long.toString(id));

        assertEquals(post.getText(), tweet.getText());
        assertNotNull(tweet.getCooridinates());
        assertEquals(2,tweet.getCooridinates().getCoordinates().size());
        assertEquals(post.getCooridinates().getCoordinates().get(0),tweet.getCooridinates().getCoordinates().get(0));
        assertEquals(post.getCooridinates().getCoordinates().get(1),tweet.getCooridinates().getCoordinates().get(1));

    }
}