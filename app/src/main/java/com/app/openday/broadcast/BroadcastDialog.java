package com.app.openday.broadcast;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.app.openday.R;

public class BroadcastDialog extends DialogFragment {

    private static Context context;
    private TextView absentStudentText;
    private LinearLayout confirmAndSend;
    private OnConfirmAndSendClickListener listener;
    private int absentStudentCount;

    public BroadcastDialog() {

    }

    public BroadcastDialog(int absentStudentCount, OnConfirmAndSendClickListener listener) {
        this.absentStudentCount = absentStudentCount;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.broadcast_dialog_layout,container,false);
        absentStudentText = view.findViewById(R.id.absent_student_count);
        confirmAndSend =  view.findViewById(R.id.confirm_send_button);
        absentStudentText.setText(String.valueOf(absentStudentCount)+ " parent(s)");
        confirmAndSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.OnConfirmAndSendClicked();
                dismiss();
            }
        });
        return view;
    }

    public interface OnConfirmAndSendClickListener{
         void OnConfirmAndSendClicked();
    }
}
