package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;

import java.util.List;

public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) {

        //check if the args have the correct length
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid Post Arguments");
        }

        //get post info
        String text = args[1];
        String[] cooridinates = args[2].split(COORD_SEP);

        //validate the post info
        if (cooridinates.length != 2 || text.isEmpty()) {
            throw new IllegalArgumentException("Invalid location or empty post");
        }

        Double lat, lon;

        try {
            lat = Double.parseDouble(cooridinates[0]);
            lon = Double.parseDouble(cooridinates[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid location parameters",e);
        }

        Tweet tweet = TweetUtil.buildTweet(text,lon,lat);
        return service.postTweet(tweet);
    }

    @Override
    public Tweet showTweet(String[] args) {
        //check if the args have the correct length
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid Show Arguments");
        }

        String id = args[1];
        //check if Id is empty
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Empty ID field");
        }

        return service.showTweet(id, null);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        //check if the args have the correct length
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid Delete Arguments");
        }

        String[] ids = args[1].split(COMMA);

        if (ids.length == 0) {
            throw new IllegalArgumentException("Empty ID field");
        }
        //check if Id is empty
        for (String id : ids ) {
            if (id.isEmpty()) {
                throw new IllegalArgumentException("Empty ID field");
            }
        }

        return service.deleteTweets(ids);
    }
}
