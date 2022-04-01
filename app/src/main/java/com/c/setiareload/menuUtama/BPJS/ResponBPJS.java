package com.c.setiareload.menuUtama.BPJS;

import java.util.ArrayList;

public class ResponBPJS {

    String code,message,error;

    ArrayList<mData> data;
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    public class mData{

        String id,name,description,product_category_id,code;

        public String getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getProduct_category_id() {
            return product_category_id;
        }
    }
}
