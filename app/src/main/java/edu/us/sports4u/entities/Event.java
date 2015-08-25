package edu.us.sports4u.entities;

import java.util.Date;

public class Event {
    protected String id;
    protected String title;
    protected String description;
    protected String address;
    protected String sport;
    protected String group;
    protected Float lat, lng;
    protected Date createdAt, startsAt;

    public Event(String id, String title, String description, String address, String sport, Date startsAt, Date createdAt, String group) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.sport = sport;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.group = group;
    }

    public Event(String title, String description, String address, String sport, Date startsAt) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.address = address;
        this.sport = sport;
        this.startsAt = startsAt;
        this.createdAt = new Date();
        this.group = null;
    }

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
        if (description == null || description.isEmpty() || description.equals("null")) {
            return "No description";
        } else {
            return description;
        }
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Date startsAt) {
        this.startsAt = startsAt;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}