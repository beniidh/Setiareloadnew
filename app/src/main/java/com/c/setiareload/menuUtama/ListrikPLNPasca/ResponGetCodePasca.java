package com.c.setiareload.menuUtama.ListrikPLNPasca;

import java.util.ArrayList;

public class ResponGetCodePasca {

    String code,error,message;

    ArrayList<mData> data;

    public ArrayList<mData> getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public class mData {

        String id,user_id,server_code,product_id,product_subcategory_id,code,name,brand;

        public String getId() {
            return id;
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

        public String getProduct_subcategory_id() {
            return product_subcategory_id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getBrand() {
            return brand;
        }
    }
}
