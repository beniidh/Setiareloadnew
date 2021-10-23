package com.c.setiareload.konter;

import java.util.ArrayList;

public class Mkonter {

    String code, error;
    ArrayList<mData> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    public class mData {
        String id, name, store_name, code;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getStore_name() {
            return store_name;
        }

        public String getCode() {
            return code;
        }
    }
}
