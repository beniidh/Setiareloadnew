package com.c.setiareload.menuUtama.BPJS;

public class MBpjs {

    String id,name;
    String description,icon,product_category_id,status;

    public MBpjs(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
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

    public String getStatus() {
        return status;
    }
}
