package edu.us.sports4u.entities;

public class Event {
    protected String id;
    protected String title;
    protected String description;
    protected String address;
    protected String sport;
    protected Float lat, lng;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Event(String id, String title, String description, String address, String sport) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.sport = sport;
    }

    public Event(String title, String description, String address, String sport) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.address = address;
        this.sport = sport;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }
}