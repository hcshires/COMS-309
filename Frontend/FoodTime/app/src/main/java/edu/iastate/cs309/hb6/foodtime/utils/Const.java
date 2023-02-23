package edu.iastate.cs309.hb6.foodtime.utils;

/**
 * Contains String and other data constants used in API requests, data transfer, and navigation
 */
public class Const {
    /** Server URL **/
    private static final String URL_SERVER = "http://coms-309-011.class.las.iastate.edu:8080";

    /** API endpoints **/
    /* User Auth */
    public static final String URL_CREATE_USER = URL_SERVER + "/users/create";
    public static final String URL_LOGIN_USER = URL_SERVER + "/users/login";
    public static final String URL_DELETE_USER = URL_SERVER + "/users/delete";
    public static final String URL_RESET_PWD = URL_SERVER + "/users/reset-password";

    /* TODO: Get Data */

    /* TODO: Set Data */
}