package com.app.openday.password;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.openday.R;
import com.app.openday.utils.Constants;


public class PasswordActivity extends AppCompatActivity {

    private PasswordFragment passwordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passwordFragment = new PasswordFragment();
        if(savedInstanceState == null){
            String mobileNumber = getIntent().getStringExtra(Constants.TEACHER_MOBILE_NUMBER);
            String teacherName = getIntent().getStringExtra(Constants.TEACHER_NAME);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TEACHER_MOBILE_NUMBER,mobileNumber);
            bundle.putString(Constants.TEACHER_NAME,teacherName);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.container,passwordFragment.newInstance(bundle)).commit();
        }
    }

}
