package com.c.dompetabata.menuUtama.PaketData.PaketsmsTelpon;

import com.c.dompetabata.menuUtama.PaketData.VoucherGame.MVoucherGame;

import java.util.ArrayList;

public class ResponSmsTelpon {

    String code, message,error;
    ArrayList<MProdukSmsTelpon> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public ArrayList<MProdukSmsTelpon> getData() {
        return data;
    }
}
