package edu.us.sports4u.api;

import android.text.TextUtils;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.http.HttpClientHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsApiClient extends BaseApiClient {

    public static List<Event> getEvents(String apiKey, Float lat, Float lng, Float radius, Iterable <String> sports ) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("lat", lat.toString()));
        params.add(new BasicNameValuePair("lng", lng.toString()));
        params.add(new BasicNameValuePair("radius", radius.toString()));
        params.add(new BasicNameValuePair("sports", TextUtils.join(",", sports)));

        List<Event> list = new ArrayList<Event>();

        try {
            String url = getUrl("/events/");
            JSONObject res = HttpClientHelper.get(url, params);
            JSONArray array = res.getJSONArray("events");


            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                Event event = convertEvent(json);
                list.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean createEvent(String apiKey, Event event) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("title", event.getTitle()));
        params.add(new BasicNameValuePair("description", event.getDescription()));
        params.add(new BasicNameValuePair("address", event.getAddress()));
        params.add(new BasicNameValuePair("sport", event.getSport()));
        params.add(new BasicNameValuePair("api_key", apiKey));
        String url = getUrl("/events/create");
        JSONObject res = HttpClientHelper.post(url, params);

        boolean result = false;

        try {
            result = res.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

   /* public static Event updateEvent(String apiKey,Event event) throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("title", event.getTitle()));
        params.add(new BasicNameValuePair("description", event.getDescription()));
        params.add(new BasicNameValuePair("address", event.getAddress()));
        params.add(new BasicNameValuePair("sport", event.getSport()));
        params.add(new BasicNameValuePair("api_key", apiKey));
        String url = getUrl("/events/update");

        JSONObject res = HttpClientHelper.put(url, params);

        boolean result = false;

        try {
            result = res.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }*/

    public static void deleteEvent(String apiKey,Event event) throws Exception
    {

    }

    private static Event convertEvent(JSONObject json) throws JSONException {

        String id = json.get("id").toString();
        String title = json.get("title").toString();
        String description = json.get("description").toString();
        String address = json.get("address").toString();

        Event event = new Event(id, title, description, address);

        return event;
    }

}
