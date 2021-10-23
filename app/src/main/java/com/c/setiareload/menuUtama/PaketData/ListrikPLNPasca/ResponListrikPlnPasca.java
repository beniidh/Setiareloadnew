package com.c.setiareload.menuUtama.PaketData.ListrikPLNPasca;

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
