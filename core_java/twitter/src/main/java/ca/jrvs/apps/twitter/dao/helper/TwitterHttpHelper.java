package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;
import sun.net.www.http.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URI;

public class TwitterHttpHelper implements HttpHelper {

    /**
     * Dependencies are specified as private member variables
     */
    private OAuthConsumer consumer;
    private CloseableHttpClient httpClient;

    /**
     * Constructor
     * Setup dependencies using secrets
     * 
     * @param consumerKey
     * @param consumerSecret
     * @param accessToken
     * @param tokenSecret
     */
    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        //check if the input is empty
        if(consumerKey == null || consumerSecret == null || accessToken == null || tokenSecret == null)
            throw new RuntimeException("Unable to find tokens or keys");
        
        //setup OAuth
        consumer = new CommonsHttpOAuthConsumer(consumerKey,consumerSecret);
        consumer.setTokenWithSecret(accessToken,tokenSecret);
        
        //setup a httpclient
        httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public HttpResponse httpPost(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.POST, uri, null);
        } catch (OAuthException | IOException e){
            throw new RuntimeException("Failed to execute", e);
        }
    }

    @Override
    public HttpResponse httpGet(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.GET, uri, null);
        } catch (OAuthException | IOException e){
            throw new RuntimeException("Failed to execute", e);
        }
    }

    /**
     * helper function to execute Http request
     * Apllied KISS abd DRY design principle
     * @param method
     * @param uri
     * @param stringEntity
     * @return HttpResponse
     */
    private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity stringEntity)
            throws OAuthException, IOException{
        if (method == HttpMethod.GET) {
            HttpGet request = new HttpGet(uri);
            consumer.sign(request);
            return httpClient.execute(request);
        } else if (method == HttpMethod.POST) {
            HttpPost request = new HttpPost(uri);
            if (stringEntity != null) {
                request.setEntity(stringEntity);
            }
            consumer.sign(request);
            return httpClient.execute(request);
        } else {
            throw new IllegalArgumentException("Unknown HTTP method: " + method.name());
        }
        
    }
}
