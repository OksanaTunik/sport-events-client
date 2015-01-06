package edu.us.sportEvents.api;

import android.text.TextUtils;
import edu.us.sportEvents.entities.Event;
import edu.us.sportEvents.http.HttpClientHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EventsApiClient extends BaseApiClient {

    public static List<Event> getEvents(String apiKey, Float lat, Float lng, Float radius, Iterable <String> sports ) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("lat", lat.toString()));
        params.add(new BasicNameValuePair("lng", lng.toString()));
        params.add(new BasicNameValuePair("radius", radius.toString()));
        params.add(new BasicNameValuePair("sports", TextUtils.join(",", sports)));

        String url = getUrl("/events");
        JSONObject res = HttpClientHelper.post(url, params);


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
    //  public void deleteEvent(String eventId){}
//public void updateEvent(String eventId){}


}
