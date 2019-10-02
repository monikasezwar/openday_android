package com.app.openday.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentResponse {

    @SerializedName("rollNo")
    @Expose
    private String rollNo;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}