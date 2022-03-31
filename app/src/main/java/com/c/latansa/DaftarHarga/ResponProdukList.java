package com.c.latansa.DaftarHarga;

import java.util.ArrayList;

public class ResponProdukList {
    String code, error, message;
    ArrayList<mData> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    public class mData {

        String name, basic_price,total_price;

        public String getName() {
            return name;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getBasic_price() {
            return basic_price;
        }
    }
}
