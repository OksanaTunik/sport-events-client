package edu.us.sports4u.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shybovycha on 27/08/15.
 */
public class EventsStorage {
    protected static List<Event> events;

    public static void setEvents(List<Event> newEvents) {
        if (events == null)
            events = new ArrayList<Event>();

        events.clear();
        events.addAll(newEvents);
    }

    public static List<Event> getEvents() {
        return events;
    }

    public static Event getEventByIndex(int index) {
        if (index < 0 || index >= events.size())
            return null;

        return events.get(index);
    }

    public static Event getEventById(String id) {
        for (Event e : events) {
            if (e.getId().equals(id))
                return e;
        }

        return null;
    }
}
