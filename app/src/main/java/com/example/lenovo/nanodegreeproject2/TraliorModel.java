package com.example.lenovo.nanodegreeproject2;

public class TraliorModel {

    String name,key;

    public TraliorModel(String traliorname, String traliorkey) {
        this.name=traliorname;
        this.key=traliorkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
