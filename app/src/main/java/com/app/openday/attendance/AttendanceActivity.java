package com.app.openday.attendance;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.openday.R;
import com.app.openday.utils.Constants;

public class AttendanceActivity extends AppCompatActivity {

    private AttendanceFragment attendanceFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attendanceFragment = new AttendanceFragment();
        setContentView(R.layout.attendance_activity);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.STUDENT_DATA,getIntent().getSerializableExtra(Constants.STUDENT_DATA));
        bundle.putString(Constants.CLASSROOM_ID,getIntent().getStringExtra(Constants.CLASSROOM_ID));
        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,attendanceFragment.newInstance(bundle)).commit();
        }
    }
}
