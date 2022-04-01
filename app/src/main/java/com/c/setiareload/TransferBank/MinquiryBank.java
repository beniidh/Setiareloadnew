package com.c.setiareload.TransferBank;

public class MinquiryBank {
    String buyer_sku_code,customer_no,type,mac_address,ip_address,user_agent;
    double longitude,latitude;
    int amount;

    public MinquiryBank(String buyer_sku_code, int amount, String customer_no, String type, String mac_address, String ip_address, String user_agent, double longitude, double latitude) {
        this.buyer_sku_code = buyer_sku_code;
        this.customer_no = customer_no;
        this.type = type;
        this.mac_address = mac_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.amount = amount;
        this.latitude = latitude;
        this.ip_address = ip_address;
    }
}
