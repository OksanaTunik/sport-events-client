package edu.us.sports4u;

import edu.us.sports4u.entities.BaseEvent;

/**
 * Created by shybovycha on 23.11.14.
 */
public class FoundAlert extends BaseEvent {
    public FoundAlert(int id, String title, String description, String author, double lat, double lon) {
        super(id, title, description, author, lat, lon);
    }
}