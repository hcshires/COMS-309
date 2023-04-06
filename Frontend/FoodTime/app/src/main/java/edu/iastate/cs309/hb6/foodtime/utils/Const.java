package edu.iastate.cs309.hb6.foodtime.utils;

/**
 * Contains String and other data constants used in API requests, data transfer, and navigation
 */
public class Const {
    /** Server URL **/
    private static final String URL_SERVER = "http://coms-309-011.class.las.iastate.edu:8080";
    // URL_SERVER = "http://localhost:8080"; // for local testing

    /** API endpoints **/
    /* User Auth */
    public static final String URL_CREATE_USER = URL_SERVER + "/users/create";
    public static final String URL_LOGIN_USER = URL_SERVER + "/users/login";
    // TODO: public static final String URL_DELETE_USER = URL_SERVER + "/users/delete";
    // TODO: public static final String URL_RESET_PWD = URL_SERVER + "/users/reset-password";

    /* Meals */
    public static final String URL_MEALS_ADD = URL_SERVER + "/meals/add";
    public static final String URL_MEALS_REMOVE = URL_SERVER + "/meals/remove";
    public static final String URL_MEALS_UPDATE = URL_SERVER + "/meals/update";
    public static final String URL_MEALS_GET_BYDAY = URL_SERVER + "/meals/get/by-day";
    public static final String URL_MEALS_GET_ALL = URL_SERVER + "/meals/get/all";

    /* Pantry */
    // NOT USED // public static final String URL_PANTRY_GETPANTRY = URL_SERVER + "/pantry/getUserPantry";
    public static final String URL_PANTRY_GETPANTRYITEMS = URL_SERVER + "/pantry/getUserPantryString";
    public static final String URL_PANTRY_ADDITEM = URL_SERVER + "/pantry/addToPantry";
    public static final String URL_PANTRY_REMOVEITEM = URL_SERVER + "/pantry/removeFromPantry";
}