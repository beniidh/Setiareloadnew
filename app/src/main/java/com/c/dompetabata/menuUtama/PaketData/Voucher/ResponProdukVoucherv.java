package com.c.dompetabata.menuUtama.PaketData.Voucher;

import com.c.dompetabata.menuUtama.PaketData.PaketsmsTelpon.MProdukSmsTelpon;

import java.util.ArrayList;

public class ResponProdukVoucherv {

    String code, message,error;
    ArrayList<VoucherData> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public ArrayList<VoucherData> getData() {
        return data;
    }

    public class VoucherData{

        String id,code,name,brand,basic_price,markup_price,status,description,product_subcategory_id;
        boolean seller_product_status,multi;

        public String getId() {
            return id;
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

        public String getBasic_price() {
            return basic_price;
        }

        public String getMarkup_price() {
            return markup_price;
        }

        public String getStatus() {
            return status;
        }

        public String getDescription() {
            return description;
        }

        public String getProduct_subcategory_id() {
            return product_subcategory_id;
        }

        public boolean isSeller_product_status() {
            return seller_product_status;
        }

        public boolean isMulti() {
            return multi;
        }
    }
}
