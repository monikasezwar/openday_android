package com.app.openday.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BroadcastRequest {

    public BroadcastRequest(String classroom, String subject, String text) {
        this.classroom = classroom;
        this.subject = subject;
        this.text = text;
    }
    @SerializedName("classroom")
    @Expose
    String classroom;
    @SerializedName("subject")
    @Expose
    String subject;
    @SerializedName("text")
    @Expose
    String text;

}
