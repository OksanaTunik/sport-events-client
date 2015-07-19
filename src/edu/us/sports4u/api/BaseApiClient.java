package edu.us.sports4u.api;

public class BaseApiClient {
//    protected static String publicUrl = "http://sport-events.herokuapp.com";
    protected static String publicUrl = "http://192.168.1.99:3000";
    public final static String apiUrl = publicUrl + "/api";

    public static String getUrl(String postfix) {
        return apiUrl + postfix;
    }
}
