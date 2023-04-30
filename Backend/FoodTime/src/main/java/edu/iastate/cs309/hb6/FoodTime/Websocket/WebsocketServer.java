package edu.iastate.cs309.hb6.FoodTime.Websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.iastate.cs309.hb6.FoodTime.Login.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import edu.iastate.cs309.hb6.FoodTime.Meal.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@ServerEndpoint("/websocket/send-meal/{UID}")
public class WebsocketServer {
    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    @Autowired
    private UserRepository userRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("UID") String UID) throws IOException {
        logger.info(String.format("Opened session for %s", UID));

        sessionUsernameMap.put(session, UID);
        usernameSessionMap.put(UID, session);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String UID = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(UID);

        logger.info(String.format("Closed session for %s", UID));
    }

    @OnMessage
    public void onMessage(Session session, String userAndMealNameString) throws IOException {
        // Handle new messages
        String[] delimitedString = userAndMealNameString.split(",");
        String destUser = delimitedString[0];
        String mealName = delimitedString[1];

        ObjectMapper mapper = new ObjectMapper();

        logger.info("Sending meal " + mealName + " to user " + destUser);

        String UID = sessionUsernameMap.get(session);
        logger.info("Got UID: " + UID + " from sessionUsernameMap");

        try {
            //UID is for the user that is sending the recipe
            //destUID is the UID of the user that it is being sent to
            Recipe mealToSend = lookUpMeal(mealName, UID);
            logger.info("Heck?");
            String destUID = userRepository.findByUsername(destUser).getUID().toString();
            usernameSessionMap.get(destUID).getBasicRemote().sendText(mapper.writeValueAsString(mealToSend));
        }
        catch (Exception e) {
            logger.info(String.format("Error looking up meal %s for UID %s%n", mealName, UID));
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Handling error");
    }

    private Recipe lookUpMeal (String mealName, String UID) {
        logger.info("Sending meal " + mealName);
        Map<String, Recipe> userRecipes = userRepository.findByUID(UID).getUserRecipes();
        logger.info("the fuck");
        if (userRecipes.containsKey(mealName)) {
            Recipe recipe = userRecipes.get(mealName);
            System.out.println("Took if");
            return recipe;
        }
        else {
            logger.info("Meal " + mealName + " not found for user " + UID);
            System.out.println("Took else");
            return null;
        }
    }
}
