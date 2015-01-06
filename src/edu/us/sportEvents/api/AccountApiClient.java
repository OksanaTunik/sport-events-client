package edu.us.sportEvents.api;

import java.util.ArrayList;
import java.util.List;

import edu.us.sportEvents.http.HttpClientHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


public class AccountApiClient extends BaseApiClient {
    public static String signUp(String email, String password, String displayName) {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("name", displayName));
        data.add(new BasicNameValuePair("email", email));
        data.add(new BasicNameValuePair("password", password));
        data.add(new BasicNameValuePair("password_confirmation", password));

        String url = getUrl("/users/sign_up");

        JSONObject res = HttpClientHelper.post(url, data);
        String result = null;

        try {
            if (res.getBoolean("success")) {
                result = res.getString("api_key");
            } else {
                result = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String logIn(String email, String password) {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("email", email));
        data.add(new BasicNameValuePair("password", password));

        String url = getUrl("/users/sign_in");

        JSONObject res = HttpClientHelper.post(url, data);
        String result = null;

        try {
            if (res.getBoolean("success")) {
                result = res.getString("api_key");
            } else {
                result = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean checkLogIn(String apiKey) {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("api_key", apiKey));

        JSONObject res = HttpClientHelper.post(getUrl("/users/restore_session"), data);
        boolean result = false;

        try {
            result = res.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

  /*  public static boolean addEvent(String apiKey, String title, String description, String address, String sport) {
        String url = getUrl("/events/create");
        boolean result = false;
        String boundary = "-------------" + System.currentTimeMillis();

        try {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

            entityBuilder.setBoundary(boundary);
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addTextBody("api_key", apiKey);
            entityBuilder.addTextBody("title", title);
            entityBuilder.addTextBody("description", description);
            entityBuilder.addTextBody("address", title);
            entityBuilder.addTextBody("sport", description);

            HttpEntity entity = entityBuilder.build();

            JSONObject res = HttpClientHelper.post(url, entity, boundary);

            result = res.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }*/

}
