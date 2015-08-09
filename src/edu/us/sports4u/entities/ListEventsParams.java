package edu.us.sports4u.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oksana on 09.08.2015.
 */
public class ListEventsParams {
    public String query;
    public String address;
    public Float radius;
    public List<String> sports;

    public ListEventsParams() {
        query = "";
        address = "";
        radius = 20.0f;
        sports = new ArrayList<String>();
    }

    public void addSport(String sport) {
        sports.add(sport);
    }

    public void addSports(List<String> sports) { sports.addAll(sports); }
}
