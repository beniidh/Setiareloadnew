package com.c.setiareload.TransferBank;

import java.util.ArrayList;

public class ModelNamaBank {

    String code,error,message;
    ArrayList<mNama> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<mNama> getData() {
        return data;
    }

    public class mNama{

        String id,code,name;
        boolean gangguan;

        public String getId() {
            return id;
        }
        public String getCode() {
            return code;
        }
        public String getName() {
            return name;
        }
        public boolean isGangguan() {
            return gangguan;
        }
    }
}
