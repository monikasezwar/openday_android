package com.app.openday.password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.openday.R;

public class PasswordFragment extends Fragment {

    private PasswordView passwordView;
    private PasswordViewModel passwordViewModel;

    public PasswordFragment(){

    }

    public static PasswordFragment newInstance(Bundle bundle){
        PasswordFragment passwordFragment = new PasswordFragment();
        passwordFragment.setArguments(bundle);
        return passwordFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.password_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passwordView = new PasswordView(this);
        passwordView.onViewCreated();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        passwordViewModel = new PasswordViewModel();

    }
}
