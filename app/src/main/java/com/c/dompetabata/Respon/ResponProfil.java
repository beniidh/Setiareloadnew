package com.c.dompetabata.Respon;

import com.c.dompetabata.Model.MSubMenu;
import com.c.dompetabata.Model.data;

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

    public com.c.dompetabata.Model.data getData() {
        return data;
    }


}
