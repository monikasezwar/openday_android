package com.app.openday.network.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PasswordResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("x-auth-token")
    @Expose
    private String xAuthToken;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getXAuthToken() {
        return xAuthToken;
    }

    public void setXAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}