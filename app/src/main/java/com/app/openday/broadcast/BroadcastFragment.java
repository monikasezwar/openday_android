package com.app.openday.broadcast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.openday.R;

public class BroadcastFragment extends Fragment {

    private BroadcastView broadcastView;

    public static BroadcastFragment newInstance(Bundle bundle) {

        BroadcastFragment fragment = new BroadcastFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.broadcast_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        broadcastView = new BroadcastView(this);
        broadcastView.onViewCreated();
    }
}
