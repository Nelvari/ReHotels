package com.example.rehotels.ApiClient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("notlp")
    private String notlp;
    @SerializedName("fotoProfile")
    private String fotoProfile;
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;

    public User() {
    }

    public User(int id, String username, String email, String notlp, String fotoProfile, String success, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.notlp = notlp;
        this.fotoProfile = fotoProfile;
        this.success = success;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotlp() {
        return notlp;
    }

    public void setNotlp(String notlp) {
        this.notlp = notlp;
    }

    public String getFotoProfile() {
        return fotoProfile;
    }

    public void setFotoProfile(String fotoProfile) {
        this.fotoProfile = fotoProfile;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
