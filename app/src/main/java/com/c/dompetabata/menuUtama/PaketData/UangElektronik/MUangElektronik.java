package com.c.dompetabata.menuUtama.PaketData.UangElektronik;

public class MUangElektronik {
    String id,name;
    String description,icon,product_category_id,status;

    public MUangElektronik(String id, String name) {
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
