package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
    @Mock
    Service service;

    @InjectMocks
    TwitterController controller;

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
        when(service.postTweet(any())).thenReturn(new Tweet());
        try {
            //too many arguments
            String[] arg = {"post","Mockito test","10:10","test"};
            controller.postTweet(arg);
            fail();
        }catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        String[] arg = {"post","Mockito test","10:10"};
        Tweet tweet = controller.postTweet(arg);

        assertNotNull(tweet);
    }

    @Test
    public void showTweet() {
        //test failed request
        when(service.showTweet(any(),any())).thenReturn(new Tweet());
        try {
            //too many arg
            String[] arg = {"show","Mockito test","123"};
            controller.showTweet(arg);
            fail();
        }catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        String[] arg = {"show","1207770774108622848"};
        Tweet tweet = controller.showTweet(arg);

        assertNotNull(tweet);
    }

    @Test
    public void deleteTweets() {
        //test failed request
        when(service.deleteTweets(any())).thenReturn(new ArrayList<Tweet>());
        try {
            //too many arg
            String[] arg = {"delete","Mockito test","123"};
            controller.deleteTweet(arg);
            fail();
        }catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        String[] arg = {"delete","1207770774108622848"};
        List<Tweet> list = controller.deleteTweet(arg);
        for(Tweet tweet : list) {
            assertNotNull(tweet);
        }
    }
}