package com.example.ukradlimirower.entities;

public class Event {

    String id;

    String title;

    String description;

    String address;

    String sport;

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

}