package com.example.indigogestionstock.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("username")
    @Expose
    private String Username;

    @SerializedName("lastname")
    @Expose
    private String Lastname;

    @SerializedName("password")
    @Expose
    private String Password;

    @SerializedName("locationCode")
    @Expose
    private String LocationCode;

    @SerializedName("postUser")
    @Expose
    private String postUser;

    public User() {
    }

    public User(String id, String username, String lastname, String password, String locationCode, String postUser) {
        this.id = id;
        Username = username;
        Lastname = lastname;
        Password = password;
        LocationCode = locationCode;
        this.postUser = postUser;
    }

    public User(String id, String username, String lastname, String password, String locationCode) {
        this.id = id;
        Username = username;
        Lastname = lastname;
        Password = password;
        LocationCode = locationCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }
}
