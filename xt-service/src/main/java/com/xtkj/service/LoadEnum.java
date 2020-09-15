package com.xtkj.service;

public enum LoadEnum {

    BANK("com.xtkj.pojo.user");

    private String clazz;

    public String getClazz(){
        return clazz;
    }

    LoadEnum(String clazz){
        this.clazz = clazz;
    }
}
