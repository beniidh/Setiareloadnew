package com.c.setiareload.Fragment.Respon;

import java.util.ArrayList;

public class MRuningText {

    String code,error;
   ArrayList<MData> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<MData> getData() {
        return data;
    }

    public class MData{

        String server_id,name,text_apk;

        public String getServer_id() {
            return server_id;
        }

        public String getName() {
            return name;
        }

        public String getText_apk() {
            return text_apk;
        }
    }
}
