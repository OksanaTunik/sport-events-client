package edu.us.sports4u.api;

import android.text.TextUtils;
import edu.us.sports4u.entities.UserAccount;
import edu.us.sports4u.http.HttpClientHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AccountApiClient extends BaseApiClient {
    public static UserAccount fetch(String apiKey) {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("api_key", apiKey));

        String url = getUrl("/users");

        JSONObject res = HttpClientHelper.get(url, data);
        UserAccount result = null;

        try {
            if (res.getBoolean("success")) {
                UserAccount acc = new UserAccount();

                acc.setName(res.getString("name"));
                acc.setAddress(res.getString("address"));
                acc.setEmail(res.getString("email"));
                acc.setFacebookId(res.getString("facebook_id"));

                List<String> sports = new ArrayList<>();
                String[] pieces = res.getString("sports").split(",");

                Collections.addAll(sports, pieces);

                acc.setSportFavorites(sports);

                result = acc;
            } else {
                result = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean update(UserAccount newAccount) {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("api_key", newAccount.getApiKey()));
        data.add(new BasicNameValuePair("name", newAccount.getName()));
        data.add(new BasicNameValuePair("email", newAccount.getEmail()));
        data.add(new BasicNameValuePair("address", newAccount.getAddress()));
        data.add(new BasicNameValuePair("sports", TextUtils.join(",", newAccount.getSportFavorites())));

        String url = getUrl("/users/update");

        JSONObject res = HttpClientHelper.post(url, data);
        boolean result = false;

        try {
            result = res.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

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

    public static String facebookSignIn(String facebookId, String email, String displayName) {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("name", displayName));
        data.add(new BasicNameValuePair("email", email));
        data.add(new BasicNameValuePair("facebook_id", facebookId));

        String url = getUrl("/users/facebook_sign_in");

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
}
