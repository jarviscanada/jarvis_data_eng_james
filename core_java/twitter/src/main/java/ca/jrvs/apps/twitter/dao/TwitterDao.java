package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.URIException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class TwitterDao implements CrdDao<Tweet, String> {

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {this.httpHelper = httpHelper; }

    /**
     * create an tweet
     * @param entity entity that to be created
     * @return
     */
    @Override
    public Tweet create(Tweet entity) {

        //construct URI
        URI uri;
        try {
            uri = getPostUri(entity);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invaild tweet input", e);
        }

        //execute http response
        HttpResponse response = httpHelper.httpPost(uri);

        //Validate response and deserialize response to Tweet object
        return parseResponseBody(response,HTTP_OK);
    }

    @Override
    public Tweet findById(String s) {
        //construct URI
        URI uri;
        try {
            uri = getShowUri(s);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Fail to construct URI", e);
        }

        //execute http response
        HttpResponse response = httpHelper.httpGet(uri);

        //Validate response and deserialize response to Tweet object
        return parseResponseBody(response,HTTP_OK);
    }

    @Override
    public Tweet deleteById(String s) {
        //construct URI
        URI uri;
        try {
            uri = getDeleteUri(s);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Fail to construct URI", e);
        }

        //execute http response
        HttpResponse response = httpHelper.httpPost(uri);

        //Validate response and deserialize response to Tweet object
        return parseResponseBody(response,HTTP_OK);
    }

    /**
     * create a Post URI
     * @param tweet
     * @return
     */
    private URI getPostUri(Tweet tweet) throws URISyntaxException, UnsupportedEncodingException {

        //get info
        String text = tweet.getText();
        double lon = tweet.getCooridinates().getCoordinates().get(0);
        double lat = tweet.getCooridinates().getCoordinates().get(1);

        //construct the URI
        StringBuilder uri = new StringBuilder();
        uri.append(API_BASE_URI);
        uri.append(POST_PATH);
        uri.append(QUERY_SYM);
        //add text
        uri.append("status");
        uri.append(EQUAL);
        uri.append(URLEncoder.encode(text,"UTF-8"));
        //add coordinates
        uri.append(AMPERSAND);
        uri.append("long");
        uri.append(EQUAL);
        uri.append(lon);
        uri.append(AMPERSAND);
        uri.append("lat");
        uri.append(EQUAL);
        uri.append(lat);

        return new URI(uri.toString());
    }

    /**
     * create show URI
     * @param s
     * @return
     * @throws URISyntaxException
     */
    private URI getShowUri(String s) throws URISyntaxException {
        //construct the URI
        StringBuilder uri = new StringBuilder();
        uri.append(API_BASE_URI);
        uri.append(SHOW_PATH);
        uri.append(QUERY_SYM);
        //add text
        uri.append("id");
        uri.append(EQUAL);
        uri.append(s);

        return new URI(uri.toString());
    }

    /**
     * create delete URI
     * @param s
     * @return
     * @throws URISyntaxException
     */
    private URI getDeleteUri(String s) throws URISyntaxException {
        //construct the URI
        StringBuilder uri = new StringBuilder();
        uri.append(API_BASE_URI);
        uri.append(DELETE_PATH);
        uri.append("/");
        uri.append(s);
        uri.append(".json");

        return new URI(uri.toString());
    }
    /**
     * Check response status code Convert Response Entity to Tweet
     * @param response
     * @param expectedStatusCode
     * @return
     */
    private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
        Tweet tweet;

        //check response status
        int status = response.getStatusLine().getStatusCode();
        if(status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status:" + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        //Convert Response Entity to str
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("FAiled to convert entity to String", e);
        }

        //deserialize JSON string ti Tweet object
        try {
            tweet = JsonUtil.toObjectFromJson(jsonStr,Tweet.class);
        } catch (IOException e){
            throw new RuntimeException("Unable to convert JSON str to Object", e);
        }

        return tweet;
    }
}
