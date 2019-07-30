package com.app.openday.password;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.openday.R;
import com.app.openday.mainpage.MainPageActivity;
import com.app.openday.network.RetrofitClientInstance;
import com.app.openday.network.request.LoginRequest;
import com.app.openday.network.response.PasswordResponse;
import com.app.openday.network.service.LoginService;
import com.app.openday.utils.Constants;
import com.app.openday.utils.JSONParserConstants;
import com.app.openday.utils.NoInternetDialog;
import com.app.openday.utils.PrefManager;
import com.app.openday.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PasswordView {

    private TextView letMeIn, teachersName;
    private TextView loginWithOTP;
    private EditText password;
    private PasswordFragment passwordFragment;
    private RelativeLayout progressBar;

    public PasswordView(PasswordFragment passwordFragment) {
        this.passwordFragment = passwordFragment;
    }

    public void onViewCreated() {
        setListener();
    }

    private void setListener() {
        teachersName = passwordFragment.getActivity().findViewById(R.id.teachers_name);
        letMeIn = passwordFragment.getActivity().findViewById(R.id.let_me_in);
        loginWithOTP = passwordFragment.getActivity().findViewById(R.id.login_with_otp);
        password = passwordFragment.getActivity().findViewById(R.id.editText_password);
        teachersName.setText("Hi," + passwordFragment.getArguments().getString(Constants.TEACHER_NAME));
        progressBar = passwordFragment.getActivity().findViewById(R.id.progressBar);

        letMeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isConnectedToNetwork(passwordFragment.requireContext())) {
                    if (Utils.isValidPassword(password.getText().toString().trim())) {
                        progressBar.setVisibility(View.VISIBLE);
                        LoginService service = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
                        LoginRequest loginRequest = new LoginRequest(passwordFragment.getArguments().getString(Constants.TEACHER_MOBILE_NUMBER), password.getText().toString().trim());
                        Call<PasswordResponse> call = service.getTeacherAuthentication(loginRequest);
                        call.enqueue(new Callback<PasswordResponse>() {
                            @Override
                            public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == JSONParserConstants.SUCCESS_CODE) {
                                        Utils.showToast(passwordFragment.requireActivity(), response.body().getMessage());
                                        saveLoginDetails(response.body());
                                    } else if (response.body().getStatus() == JSONParserConstants.ERROR_CODE_2) {
                                        Utils.showToast(passwordFragment.requireActivity(), "Incorrect Password");
                                    } else if (response.body().getStatus() == JSONParserConstants.ERROR_CODE) {
                                        Utils.showToast(passwordFragment.requireActivity(), "Invalid Password");
                                    } else {
                                        Utils.showToast(passwordFragment.requireActivity(), "Server Error");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PasswordResponse> call, Throwable t) {
                                Utils.showToast(passwordFragment.requireActivity(), "Something went wrong...Please try later!");
                            }
                        });
                    } else {
                        Utils.showToast(passwordFragment.requireContext(), "Please enter valid password");
                    }
                }else {
                    NoInternetDialog noInternetDialog = new NoInternetDialog();
                    noInternetDialog.show(passwordFragment.getFragmentManager(), "nointernet");
                }
            }
        });

        loginWithOTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


    }

    private void saveLoginDetails(PasswordResponse body) {
        new PrefManager(passwordFragment.getContext()).saveLoginDetails(body.getXAuthToken());
        startMainPageActivity();
        progressBar.setVisibility(View.GONE);
    }

    private void startMainPageActivity() {
        Intent intent = new Intent(passwordFragment.requireContext(), MainPageActivity.class);
        intent.putExtra(Constants.TEACHER_NAME,passwordFragment.getArguments().getString(Constants.TEACHER_NAME));
        passwordFragment.requireActivity().startActivity(intent);
    }

}
