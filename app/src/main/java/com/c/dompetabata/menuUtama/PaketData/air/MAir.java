package com.c.dompetabata.menuUtama.PaketData.air;

public class MAir {

    String id,name;
    String description,icon,product_category_id,status;

    public MAir(String id, String name) {
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
