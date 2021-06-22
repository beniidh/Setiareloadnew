package com.c.dompetabata.Respon;

import com.c.dompetabata.Model.MsubCategory;

import java.util.ArrayList;

public class ResponSubCategory {

    ArrayList<MsubCategory> data;
    String code,success,message;

    public String getCode() {
        return code;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MsubCategory> getData() {
        return data;
    }
}
