package com.c.dompetabata.TopUpSaldoku;

public class ReqSaldoku {


    String pin, mac_address, ip_address, user_agent;
    double latitude, longitude, amount;
    String code,message;

    public ReqSaldoku(String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double amount) {
        this.pin = pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.latitude = latitude;
        this.longitude = longitude;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
