package com.app.openday.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public Utils() {
    }

    public static String getTodaysDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static boolean isValidMobileNumber(String mobileNumber){
        return !TextUtils.isEmpty(mobileNumber) &&
                mobileNumber.length() == 10;
    }

    public static boolean isValidPassword(String password){
        return !TextUtils.isEmpty(password) &&
                password.length() >= 6;
    }

    public static void showToast(Context context, String message){
        Toast toast =  Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

  /*  public static Long convertDateIntoTimeMillis(String currentDate) {
        Date parsed = new Date();
        if (TextUtils.isEmpty(currentDate)) {
            return 0L;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            parsed = simpleDateFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsed.getTime();
    }*/

  public static Long convertDateIntoTimeMillis(String currentDate){
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
      Date date = null;
      try {
          date = sdf.parse(currentDate);
      } catch (ParseException e) {
          e.printStackTrace();
      }
      long millis = date.getTime();
      return millis;
    }

    public static void hideKeyboard(View view){
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }

        return isConnected;
    }


}
