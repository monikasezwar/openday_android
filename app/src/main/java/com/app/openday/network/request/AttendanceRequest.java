package com.app.openday.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceRequest {

    public AttendanceRequest(Long dateTimeStamp, String classroom) {
        this.dateTimeStamp = dateTimeStamp;
        this.classroom = classroom;
    }

    @SerializedName("date")
    @Expose
    private Long dateTimeStamp;
    @SerializedName("classroom")
    @Expose
    private String classroom;
}
