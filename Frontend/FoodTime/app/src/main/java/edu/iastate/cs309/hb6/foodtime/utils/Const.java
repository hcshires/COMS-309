package edu.iastate.cs309.hb6.foodtime.utils;

/**
 * Contains String and other data constants used in API requests, data transfer, and navigation
 */
public class Const {
    /**
     * Server URL
     **/

    /**
     * Parent User Testing credentials, username[0], password[1], uid[2]
     */
    public static final String[] CRED_PARENT_USER = {"wm@test.com", "test", "3ccd98d9-9951-4a17-98e6-b368fce2a4de"};

    /* Meals */
    /**
     * Server URL
     **/
    private static final String URL_SERVER = "http://coms-309-011.class.las.iastate.edu:8080";
    // URL_SERVER = "http://localhost:8080"; // for local testing

    /**
     * Websocket URL
     */
    private static final String URL_WEBSOCKET = "ws://coms-309-011.class.las.iastate.edu:8080/websocket";

    /* Meals */

    /**
     * Add a new meal
     */
    public static final String URL_MEALS_ADD = URL_SERVER + "/meals/add";

    /**
     * Remove a meal by ID
     */
    public static final String URL_MEALS_REMOVE = URL_SERVER + "/meals/remove";
    // NOT USED // public static final String URL_MEALS_UPDATE = URL_SERVER + "/meals/update";

    /**
     * Get meals by day
     */
    public static final String URL_MEALS_GET_BYDAY = URL_SERVER + "/meals/get/by-day";
    // NOT USED // public static final String URL_MEALS_GET_ALL = URL_SERVER + "/meals/get/all";

    /* Pantry */
    // NOT USED // public static final String URL_PANTRY_GETPANTRY = URL_SERVER + "/pantry/getUserPantry";

    /**
     * Get the pantry items as a String
     */
    public static final String URL_PANTRY_GETPANTRYITEMS = URL_SERVER + "/pantry/getUserPantryString";

    /**
     * Add an item to the pantry
     */
    public static final String URL_PANTRY_ADDITEM = URL_SERVER + "/pantry/addToPantry";

    /**
     * Remove an item from the pantry
     */
    public static final String URL_PANTRY_REMOVEITEM = URL_SERVER + "/pantry/removeFromPantry";

    /* Recipes */
    /**
     * Get a recipe by ID
     */
    public static final String URL_RECIPES_GETRECIPE = URL_SERVER + "/recipes/get";

    /**
     * Get all recipes (labels only)
     */
    public static final String URL_RECIPES_GETLABELS = URL_SERVER + "/recipes/get/labels";

    /**
     * Add a new recipe
     */
    public static final String URL_RECIPES_ADDRECIPE = URL_SERVER + "/recipes/add";
    // NOT USED // public static final String URL_RECIPES_REMOVERECIPE = URL_SERVER + "/recipes/remove";

    /** get recipe ingredients and quantities and types */
    public static final String URL_RECIPES_COMPARE = URL_SERVER + "/recipe/compareIngredients";

    /** Add new directions for a recipe" */
    public static final String URL_DIRECTIONS_SETDIRECTIONS = URL_SERVER + "/meals/setDirections";

    /** Get directions given recipe name */
    public static final String URL_DIRECTIONS_GETDIRECTIONS = URL_SERVER + "/meals/getDirections";

    /* User Auth */
    /**
     * Create a new user
     */
    public static final String URL_CREATE_USER = URL_SERVER + "/users/create";

    /**
     * Login a user
     */
    public static final String URL_LOGIN_USER = URL_SERVER + "/users/login";
    // NOT USED // public static final String URL_DELETE_USER = URL_SERVER + "/users/delete";
    // NOT USED // static final String URL_RESET_PWD = URL_SERVER + "/users/reset-password";

    /* Websockets */

    /**
     * Chat Websocket
     */
    public static final String URL_WEBSOCKET_CHAT = URL_WEBSOCKET + "/send-meal/";
}