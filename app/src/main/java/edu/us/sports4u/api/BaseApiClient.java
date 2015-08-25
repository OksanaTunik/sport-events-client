package edu.us.sports4u.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BaseApiClient {
//    protected static String publicUrl = "http://sport-events.herokuapp.com";
    protected static String publicUrl = "http://192.168.1.99:3000";
    public final static String apiUrl = publicUrl + "/api";

    public static String getUrl(String postfix) {
        return apiUrl + postfix;
    }
}
