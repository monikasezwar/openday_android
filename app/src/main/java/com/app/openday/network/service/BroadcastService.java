package com.app.openday.network.service;

import com.app.openday.network.request.BroadcastRequest;
import com.app.openday.network.response.BroadcastResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import static com.app.openday.network.ServerAPIs.API_SEND_BROADCAST;

public interface BroadcastService {

    @POST(API_SEND_BROADCAST)
    Call<BroadcastResponse> sendBroadcast(@HeaderMap Map<String,String> headers, @Body BroadcastRequest request);
}
