package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    Tweet expectedTweet;
    @Before
    public void setup() throws Exception{
        String tweetJsonStr = "{\n" +
                "  \"created_at\" : \"Thu Dec 19 21:12:46 +0000 2019\",\n" +
                "  \"id\" : 1207770774108622848,\n" +
                "  \"id_str\" : \"1207770774108622848\",\n" +
                "  \"text\" : \"Test main cli2\",\n" +
                "  \"entities\" : {\n" +
                "    \"hashtags\" : [ ],\n" +
                "    \"user_mentions\" : [ ]\n" +
                "  },\n" +
                "  \"coordinates\" : {\n" +
                "    \"coordinates\" : [ 145.0, 18.0 ],\n" +
                "    \"type\" : \"Point\"\n" +
                "  },\n" +
                "  \"retweet_count\" : 0,\n" +
                "  \"retweeted\" : false,\n" +
                "  \"entites\" : {\n" +
                "    \"hashtags\" : [ ],\n" +
                "    \"user_mentions\" : [ ]\n" +
                "  },\n" +
                "  \"favourite_count\" : 0\n" +
                "}";

        try {
            expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        }catch (IOException e){
            throw new IOException();
        }
    }

    @Test
    public void postTweet() {
        //test failed request
        when(dao.create(any())).thenReturn(new Tweet());
        try {
            //lon is exceeding the limit
            service.postTweet(TweetUtil.buildTweet("Mockito test", 360.0, 1d));
            fail();
        }catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        Tweet tweet = service.postTweet(expectedTweet);

        assertNotNull(tweet);
    }

    @Test
    public void showTweet() {
        when(dao.findById(any())).thenReturn(new Tweet());
        try {
            //is contains character
            service.showTweet("abcdefg",null);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        Tweet tweet = service.showTweet("1207770774108622848",null);
        assertNotNull(tweet);
    }

    @Test
    public void deleteTweets() {
        when(dao.deleteById(any())).thenReturn(new Tweet());
        try {
            String[] ids = {"abc","123"};
            service.deleteTweets(ids);
            fail();
        }catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        String[] ids = {"1207770774108622848"};
        List<Tweet> list = service.deleteTweets(ids);
        for(Tweet tweet : list) {
            assertNotNull(tweet);
        }
    }
}