package com.c.dompetabata.Respon;

import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Model.ModelKelurahan;

import java.util.List;

public class Responkel {

    String code;
    List<ModelKelurahan> data;

    public String getCode() {
        return code;
    }

    public List<ModelKelurahan> getData() {
        return data;
    }
}
