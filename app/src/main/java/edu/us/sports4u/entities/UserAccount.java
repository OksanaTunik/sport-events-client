package edu.us.sports4u.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oksana on 09.08.2015.
 */
public class UserAccount {
    private String name;
    private String address;
    private Bitmap photo;
    private List<String> sports;

    public UserAccount() {
        name = "";
        address = "";
        photo = null;
        sports = new ArrayList<>();
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
}
