package com.example.ukradlimirower.com.example.api;

import android.graphics.Bitmap;
import com.example.ukradlimirower.entities.Event;
import com.example.ukradlimirower.http.HttpClientHelper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EventsApiClient extends BaseApiClient {

    //    public List<Event> getEvents(){}
    public boolean createEvent(Event event) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("Title", event.getTitle()));
        params.add(new BasicNameValuePair("Description", event.getDescription()));
        params.add(new BasicNameValuePair("Address", event.getAddress()));
        params.add(new BasicNameValuePair("Sport", event.getSport()));
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
