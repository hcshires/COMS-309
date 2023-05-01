package edu.iastate.cs309.hb6.FoodTime.Websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.iastate.cs309.hb6.FoodTime.Login.User;
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

@Component
@ServerEndpoint("/websocket/send-meal/{UID}")
public class WebsocketServer {
    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    private static UserRepository userRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("UID") String UID) throws IOException {
        logger.info(String.format("Opened session for %s", UID));

        if (userRepository.existsById(UID)) {
            sessionUsernameMap.put(session, UID);
            usernameSessionMap.put(UID, session);
        }
        else {
            session.getBasicRemote().sendText("Invalid UID. Cannot connect.");
        }

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
            Recipe mealToSend = lookUpMeal(mealName, UID, session);
            if (mealToSend == null) throw new NullPointerException("Recipe not found in user's cookbook.");

            User destUserObj = userRepository.findByUsername(destUser);
            if (userRepository.existsByUsername(destUser)) {
                logger.info("Destination user exists");
            }
            else {
                logger.info("Destination user does not exist");
                session.getBasicRemote().sendText("Error: Specified recipient does not exist.");
            }
            String destUID = destUserObj.getUID().toString();
            logger.info("Destination UID is: " + destUID);

            if (usernameSessionMap.containsKey(destUID)) {
                //Prettify the Meal instead of it printing raw json
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(mapper.writeValueAsString(mealToSend));
                String prettyRecipeJson = gson.toJson(jsonElement);

                usernameSessionMap.get(destUID).getBasicRemote().sendText(userRepository.findByUID(UID).getUsername() + " sent you a meal:\n" + prettyRecipeJson);
                session.getBasicRemote().sendText("Successfully sent meal to user.");
            }
            else {
                logger.error("Cannot send meal to user that is not currently online.");
                session.getBasicRemote().sendText("Error: Cannot send meal to user that is not currently online.");
            }
        }
        catch (Exception e) {
            logger.info(String.format("Error looking up meal %s for UID %s%n", mealName, UID));
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        logger.info("Handling error");
        session.getBasicRemote().sendText("Error: " + throwable.getStackTrace());
    }

    private Recipe lookUpMeal (String mealName, String UID, Session session) throws IOException {
        logger.info("Sending meal " + mealName);
        Map<String, Recipe> userRecipes = userRepository.findByUID(UID).getUserRecipes();
        if (userRecipes.containsKey(mealName)) {
            Recipe recipe = userRecipes.get(mealName);
            logger.info(recipe.getIngredients().toString());
            return recipe;
        }
        else {
            logger.info("Meal " + mealName + " not found for user " + UID);
            session.getBasicRemote().sendText("Error: Recipe not found in your cookbook.");
            return null;
        }
    }

    @Autowired
    public void setUserRepository(UserRepository repo) {
        userRepository = repo;
    }

}
