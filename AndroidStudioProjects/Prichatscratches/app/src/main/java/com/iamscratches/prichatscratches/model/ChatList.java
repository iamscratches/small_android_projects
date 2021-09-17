package com.iamscratches.prichatscratches.model;

public class ChatList {
    private String usedID, usename, description, date, urlProfile;

    public ChatList() {
    }

    public ChatList(String usedID, String usename, String description, String date, String urlProfile) {
        this.usedID = usedID;
        this.usename = usename;
        this.description = description;
        this.date = date;
        this.urlProfile = urlProfile;
    }

    public String getUsedID() {
        return usedID;
    }

    public void setUsedID(String usedID) {
        this.usedID = usedID;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }
}
