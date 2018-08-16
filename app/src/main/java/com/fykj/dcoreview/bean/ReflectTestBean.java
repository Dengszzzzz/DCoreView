package com.fykj.dcoreview.bean;

import android.os.Bundle;

/**
 * Created by administrator on 2018/7/27.
 */

public class ReflectTestBean {

    private String name;
    private int age;

    public ReflectTestBean() {
    }

    public ReflectTestBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name:" + name + "  age:" + age;
    }
}
