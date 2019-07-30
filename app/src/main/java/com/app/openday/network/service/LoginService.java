package com.app.openday.network.service;

import com.app.openday.login.User;
import com.app.openday.network.request.LoginRequest;
import com.app.openday.network.response.LoginResponse;
import com.app.openday.network.response.PasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.app.openday.network.ServerAPIs.API_AUTHENTICATE;
import static com.app.openday.network.ServerAPIs.API_LOGIN;

public interface LoginService {

    @GET(API_LOGIN)
    Call<LoginResponse> getTeacherDetail(@Query("username") String _id);

    @POST(API_AUTHENTICATE)
    Call<PasswordResponse> getTeacherAuthentication(@Body LoginRequest loginRequest);

}
