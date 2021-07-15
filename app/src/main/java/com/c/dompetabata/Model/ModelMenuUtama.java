package com.c.dompetabata.Model;

public class ModelMenuUtama {

    String id,name,description,status,icon,url,type;

    public ModelMenuUtama(String name, String icon, String url) {
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
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

    public String getStatus() {
        return status;
    }

    public String getIcon() {
        return icon;
    }
}
