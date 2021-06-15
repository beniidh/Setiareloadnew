package com.c.dompetabata.Model;

public class Mlogin {
    String id,code,name,email,phone,message;
    String credentials,password,ip_address,user_agent;
    Double longitude,latitude;

    public Mlogin(String credentials, String password, String ip_address, String user_agent, Double longitude, Double latitude) {
        this.credentials = credentials;
        this.password = password;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
