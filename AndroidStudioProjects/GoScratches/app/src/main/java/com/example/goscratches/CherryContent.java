package com.example.goscratches;

import android.location.Location;

public class CherryContent {
    public int value;
    public String name;
    public int type;       //0 means simple cherry 1 means super cherry
    public boolean isCatch;
    public Location location;
    CherryContent(String name,int value, int type,boolean isCatch, double latitude, double longitude){
        this. name = name;
        this.value = value;
        this.type = type;
        this.isCatch = isCatch;
        this.location = new Location(String.valueOf(type));
        location.setLatitude(latitude);
        location.setLongitude(longitude);
    }


}
