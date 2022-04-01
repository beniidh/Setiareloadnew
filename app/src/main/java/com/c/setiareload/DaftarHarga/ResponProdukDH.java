package com.c.setiareload.DaftarHarga;

import java.util.ArrayList;

public class ResponProdukDH {

    String code,error,message;
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

    public class mData{

        String name,id,status,type;

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }
    }
}
