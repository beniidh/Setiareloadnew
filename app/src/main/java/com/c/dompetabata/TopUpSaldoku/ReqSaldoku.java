package com.c.dompetabata.TopUpSaldoku;

public class ReqSaldoku {


    String pin, mac_address, ip_address, user_agent,error;
    double latitude, longitude, amount;
    String code,message;
    mData data;

    public ReqSaldoku(String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double amount) {
        this.pin = pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.latitude = latitude;
        this.longitude = longitude;
        this.amount = amount;
    }

    public mData getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public class mData{

        String id,type,amount;

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getAmount() {
            return amount;
        }
    }
}
