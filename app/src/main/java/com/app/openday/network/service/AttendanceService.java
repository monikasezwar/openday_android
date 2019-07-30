package com.app.openday.network.service;

import com.app.openday.network.request.AttendanceRequest;
import com.app.openday.network.request.AttendanceSubmitRequest;
import com.app.openday.network.response.AttendanceResponse;
import com.app.openday.network.response.AttendanceSubmitResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.app.openday.network.ServerAPIs.API_GET_SET_ATTENDANCE;

public interface AttendanceService {

    @GET(API_GET_SET_ATTENDANCE)
    Call<AttendanceResponse> getAttendance(@HeaderMap Map<String, String> headers, @Query("date") Long date,@Query("classroom") String classroom);

    @POST(API_GET_SET_ATTENDANCE)
    Call<AttendanceSubmitResponse> submitAttendance(@HeaderMap Map<String, String> headers,@Body AttendanceSubmitRequest attendanceSubmitRequest);
}
