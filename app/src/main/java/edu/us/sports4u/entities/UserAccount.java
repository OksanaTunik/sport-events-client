package edu.us.sports4u.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oksana on 09.08.2015.
 */
public class UserAccount {
    private String apiKey;
    private String name;
    private String email;
    private String facebookId;
    private String address;
    private Bitmap photo;
    private List<String> sports;
    private List<String> eventIds;

    public UserAccount() {
        name = "";
        address = "";
        photo = null;
        sports = new ArrayList<>();
        eventIds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public List<String> getSportFavorites() {
        return sports;
    }

    public void setSportFavorites(List<String> sports) {
        this.sports = sports;
    }

    public void joinEvent(String eventId) {
        this.eventIds.add(eventId);
    }

    public void leaveEvent(String eventId) {
        this.eventIds.remove(eventId);
    }

    public List<String> getEventIds() {
        return this.eventIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
