package com.app.openday.attendance;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.openday.R;
import com.app.openday.broadcast.BroadcastDialog;
import com.app.openday.network.RetrofitClientInstance;
import com.app.openday.network.request.AttendanceSubmitRequest;
import com.app.openday.network.response.AttendanceResponse;
import com.app.openday.network.response.AttendanceSubmitResponse;
import com.app.openday.network.response.Record;
import com.app.openday.network.response.Student;
import com.app.openday.network.service.AttendanceService;
import com.app.openday.utils.Constants;
import com.app.openday.utils.JSONParserConstants;
import com.app.openday.utils.NoInternetDialog;
import com.app.openday.utils.OnSubmitClickListener;
import com.app.openday.utils.PrefManager;
import com.app.openday.utils.ServerError;
import com.app.openday.utils.Utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceView implements OnSubmitClickListener, BroadcastDialog.OnConfirmAndSendClickListener {

    private AttendanceFragment attendanceFragment;
    private RecyclerView absenteeRecyclerView;
    private TextView currentDate, topHeading;
    private ImageView nextIcon, backButton;
    private int mYear, mMonth, mDay;
    private String authToken, classroomId;
    private List<Record> attendanceRecord = null;


    public AttendanceView(AttendanceFragment attendanceFragment) {
        this.attendanceFragment = attendanceFragment;
    }

    public void onViewCreated() {
        classroomId = attendanceFragment.getArguments().get(Constants.CLASSROOM_ID).toString();
        setListeners();
    }

    private void setListeners() {
        topHeading = attendanceFragment.requireActivity().findViewById(R.id.top_heading);
        currentDate = attendanceFragment.requireActivity().findViewById(R.id.current_date);
        nextIcon = attendanceFragment.requireActivity().findViewById(R.id.next_icon);
        backButton = attendanceFragment.requireActivity().findViewById(R.id.back_button);
        topHeading.setText("Attendance");
        currentDate.setText(Utils.getTodaysDate());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceFragment.requireActivity().onBackPressed();
            }
        });
        nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDatePickDialog();
            }
        });
        // for showing todays attendance(taken or not taken) on opening attendance fragment
        getAttendanceOfSelectedDate(System.currentTimeMillis());
    }

    private void callDatePickDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(attendanceFragment.requireContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        currentDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Long timeInMillis = Utils.convertDateIntoTimeMillis(currentDate.getText().toString());
                        getAttendanceOfSelectedDate(timeInMillis);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void getAttendanceOfSelectedDate(Long timeInMillis) {
        if(Utils.isConnectedToNetwork(attendanceFragment.requireContext())) {
            authToken = new PrefManager(attendanceFragment.getContext()).getLoginDetails();
            Map<String, String> hashMap = new HashMap();
            hashMap.put("Authorization", authToken);
            //AttendanceRequest attendanceRequest = new AttendanceRequest(timeStamp,attendanceFragment.getArguments().getString(Constants.CLASSROOM_ID));
            AttendanceService service = RetrofitClientInstance.getRetrofitInstance().create(AttendanceService.class);
            Call<AttendanceResponse> call = service.getAttendance(hashMap, timeInMillis, classroomId);
            call.enqueue(new Callback<AttendanceResponse>() {
                @Override
                public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == JSONParserConstants.SUCCESS_CODE) {
                            readResponse(response.body());
                        } else {
                            ServerError.handleServerError(response.body().getStatus(), attendanceFragment.requireContext());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                    Utils.showToast(attendanceFragment.requireActivity(), "Something went wrong...Please try later!");
                }

            });
        }else {
            NoInternetDialog noInternetDialog = new NoInternetDialog();
            noInternetDialog.show(attendanceFragment.getFragmentManager(),"nointernet");
        }

    }

    private void readResponse(AttendanceResponse body) {
        attendanceRecord = body.getData().getRecords();
        setStudentList(attendanceRecord);
    }

    private void setStudentList(List<Record> attendanceRecord) {
        absenteeRecyclerView = attendanceFragment.requireActivity().findViewById(R.id.absentee_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(attendanceFragment.requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        absenteeRecyclerView.setLayoutManager(linearLayoutManager);
        List<Student> studentList = (List<Student>) attendanceFragment.getArguments().getSerializable(Constants.STUDENT_DATA);
        AbsentStudentsNameAdapter absentStudentsNameAdapter = new AbsentStudentsNameAdapter(attendanceFragment.requireActivity(), studentList, attendanceRecord, this, attendanceFragment);
        absenteeRecyclerView.setAdapter(absentStudentsNameAdapter);
    }

    @Override
    public void onSubmitAttendance(List<Record> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
        int absentStudentCount = getCountOfAbsentStudents(attendanceRecord);
        DialogFragment dialogFragment = new BroadcastDialog(absentStudentCount, this);
        dialogFragment.show(attendanceFragment.getFragmentManager(), "broadcast");
    }

    private int getCountOfAbsentStudents(List<Record> attendanceRecord) {
        int absentStudentCount = 0;
        for (int i = 0; i < attendanceRecord.size(); i++) {
            if (attendanceRecord.get(i).getAbsent()) {
                absentStudentCount++;
            }
        }
        return absentStudentCount;
    }

    @Override
    public void OnConfirmAndSendClicked() {
        if (Utils.isConnectedToNetwork(attendanceFragment.requireContext())) {
            Long timeStamp = System.currentTimeMillis();
            String authToken = new PrefManager(attendanceFragment.requireContext()).getLoginDetails();
            Map<String, String> hashMap = new HashMap();
            hashMap.put("Authorization", authToken);
            final AttendanceService attendanceService = RetrofitClientInstance.getRetrofitInstance().create(AttendanceService.class);
            AttendanceSubmitRequest attendanceSubmitRequest = new AttendanceSubmitRequest(timeStamp, classroomId, attendanceRecord);
            Call<AttendanceSubmitResponse> call = attendanceService.submitAttendance(hashMap, attendanceSubmitRequest);
            call.enqueue(new Callback<AttendanceSubmitResponse>() {
                @Override
                public void onResponse(Call<AttendanceSubmitResponse> call, Response<AttendanceSubmitResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == JSONParserConstants.SUCCESS_CODE) {
                            Utils.showToast(attendanceFragment.getContext(), "Attendance submitted successfully");
                        } else {
                            ServerError.handleServerError(response.body().getStatus(), attendanceFragment.requireContext());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AttendanceSubmitResponse> call, Throwable t) {
                    Utils.showToast(attendanceFragment.requireContext(), "Some problem while submitting attendance");
                }
            });
        } else {
            NoInternetDialog noInternetDialog = new NoInternetDialog();
            noInternetDialog.show(attendanceFragment.getFragmentManager(),"nointernet");
        }
    }
}
