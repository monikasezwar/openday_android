package com.app.openday.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String authValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Authorization",authValue);
        editor.commit();
    }

    public String getLoginDetails(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        return sharedPreferences.getString("Authorization","");
    }

    public void removeLoginDetails(){
       SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.remove("Authorization");
       editor.commit();
    }
}
