package com.c.setiareload.Respon;

import com.c.setiareload.Model.MSubMenu;
import com.c.setiareload.Model.data;

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

    public com.c.setiareload.Model.data getData() {
        return data;
    }


}
