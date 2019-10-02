package com.app.openday.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.openday.R;


public class AttendanceFragment extends Fragment {

    private AttendanceView attendanceView;

    public static AttendanceFragment newInstance(Bundle bundle) {
        AttendanceFragment fragment = new AttendanceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.attendance_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attendanceView = new AttendanceView(this);
        attendanceView.onViewCreated();
    }
}
