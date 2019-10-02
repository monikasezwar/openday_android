package com.app.openday.mainpage;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.openday.R;
import com.app.openday.utils.Constants;

public class MainPageActivity extends AppCompatActivity {

    private MainPageFragment mainPageFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);
        mainPageFragment = new MainPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TEACHER_NAME,getIntent().getStringExtra(Constants.TEACHER_NAME));
        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,mainPageFragment.newInstance(bundle)).commit();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
