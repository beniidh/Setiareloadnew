package com.c.dompetabata.Model;

public class MRegister {
    String name;
    String email;
    String phone;
    String message;
    public MRegister(){}

    public MRegister(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    protected String getName() {
        return name;
    }

    protected String getEmail() {
        return email;
    }

    protected String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }
}
