package com.c.setiareload.MarkUP.markupSpesifik;

import java.util.ArrayList;

public class ResponProdukListM {
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

        String name, basic_price,total_price,user_id,server_code,product_id,id,status;

        public String getStatus() {
            return status;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getServer_code() {
            return server_code;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getId() {
            return id;
        }

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
