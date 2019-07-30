package com.app.openday.mainpage;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.openday.R;
import com.app.openday.attendance.AttendanceActivity;
import com.app.openday.broadcast.BroadcastActivity;
import com.app.openday.network.RetrofitClientInstance;
import com.app.openday.network.response.ClassResponse;
import com.app.openday.network.response.Classroom;
import com.app.openday.network.response.Student;
import com.app.openday.network.service.ClassService;
import com.app.openday.utils.Constants;
import com.app.openday.utils.JSONParserConstants;
import com.app.openday.utils.PrefManager;
import com.app.openday.utils.ServerError;
import com.app.openday.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPageView implements AdapterView.OnItemSelectedListener {

    private MainPageFragment mMainPageFragment;
    private RecyclerView mNameRecyclerView;
    private ImageView attendanceIcon,timeTableIcon,addEventsIcon,broadCastIcon;
    private Spinner mSpinner;
    private EditText mSearchView;
    private TextView mTeacherName;
    private ImageView mHamburgerIcon;
    private StudentsNameAdapter mStudentsNameAdapter;
    private List<Student> studentList =  new ArrayList<>();
    private String classRoomId;
    private DrawerLayout mDrawerLayout;

    public void onViewCreated(MainPageFragment mainPageFragment){
        this.mMainPageFragment = mainPageFragment;
        setListeners();
        setClassStudentData();
    }

    private void setListeners(){
        mHamburgerIcon = mMainPageFragment.requireActivity().findViewById(R.id.hamburgerIcon);
        mTeacherName = mMainPageFragment.requireActivity().findViewById(R.id.teachers_name);
        mSearchView = mMainPageFragment.requireActivity().findViewById(R.id.name_searchview);
        mTeacherName.setText("Hi, " + mMainPageFragment.getArguments().getString(Constants.TEACHER_NAME));
        attendanceIcon = mMainPageFragment.requireActivity().findViewById(R.id.attendance_icon);
        timeTableIcon = mMainPageFragment.requireActivity().findViewById(R.id.time_table_icon);
        addEventsIcon = mMainPageFragment.requireActivity().findViewById(R.id.add_events_icon);
        broadCastIcon = mMainPageFragment.requireActivity().findViewById(R.id.broadcast_icon);
        mDrawerLayout = mMainPageFragment.requireActivity().findViewById(R.id.drawer_layout);

        mHamburgerIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
                NavigationView navigationView =  mMainPageFragment.requireActivity().findViewById(R.id.navigationView);
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int menuId = menuItem.getItemId();
                        if( menuId == R.id.logout){
                            new PrefManager(mMainPageFragment.getContext()).removeLoginDetails();
                            mMainPageFragment.getActivity().onBackPressed();
                        }
                        return false;
                    }
                });
            }
        });

        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStudentsNameAdapter.getFilter().filter(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        attendanceIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainPageFragment.requireContext(), AttendanceActivity.class);
                intent.putExtra(Constants.STUDENT_DATA,(Serializable) studentList);
                intent.putExtra(Constants.CLASSROOM_ID,classRoomId);
                mMainPageFragment.requireActivity().startActivity(intent);
            }
        });

        addEventsIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

        broadCastIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainPageFragment.requireContext(), BroadcastActivity.class);
                intent.putExtra(Constants.CLASSROOM_ID,classRoomId);
                mMainPageFragment.requireActivity().startActivity(intent);
            }
        });

        timeTableIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setClassStudentData(){
        mSpinner = mMainPageFragment.requireActivity().findViewById(R.id.spinner_class_name);
        ClassService service = RetrofitClientInstance.getRetrofitInstance().create(ClassService.class);
        String authToken = new PrefManager(mMainPageFragment.getContext()).getLoginDetails();
        Map<String, String> hashMap = new HashMap();
        hashMap.put("Authorization", authToken);
        Call<ClassResponse> call = service.getClasses(hashMap);
        call.enqueue(new Callback<ClassResponse>() {
            @Override
            public void onResponse(Call<ClassResponse> call, Response<ClassResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus() == JSONParserConstants.SUCCESS_CODE){
                        readResponse(response.body());
                    }else {
                        ServerError.handleServerError(response.body().getStatus(),mMainPageFragment.getContext());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClassResponse> call, Throwable t) {
                Utils.showToast(mMainPageFragment.requireActivity(), "Something went wrong...Please try later!");
            }

        });

    }

    private void readResponse(ClassResponse body) {
        List<Classroom> classroomList = body.getClassroomData().getClassrooms();
        studentList = body.getClassroomData().getStudents();
        classRoomId = body.getClassroomData().getId();
        List<String> classRoomNames = new ArrayList<>();

        for(int i=0;i<classroomList.size();i++){
            classRoomNames.add(classroomList.get(i).getName());
        }

        setClassroomList(classRoomNames);
        setStudentList(studentList);
    }

    private void setClassroomList(List<String> classroomNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter(mMainPageFragment.requireActivity(),
                android.R.layout.simple_spinner_item,classroomNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    private void setStudentList( List<Student> studentList){
        mNameRecyclerView = mMainPageFragment.requireActivity().findViewById(R.id.name_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainPageFragment.requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mNameRecyclerView.setLayoutManager(linearLayoutManager);
        mStudentsNameAdapter = new StudentsNameAdapter(mMainPageFragment.requireContext(),studentList);
        mNameRecyclerView.setAdapter(mStudentsNameAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*private void filter(String text){
        //new array list that will hold the filtered data
        List<Student> filteredNames = new ArrayList<>();

        //looping through existing elements
        for (Student s : studentList) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase()) || s.getRollNo().contains(text)) {
                //adding the element to filtered list
                filteredNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mStudentsNameAdapter.filterList(filteredNames);
    }*/

}
