package com.c.dompetabata.Respon;

import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Model.ModelProvinsi;
import com.google.gson.annotations.SerializedName;

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
