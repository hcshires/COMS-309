package edu.iastate.cs309.hb6.foodtime.utils;

/**
 * Contains String and other data constants used in API requests, data transfer, and navigation
 */
public class Const {

    /**
     * Parent User Testing credentials, username[0], password[1], uid[2]
     */
    public static final String[] CRED_PARENT_USER = {"SystemTestUser@COMS309.com", "root", "bd66484d-ae81-492a-9457-9be427aa9998"};

    /* Meals */
    /**
     * Server URL
     **/
    private static final String URL_SERVER = "http://coms-309-011.class.las.iastate.edu:8080";
                             // URL_SERVER = "http://localhost:8080"; // for local testing
    /**
     * Add a new meal
     */
    public static final String URL_MEALS_ADD = URL_SERVER + "/meals/add";
    // NOT USED // public static final String URL_MEALS_UPDATE = URL_SERVER + "/meals/update";
    /**
     * Remove a meal by ID
     */
    public static final String URL_MEALS_REMOVE = URL_SERVER + "/meals/remove";
    // NOT USED // public static final String URL_MEALS_GET_ALL = URL_SERVER + "/meals/get/all";

    /* Pantry */
    // NOT USED // public static final String URL_PANTRY_GETPANTRY = URL_SERVER + "/pantry/getUserPantry";
    /**
     * Get meals by day
     */
    public static final String URL_MEALS_GET_BYDAY = URL_SERVER + "/meals/get/by-day";
    /**
     * Get the pantry items as a String
     */
    public static final String URL_PANTRY_GETPANTRYITEMS = URL_SERVER + "/pantry/getUserPantryString";
    /**
     * Add an item to the pantry
     */
    public static final String URL_PANTRY_ADDITEM = URL_SERVER + "/pantry/addToPantry";

    /* Recipes */
    /**
     * Remove an item from the pantry
     */
    public static final String URL_PANTRY_REMOVEITEM = URL_SERVER + "/pantry/removeFromPantry";
    /**
     * Get a recipe by ID
     */
    public static final String URL_RECIPES_GETRECIPE = URL_SERVER + "/recipes/get";
    /**
     * Get all recipes (labels only)
     */
    public static final String URL_RECIPES_GETLABELS = URL_SERVER + "/recipes/get/labels";
    // NOT USED // public static final String URL_RECIPES_REMOVERECIPE = URL_SERVER + "/recipes/remove";

    /* User Auth */
    /**
     * Add a new recipe
     */
    public static final String URL_RECIPES_ADDRECIPE = URL_SERVER + "/recipes/add";
    /**
     * Create a new user
     */
    public static final String URL_CREATE_USER = URL_SERVER + "/users/create";
    // NOT USED // public static final String URL_DELETE_USER = URL_SERVER + "/users/delete";
    // NOT USED // static final String URL_RESET_PWD = URL_SERVER + "/users/reset-password";

    /**
     * Login a user
     */
    public static final String URL_LOGIN_USER = URL_SERVER + "/users/login";


}