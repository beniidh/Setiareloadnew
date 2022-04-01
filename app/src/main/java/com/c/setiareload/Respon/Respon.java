package com.c.setiareload.Respon;

import com.c.setiareload.Model.ModelProvinsi;

import java.util.List;

public class Respon {
    String code ;
    String error;
    List<ModelProvinsi> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public List<ModelProvinsi> getData() {
        return data;
    }

}
