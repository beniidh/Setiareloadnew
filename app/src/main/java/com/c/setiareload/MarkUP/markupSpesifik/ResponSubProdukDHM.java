package com.c.setiareload.MarkUP.markupSpesifik;

import java.util.ArrayList;

public class ResponSubProdukDHM {

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

    public static class mData{

        String name,id,status;

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
