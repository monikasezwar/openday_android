package com.app.openday.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    public Record(String student, Boolean absent) {
        this.student = student;
        this.absent = absent;
    }

    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("absent")
    @Expose
    private Boolean absent;

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Boolean getAbsent() {
        return absent;
    }

    public void setAbsent(Boolean absent) {
        this.absent = absent;
    }

}