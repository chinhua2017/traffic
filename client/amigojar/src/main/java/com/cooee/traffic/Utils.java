package com.cooee.traffic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by on 2017/6/20.
 */

public class Utils {

    public static boolean isStringEmpty(String str) {
        if(str == null || str.trim().length() == 0 || str.trim().equals("null")) {
            return true;
        } else {
            return false;
        }
    }

    public static String preparePhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim().replaceAll("-", "").replaceAll(" ", "");
        if (phoneNumber.startsWith("+86")) {
            phoneNumber = phoneNumber.substring(3);
        } else if (phoneNumber.startsWith("86")) {
            phoneNumber = phoneNumber.substring(2);
        }
        return phoneNumber;
    }

    public static void v(String msg) {
        Log.v("traffic_log", msg);
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
