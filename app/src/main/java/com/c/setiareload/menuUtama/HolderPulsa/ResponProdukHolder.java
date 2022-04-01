package com.c.setiareload.menuUtama.HolderPulsa;

import java.util.ArrayList;

public class ResponProdukHolder {

    String error, code;
    ArrayList<Mdata> data;

    public ArrayList<Mdata> getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    protected class Mdata {

        String id, code, brand, basic_price, name, description, icon, product_category_id, created_at, updated_at, total_price;
        boolean gangguan;

        public String getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getBrand() {
            return brand;
        }

        public String getBasic_price() {
            return basic_price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public String getProduct_category_id() {
            return product_category_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getTotal_price() {
            return total_price;
        }

        public boolean isGangguan() {
            return gangguan;
        }
    }
}