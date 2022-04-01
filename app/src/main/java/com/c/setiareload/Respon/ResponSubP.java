package com.c.setiareload.Respon;

import com.c.setiareload.Model.MSubPLN;

import java.util.List;

public class ResponSubP {
    String code ;
    String error;
    List<MSubPLN> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public List<MSubPLN> getData() {
        return data;
    }
}
