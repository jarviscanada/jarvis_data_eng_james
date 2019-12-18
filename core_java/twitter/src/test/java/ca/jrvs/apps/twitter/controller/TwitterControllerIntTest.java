package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    private TwitterController controller;
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
        CrdDao dao = new TwitterDao(httpHelper);
        Service twitterService = new TwitterService(dao);
        this.controller = new TwitterController(twitterService);
        tweets = new ArrayList<>();

        //create tweets
        for(int i = 0; i < 4; i++) {
            String text = "Test Twitter Controller at" + System.currentTimeMillis();
            Double lon = -118.0;
            Double lat = 34.0;
            Tweet postTweet = TweetUtil.buildTweet(text,lon,lat);

            String[] args = new String[3];
            args[0] = "POST";
            args[1] = text;
            args[2] = lat + ":" + lon;
            Tweet tweet = controller.postTweet(args);

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
            String[] args = new String[2];
            args[0] = "SHOW";
            args[1] = Long.toString(id);
            Tweet tweet = controller.showTweet(args);

            assertEquals(post.getText(), tweet.getText());
            assertNotNull(tweet.getCooridinates());
            assertEquals(2,tweet.getCooridinates().getCoordinates().size());
            assertEquals(post.getCooridinates().getCoordinates().get(0),tweet.getCooridinates().getCoordinates().get(0));
            assertEquals(post.getCooridinates().getCoordinates().get(1),tweet.getCooridinates().getCoordinates().get(1));

        }
    }

    @After
    public void deleteTweet() {
        String[] ids = new String[4] ;
        int index = 0;
        for (Tweet post : tweets) {
            ids[index] = Long.toString(post.getId());
            index++;
        }

        String[] args = new String[2];
        args[0] = "DELETE";
        args[1] = String.join(",", ids);
        List<Tweet> deleted = controller.deleteTweet(args);

        assertNotNull(deleted);

    }
}