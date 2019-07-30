package com.app.openday.broadcast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.openday.R;
import com.app.openday.utils.Constants;

public class BroadcastActivity extends AppCompatActivity {

    private BroadcastFragment broadcastFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_activity);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CLASSROOM_ID,getIntent().getStringExtra(Constants.CLASSROOM_ID));
        broadcastFragment = new BroadcastFragment();
        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, broadcastFragment.newInstance(bundle)).commit();
        }
    }
}
