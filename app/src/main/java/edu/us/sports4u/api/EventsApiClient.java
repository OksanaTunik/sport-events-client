package edu.us.sports4u.api;

import android.text.TextUtils;
import edu.us.sports4u.entities.Event;
import edu.us.sports4u.http.HttpClientHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsApiClient extends BaseApiClient {
    public static List<Event> getEvents(String apiKey, String query, String address, Float radius, Iterable<String> sports) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("api_key", apiKey));
        params.add(new BasicNameValuePair("query", query));
        params.add(new BasicNameValuePair("address", address));
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

    public static List<Event> getCalendarEvents(String apiKey, String address, Float radius, Iterable<String> sports) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String groupBy = "week";

        params.add(new BasicNameValuePair("api_key", apiKey));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("radius", radius.toString()));
        params.add(new BasicNameValuePair("sports", TextUtils.join(",", sports)));
        params.add(new BasicNameValuePair("group_by", groupBy));

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

    public static String join(String eventId, String apiKey) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("api_key", apiKey));
        params.add(new BasicNameValuePair("event_id", eventId));

        String result = null;

        try {
            String url = getUrl("/events/join");
            JSONObject res = HttpClientHelper.get(url, params);

            if (res.getBoolean("success"))
                result = eventId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String leave(String eventId, String apiKey) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("api_key", apiKey));
        params.add(new BasicNameValuePair("event_id", eventId));

        String result = null;

        try {
            String url = getUrl("/events/leave");
            JSONObject res = HttpClientHelper.get(url, params);

            if (res.getBoolean("success"))
                result = eventId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Event convertEvent(JSONObject json) throws JSONException, ParseException {
        String id = json.get("id").toString();
        String title = json.get("title").toString();
        String description = json.get("description").toString();
        String address = json.get("address").toString();
        String sport = json.get("sport").toString();
        String group = json.get("group").toString();

        if (group.isEmpty() || group.equals("null"))
            group = null;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date plannedAt = format.parse(json.get("starts_at").toString());
        Date createdAt = format.parse(json.get("created_at").toString());

        Event event = new Event(id, title, description, address, sport, createdAt, plannedAt, group);

        return event;
    }

}
