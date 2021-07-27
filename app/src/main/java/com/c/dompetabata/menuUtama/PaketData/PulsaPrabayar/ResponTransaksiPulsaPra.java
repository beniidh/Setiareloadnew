package com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar;

public class ResponTransaksiPulsaPra {

    String code,error,message;
    MTransaksiPulsaRespon data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public MTransaksiPulsaRespon getData() {
        return data;
    }
}
