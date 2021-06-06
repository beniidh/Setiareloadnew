package com.c.dompetabata.Helper;

import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Model.ModelProvinsi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Respon {
    String code ;
    List<ModelProvinsi> data;

//    @SerializedName("data")
//    List<ModelKabupaten> datakabupaten;

    public String getCode() {
        return code;
    }

    public List<ModelProvinsi> getData() {
        return data;
    }
//
//    public List<ModelKabupaten> getDatakabupaten() {
//        return datakabupaten;
//    }
}
