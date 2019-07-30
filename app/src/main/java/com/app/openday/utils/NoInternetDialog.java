package com.app.openday.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.app.openday.R;

public class NoInternetDialog extends DialogFragment {
    private ImageView imageview;
    private TextView okGotIt,notificationHeader,notificationMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.notification_dialog,container,false);
        imageview = view.findViewById(R.id.icon);
        okGotIt = view.findViewById(R.id.ok_message);
        notificationHeader = view.findViewById(R.id.notification_header);
        notificationMessage = view.findViewById(R.id.notification_message);

        //imageview.setImageDrawable(R.drawable.no_internet);
        okGotIt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        notificationHeader.setText("No Internet");
        notificationMessage.setText("Please check your internet connection and try again");

        return view;
    }

}
