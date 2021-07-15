package com.c.dompetabata.menuUtama.PaketData.AngsuranKredit;

public class ModelAngsuran {
    String id,name;
    String description,icon,product_category_id,status;

    public ModelAngsuran(String id, String name) {
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
