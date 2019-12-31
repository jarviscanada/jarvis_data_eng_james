package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Cooridinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetUtil {

    /**
     * Create a tweet object
     * @param text
     * @param lon
     * @param lat
     * @return
     */
    public static Tweet buildTweet (String text, Double lon, Double lat) {

        Tweet tweet = new Tweet();
        tweet.setText(text);
        Cooridinates cooridinates = new Cooridinates();
        List<Double> list = new ArrayList<>();
        list.add(lon);
        list.add(lat);
        cooridinates.setCoordinates(list);
        tweet.setCooridinates(cooridinates);
        return tweet;
    }
}
