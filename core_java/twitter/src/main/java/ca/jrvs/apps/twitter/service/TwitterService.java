package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TwitterService implements Service {

    private CrdDao dao;


    public TwitterService(CrdDao dao) {
        this.dao=dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePostTweet(tweet);

        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateId(id);

        return (Tweet) dao.findById(id);
    }


    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List<Tweet> deleted = new ArrayList<>();

        for(String id : ids) {
            validateId(id);
            deleted.add((Tweet) dao.deleteById(id));
        }

        return deleted;
    }


    /**
     * Helper function to ckeck if the text is in vaild length
     * check if lat/lon is within range
     * @param tweet
     */
    private void validatePostTweet(Tweet tweet) {
        //check if the length exceeds 140 characters
        if (tweet.getText().length() > 140) {
            throw new IllegalArgumentException("Tweet is too long");
        }

        //check if lon/lst is within range
        Double lon = tweet.getCooridinates().getCoordinates().get(0);
        Double lat = tweet.getCooridinates().getCoordinates().get(1);
        if (lon > 180 || lon < -180 || lat > 90 || lat <-90) {
            throw new IllegalArgumentException("Coordinates out of range");
        }

        return;
    }

    /**
     * check if the tweet id is all digit
     * @param id
     */
    private void validateId(String id) {
        if (!id.matches("[0-9]+")) {
            throw new IllegalArgumentException("Invaild Tweet Id");
        }
        return;
    }
}
