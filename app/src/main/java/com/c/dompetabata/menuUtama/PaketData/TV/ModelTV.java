package com.c.dompetabata.menuUtama.PaketData.TV;

public class ModelTV {
    String id,name;
    String description,icon,product_category_id,status;

    public ModelTV(String id, String name) {
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
