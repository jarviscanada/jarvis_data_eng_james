package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TwitterCLIApp {

    private Controller controller;

    public TwitterCLIApp(Controller controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {

        //get key from env
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        //setup dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);

        //pass dependency
        CrdDao dao = new TwitterDao(httpHelper);
        Service twitterService = new TwitterService(dao);
        Controller controller = new TwitterController(twitterService);

        TwitterCLIApp app = new TwitterCLIApp(controller);

        app.run(args);

    }

    /**
     * run the command
     * @param args
     */
    public void run(String[] args) {

        //check if the argument is empty
        if(args.length == 0) {
            throw new IllegalArgumentException("Empty command");
        }

        //check which command
        switch (args[0].toLowerCase()) {
            case "post":
                printTweet(controller.postTweet(args));
                break;
            case "show":
                printTweet(controller.showTweet(args));
                break;
            case "delete":
                controller.deleteTweet(args).forEach(this::printTweet);
                break;
            default:
                throw new IllegalArgumentException("Wrong Command");
        }
    }

    public void printTweet (Tweet tweet){

        try {
            System.out.println(JsonUtil.toPrettyJson(tweet));
        } catch (JsonProcessingException e){
            throw new RuntimeException("Unable to convert tweet to Json", e);
        }
    }
}
