package com.c.latansa.KomisiSales;

import java.util.ArrayList;

public class ResponSales {

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



        String id,komisi,sales_id,server_id;

        public String getId() {
            return id;
        }

        public String getKomisi() {
            return komisi;
        }

        public String getSales_id() {
            return sales_id;
        }

        public String getServer_id() {
            return server_id;
        }
    }
}
