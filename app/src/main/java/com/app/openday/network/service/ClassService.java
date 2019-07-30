package com.app.openday.network.service;

import com.app.openday.network.response.ClassResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;

import static com.app.openday.network.ServerAPIs.API_GET_CLASSES;

public interface ClassService {

    @GET(API_GET_CLASSES)
    Call<ClassResponse> getClasses( @HeaderMap Map<String, String> headers);
}
