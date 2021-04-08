package com.example.chattingwhatsapp.Models;

public class User {
    String name, uniqueId, phoneNumber, imageString;


    public User() {
    }

    public User(String name, String uniqueId, String phoneNumber, String imageString) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.phoneNumber = phoneNumber;
        this.imageString = imageString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
