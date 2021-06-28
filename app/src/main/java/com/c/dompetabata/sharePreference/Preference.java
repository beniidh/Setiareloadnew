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

    public static void setName(Context context, String name){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("nameM", name);
        editor.apply();
    }
    public static String getName(Context context){
        return getSharedPreference(context).getString("nameM","");
    }

    public static void setID(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("idM", id);
        editor.apply();
    }
    public static String getID(Context context){
        return getSharedPreference(context).getString("idM","");
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



    //ID provinsi

    public static void setIDProvinsi(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDProvinsi", id);
        editor.apply();
    }
    public static String getIDProvinsi(Context context){
        return getSharedPreference(context).getString("IDProvinsi","");
    }

    // ID kabupaten

    public static void setIDKabupaten(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDKabupaten", id);
        editor.apply();
    }
    public static String getIDKabupaten(Context context){
        return getSharedPreference(context).getString("IDKabupaten","");
    }

    // ID Kecamatan

    public static void setIDKecamatan(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDKecamatan", id);
        editor.apply();
    }
    public static String getIDKecamatan(Context context){
        return getSharedPreference(context).getString("IDKecamatan","");
    }

    // ID Kelurahan

    public static void setIDKelurahan(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDKelurahan", id);
        editor.apply();
    }
    public static String getIDKelurahan(Context context){
        return getSharedPreference(context).getString("IDKelurahan","");
    }

    public static void setkredentials(Context context, String credentials){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("credentials", credentials);
        editor.apply();
    }
    public static String getKredentials(Context context){
        return getSharedPreference(context).getString("credentials","");
    }
    public static void setPIN(Context context, String pin){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("PIN", pin);
        editor.apply();
    }
    public static String getPIN(Context context){
        return getSharedPreference(context).getString("PIN","");
    }

    public static void setLong(Context context, String longlitut){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("longlitut", longlitut);
        editor.apply();
    }

    public static String getLong(Context context){
        return getSharedPreference(context).getString("longlitut","");
    }

    public static void setLat(Context context, String latitude){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("latitude", latitude);
        editor.apply();
    }

    public static String getLat(Context context){
        return getSharedPreference(context).getString("latitude","");
    }

    public static void setToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String getToken(Context context){
        return getSharedPreference(context).getString("token","");
    }

    // Set Roles ID

    public static void setSaldoku(Context context, String saldoku){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("saldoku", saldoku);
        editor.apply();
    }

    public static String getSaldoku(Context context){
        return getSharedPreference(context).getString("saldoku","");
    }


}
