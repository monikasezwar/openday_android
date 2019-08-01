package com.app.openday.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.openday.R;
import com.app.openday.network.RetrofitClientInstance;
import com.app.openday.network.response.LoginResponse;
import com.app.openday.network.service.LoginService;
import com.app.openday.password.PasswordActivity;
import com.app.openday.utils.Constants;
import com.app.openday.utils.JSONParserConstants;
import com.app.openday.utils.NoInternetDialog;
import com.app.openday.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginView {

    private TextView login;
    private EditText mobileNumberEditText;
    private RelativeLayout progressBar;

    private LoginFragment loginFragment;

    public LoginView(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public void onViewCreated(){
        setListener();
    }

    private void setListener(){
        mobileNumberEditText = loginFragment.getActivity().findViewById(R.id.editText_mobile_number);
        login = loginFragment.getActivity().findViewById(R.id.proceed_to_login_text);
        progressBar = loginFragment.getActivity().findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isConnectedToNetwork(loginFragment.requireContext())) {
                    if (Utils.isValidMobileNumber(mobileNumberEditText.getText().toString().trim())) {
                        //LoginRequest loginRequest = new LoginRequest(mobileNumber.toString());
                        progressBar.setVisibility(View.VISIBLE);
                        LoginService service = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
                        Call<LoginResponse> call = service.getTeacherDetail(mobileNumberEditText.getText().toString().trim());
                        call.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == JSONParserConstants.SUCCESS_CODE) {
                                        readResponse(response.body());
                                    } else if (response.body().getStatus() == JSONParserConstants.NOT_FOUND) {
                                        Utils.showToast(loginFragment.requireActivity(), "Username not found");
                                        progressBar.setVisibility(View.GONE);
                                    } else if (response.body().getStatus() == JSONParserConstants.ERROR_CODE) {
                                        Utils.showToast(loginFragment.requireActivity(), "Invalid Username");
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Utils.showToast(loginFragment.requireActivity(), "Server error");
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Utils.showToast(loginFragment.requireActivity(), "Something went wrong...Please try later!");
                            }
                        });
                    } else {
                        Utils.showToast(loginFragment.requireActivity(), "Enter a valid mobile number");
                    }
                }else {
                    NoInternetDialog noInternetDialog = new NoInternetDialog();
                    noInternetDialog.show(loginFragment.getFragmentManager(),"nointernet");
                }
            }
        });
    }

    private void readResponse(LoginResponse body) {
        String _id = body.getData().getId();
        String name = body.getData().getName();
        startPasswordActivity(_id,name);
        progressBar.setVisibility(View.GONE);
    }

    private void startPasswordActivity(String id, String name){
        Intent intent = new Intent(loginFragment.getActivity(), PasswordActivity.class);
        intent.putExtra(Constants.TEACHER_MOBILE_NUMBER,mobileNumberEditText.getText().toString().trim());
        intent.putExtra(Constants.TEACHER_NAME,name);
        intent.putExtra(Constants.TEACHER_ID,id);
        loginFragment.getActivity().startActivity(intent);
    }
}
