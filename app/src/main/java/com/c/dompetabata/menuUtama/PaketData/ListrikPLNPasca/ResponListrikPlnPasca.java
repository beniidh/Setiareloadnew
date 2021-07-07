package com.c.dompetabata.menuUtama.PaketData.ListrikPLNPasca;

import com.c.dompetabata.menuUtama.PaketData.ListrikPLN.ModelProdukPln;

import java.util.List;

public class ResponListrikPlnPasca {

    String code, message;

    List<ModelProdukPlnPasca> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelProdukPlnPasca> getData() {
        return data;
    }
}
