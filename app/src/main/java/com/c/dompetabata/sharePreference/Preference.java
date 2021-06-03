package com.c.dompetabata.sharePreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {

    static final String KEY_USER_ID ="user_id";
    static final String KEY_OTP_ID = "otp_id";
    static final String KEY_USER_CODE = "code_id";
    static final String KEY_PHONE = "phone_id";

    /** Pendlakarasian Shared Preferences yang berdasarkan paramater context */
    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    //user id
    public static void setKeyUserId(Context context, String userid){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_ID, userid);
        editor.apply();
    }

    //otp id
    public static void setKeyOtpId(Context context, String otpid){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_OTP_ID, otpid);
        editor.apply();
    }

    public static void setKeyUserCode(Context context, String usercode){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_CODE, usercode);
        editor.apply();
    }

    public static void setKeyPhone(Context context, String phoneid){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PHONE, phoneid);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_USER_TEREGISTER berupa String */
    public static String getKeyPhone(Context context){
        return getSharedPreference(context).getString(KEY_PHONE,"");
    }

    public static String getKeyUserId(Context context){
        return getSharedPreference(context).getString(KEY_USER_ID,"");
    }

    public static String getKeyUserCode(Context context){
        return getSharedPreference(context).getString(KEY_USER_CODE,"");
    }

    public static String getKeyOtpId(Context context){
        return getSharedPreference(context).getString(KEY_OTP_ID,"");
    }
}
