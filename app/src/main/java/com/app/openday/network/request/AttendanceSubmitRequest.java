package com.app.openday.network.request;

import com.app.openday.network.response.Record;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendanceSubmitRequest {
    @SerializedName("date")
    @Expose
    private Long date;
    @SerializedName("classroom")
    @Expose
    private String classroom;
    @SerializedName("attendance")
    @Expose
    private List<Record> attendance = null;

    public AttendanceSubmitRequest(Long timeStamp, String classroomId, List<Record> studentListWithAttendance) {
        this.date = timeStamp;
        this.classroom = classroomId;
        this.attendance = studentListWithAttendance;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public List<Record> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Record> attendance) {
        this.attendance = attendance;
    }

    private Record attendanceRecord;


}
