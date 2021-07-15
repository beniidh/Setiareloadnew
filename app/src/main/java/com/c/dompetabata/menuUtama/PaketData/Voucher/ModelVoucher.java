package com.c.dompetabata.menuUtama.PaketData.Voucher;

public class ModelVoucher {
    String id,name;
    String description,icon,product_category_id,status;

    public ModelVoucher(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
