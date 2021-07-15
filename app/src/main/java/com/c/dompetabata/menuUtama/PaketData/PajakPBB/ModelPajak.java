package com.c.dompetabata.menuUtama.PaketData.PajakPBB;

public class ModelPajak {
    String id,name;
    String description,icon,product_category_id,status;

    public ModelPajak(String id, String name) {
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
