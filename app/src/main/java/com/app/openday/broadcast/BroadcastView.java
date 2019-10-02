package com.app.openday.broadcast;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.openday.R;
import com.app.openday.network.RetrofitClientInstance;
import com.app.openday.network.request.BroadcastRequest;
import com.app.openday.network.response.BroadcastResponse;
import com.app.openday.network.service.BroadcastService;
import com.app.openday.utils.Constants;
import com.app.openday.utils.JSONParserConstants;
import com.app.openday.utils.PrefManager;
import com.app.openday.utils.ServerError;
import com.app.openday.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BroadcastView {

    private BroadcastFragment broadcastFragment;
    private TextView topHeading, submitButton;
    private ImageView backButton;
    private EditText mailBody, subject;
    private String classroomId;

    public BroadcastView(BroadcastFragment broadcastFragment) {
        this.broadcastFragment = broadcastFragment;
    }

    public void onViewCreated(){
        classroomId = broadcastFragment.getArguments().getString(Constants.CLASSROOM_ID);
        setListeners();
    }

    private void setListeners() {
        backButton = broadcastFragment.requireActivity().findViewById(R.id.back_button);
        topHeading = broadcastFragment.requireActivity().findViewById(R.id.top_heading);
        submitButton = broadcastFragment.requireActivity().findViewById(R.id.submit_text);
        mailBody = broadcastFragment.requireActivity().findViewById(R.id.mail_body);
        subject = broadcastFragment.requireActivity().findViewById(R.id.subject);

        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                broadcastFragment.requireActivity().onBackPressed();
            }
        });
        topHeading.setText("Broadcast");

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String authToken = new PrefManager(broadcastFragment.requireContext()).getLoginDetails();
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("Authorization",authToken);
                BroadcastService broadcastService = RetrofitClientInstance.getRetrofitInstance().create(BroadcastService.class);
                BroadcastRequest broadcastRequest = new BroadcastRequest(classroomId,subject.getText().toString(),mailBody.getText().toString());
                Call<BroadcastResponse> call = broadcastService.sendBroadcast(hashMap,broadcastRequest);
                call.enqueue(new Callback<BroadcastResponse>() {
                    @Override
                    public void onResponse(Call<BroadcastResponse> call, Response<BroadcastResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getStatus() == JSONParserConstants.SUCCESS_CODE){
                                Utils.showToast(broadcastFragment.requireContext(),"Mail has been sent successfully");
                            }else{
                                ServerError.handleServerError(response.body().getStatus(), broadcastFragment.requireContext());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BroadcastResponse> call, Throwable t) {
                        Utils.showToast(broadcastFragment.requireContext(),"Something went wrong...Please try later!");
                    }
                });

            }
        });
    }
}
