package com.intern.aditya.telenotes;

/**
 * Created by Aditya on 3/23/2016.
 */
public class Restaurant {

    String name;
    String address;
    String type;
    String rating;

    public Restaurant(String name, String address, String type, String rating) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getRating() {
        return rating;
    }

}
