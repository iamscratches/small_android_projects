package com.iamscratches.prichatscratches.model;

public class ChatList {
    private String usedID, username, description, date, urlProfile;

    public ChatList() {
    }

    public ChatList(String usedID, String username, String description, String date, String urlProfile) {
        this.usedID = usedID;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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