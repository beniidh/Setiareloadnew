package com.c.latansa.CetakStruk;

public class ResponCodeSubPS {
    String code,error;
    Data data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    public class Data{

        Product_subcategory product_subcategory;
        public Product_subcategory getProduct_subcategory() {
            return product_subcategory;
        }

        public class Product_subcategory{
            String id;
            public String getId() {
                return id;
            }
        }
    }
}
