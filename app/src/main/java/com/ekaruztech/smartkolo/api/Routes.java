package com.ekaruztech.smartkolo.api;

/**
 * Created by Emmanuel on 4/23/2016.
 */
public class Routes {
    public static final String TAG = Routes.class
            .getSimpleName();

    public static final String HOST = "http://smartkolo.herokuapp.com";

    public static final int PORT = 3000;
    public static final String API_PATH = "api/";

    public static final String AUTH_LOGIN_ENDPOINT = API_PATH + "login";
    public static final String AUTH_REGISTER_ENDPOINT = API_PATH + "register";
    public static final String AUTH_VERIFY_ENDPOINT = API_PATH + "verify-code";
}
