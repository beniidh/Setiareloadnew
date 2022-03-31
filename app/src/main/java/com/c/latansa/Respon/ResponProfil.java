package com.c.latansa.Respon;

import com.c.latansa.Model.MSubMenu;
import com.c.latansa.Model.data;

import java.util.ArrayList;

public class ResponProfil {
    String code, error, message;
    data data;
    ArrayList<MSubMenu> menu;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public com.c.latansa.Model.data getData() {
        return data;
    }


}
