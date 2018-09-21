package com.javaSummary.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by administrator on 2018/9/6.
 */
public class Person implements Serializable{

    private String name;                                 //名字
    private int year;                                    //年龄
    private String city;                                 //城市

    public static void yell(){
        System.out.println("Person yell");
    }

    public Person() {
    }

    public Person(String name, int year, String city) {
        this.name = name;
        this.year = year;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", city='" + city + '\'' +
                '}';
    }
}
