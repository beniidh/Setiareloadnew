package com.c.latansa.Api;

import android.content.Context;
import android.webkit.WebView;

import com.c.latansa.Helper.utils;
import com.c.latansa.Model.data;

public class Value {

    String credentials;
    String password;
    data data;
    String message;
    public static String BASE_URL = "https://api-mobile.c-software.id/";   //https://api-mobile-staging.abatapulsa.com/

    public String getMessage() {
        return message;
    }

    public Value() {
    }

    public data getData() {
        return data;
    }

    public Value(String credentials, String password) {
        this.credentials = credentials;
        this.password = password;
    }

    public static String getMacAddress(Context context) {
        return utils.getMacAddr();
    }

    public static String getUserAgent(Context context) {

        String ua = new WebView(context).getSettings().getUserAgentString();
        return ua;
    }

    public static String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }


}
