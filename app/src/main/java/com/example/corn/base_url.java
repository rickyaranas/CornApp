package com.example.corn;

public class base_url {
    private static base_url instance;
    String base_url = "http://192.168.100.6/BISU/";

    public String getBase_url(){
        return base_url;
    }


    public static synchronized base_url getInstance() {
        if (instance == null) {
            instance = new base_url();
        }
        return instance;
    }
}
