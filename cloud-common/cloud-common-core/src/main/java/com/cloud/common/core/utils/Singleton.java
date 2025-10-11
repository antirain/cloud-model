package com.cloud.common.core.utils;

/**
 * @author local
 * @date 2025/10/9 17:12
 * @description
 */
public enum Singleton {

    INSTANCE;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println("name:" + name);
    }
}
