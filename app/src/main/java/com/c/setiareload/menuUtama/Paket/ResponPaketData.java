package com.c.setiareload.menuUtama.Paket;

import java.util.ArrayList;

public class ResponPaketData {
    String code,message,error;

    ArrayList<MProdukPaketData> data;

    public ArrayList<MProdukPaketData> getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
