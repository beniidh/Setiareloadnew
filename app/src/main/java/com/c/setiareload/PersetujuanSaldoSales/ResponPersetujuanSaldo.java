package com.c.setiareload.PersetujuanSaldoSales;

import java.util.ArrayList;

public class ResponPersetujuanSaldo {

    String code, message, error;

    ArrayList<ModelPersetujuanSaldo> data;

    public String getCode() {
        return code;
    }

    public ArrayList<ModelPersetujuanSaldo> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
