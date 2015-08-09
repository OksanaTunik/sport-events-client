package edu.us.sports4u.entities;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Created by Oksana on 09.08.2015.
 */
public class UserAccount {
    private String name;
    private String address;
    private Bitmap photo;

    public UserAccount() {
        name = "";
        address = "";
        photo = null;
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
}
