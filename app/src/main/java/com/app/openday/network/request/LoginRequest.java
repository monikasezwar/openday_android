package com.app.openday.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
}
