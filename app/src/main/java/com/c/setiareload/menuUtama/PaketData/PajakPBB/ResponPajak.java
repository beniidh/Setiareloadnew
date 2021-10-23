package com.c.setiareload.menuUtama.PaketData.PajakPBB;

import java.util.ArrayList;

public class ResponPajak {
    String code,error,message;
    ArrayList<ModelPajak> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ModelPajak> getData() {
        return data;
    }
}
