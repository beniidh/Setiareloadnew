package com.c.setiareload.menuUtama.HolderPulsa;

import java.util.ArrayList;

public class ResponSub {

    String error,code;
    ArrayList<mData>data;

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    protected class mData {
        String name,id,icon;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getIcon() {
            return icon;
        }
    }
}
