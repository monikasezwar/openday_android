package com.app.openday.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.openday.R;
import com.app.openday.network.response.Record;
import com.app.openday.network.response.Student;
import com.app.openday.utils.OnSubmitClickListener;
import com.app.openday.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AbsentStudentsNameAdapter extends RecyclerView.Adapter {

    private List<Student> studentList;
    private List<Record> attendanceRecords;
    private Context context;
    private AbsentStudentsNameViewHolder studentsNameViewHolder;
    //there should be a different POJO class of student holding info like parents contact, address , n all
    private List<Record> studentListWithAttendance = new ArrayList<>();
    private Record record;
    private OnSubmitClickListener callback;
    private AttendanceFragment attendanceFrag;
    private TextView submitButton;


    public AbsentStudentsNameAdapter(Context context, List<Student> studentList, List<Record> attendanceRecords, AttendanceView attendanceView, AttendanceFragment attendanceFragment){
        this.context = context;
        this.studentList = studentList;
        this.attendanceRecords = attendanceRecords;
        this.callback = attendanceView;
        this.attendanceFrag = attendanceFragment;

        for(int i =0;i < studentList.size();i++){
            record = new Record(studentList.get(i).getId(),false);
            studentListWithAttendance.add(record);
        }
        submitButton = attendanceFrag.requireActivity().findViewById(R.id.submit_text);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                callback.onSubmitAttendance(studentListWithAttendance);
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.absentee_list_item,parent,false);
        studentsNameViewHolder = new AbsentStudentsNameViewHolder(view);
        return studentsNameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        studentsNameViewHolder.rollNo.setText(studentList.get(position).getRollNo());
        studentsNameViewHolder.studentName.setText(studentList.get(position).getName());
        if(attendanceRecords == null){
            Utils.showToast(context,"Today's attendance has not been taken");
            studentsNameViewHolder.attendanceStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        //marking attendance as absent only
                        studentListWithAttendance.get(position).setAbsent(true);
                        buttonView.setBackgroundColor(context.getResources().getColor(R.color.red));
                        buttonView.setText("A");
                    }else{
                        studentListWithAttendance.get(position).setAbsent(false);
                        buttonView.setBackgroundColor(context.getResources().getColor(R.color.green));
                        buttonView.setText("P");
                    }
                }
            });
        }else{
            studentsNameViewHolder.attendanceStatus.setClickable(false);
            if(studentList.get(position).getId().contentEquals(attendanceRecords.get(position).getStudent())){
                if(attendanceRecords.get(position).getAbsent()){
                    studentsNameViewHolder.attendanceStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
                    studentsNameViewHolder.attendanceStatus.setText("A");
                }else{
                    studentsNameViewHolder.attendanceStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
                    studentsNameViewHolder.attendanceStatus.setText("P");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class AbsentStudentsNameViewHolder extends RecyclerView.ViewHolder{
        TextView studentName;
        Button rollNo;
        CheckBox attendanceStatus;
        public AbsentStudentsNameViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            rollNo = itemView.findViewById(R.id.roll_no_icon);
            attendanceStatus = itemView.findViewById(R.id.attendance_status_button);
        }
    }
}
