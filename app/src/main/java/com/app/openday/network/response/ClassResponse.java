package com.app.openday.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private ClassroomData classroomData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ClassroomData getClassroomData() {
        return classroomData;
    }

    public void setClassroomData(ClassroomData classroomData) {
        this.classroomData = classroomData;
    }
}
