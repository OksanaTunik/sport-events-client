package edu.us.sportEvents.api;

public class BaseApiClient {
//    protected static String publicUrl = "http://sport-events.herokuapp.com";
    protected static String publicUrl = "http://192.168.2.237:3000";
    public final static String apiUrl = publicUrl + "/api";

    public static String getUrl(String postfix) {
        return apiUrl + postfix;
    }
}
