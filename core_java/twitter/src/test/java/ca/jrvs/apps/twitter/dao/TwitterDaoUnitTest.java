package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

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
    public void create() throws Exception {
        //test failed request
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.create(TweetUtil.buildTweet("Mockito test", -1d, 1d));
            fail();
        }catch (RuntimeException e) {
            assertTrue(true);
        }

        //test happy path
        //make a spyDao to fake parseResponseBody return value

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.create(TweetUtil.buildTweet("Mockito test", -1d, 1d));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void findById() throws Exception {
        //test failed request
        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.findById("1207770774108622848");
            fail();
        }catch (RuntimeException e) {
            assertTrue(true);
        }

        //test happy path
        //make a spyDao to fake parseResponseBody return value

        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.findById("1207770774108622848");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteById() throws Exception{
        //test failed request
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.deleteById("1207770774108622848");
            fail();
        }catch (RuntimeException e) {
            assertTrue(true);
        }

        //test happy path
        //make a spyDao to fake parseResponseBody return value

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.deleteById("1207770774108622848");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}