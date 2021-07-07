package com.c.dompetabata.menuUtama.PaketData.PaketsmsTelpon;

import com.c.dompetabata.menuUtama.PaketData.VoucherGame.MVoucherGame;

import java.util.ArrayList;

public class ResponSmsTelpon {

    String code, message;
    ArrayList<MsmsTelpon> data;
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MsmsTelpon> getData() {
        return data;
    }
}
