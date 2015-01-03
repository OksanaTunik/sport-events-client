package com.example.ukradlimirower.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shybovycha on 23.11.14.
 */
public class BaseEvent {
    protected String title;
    protected String description;
    protected String author;
    protected double lat;
    protected double lon;
    protected int id;
    public List<String> images;
    public List<Bitmap> bitmaps;

    public BaseEvent(int id, String title, String description, String author, double lat, double lon) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.lat = lat;
        this.lon = lon;
        this.images = new ArrayList<String>();
        this.bitmaps = new ArrayList<Bitmap>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
