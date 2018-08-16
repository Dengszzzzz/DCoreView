package com.fykj.dcoreview.bean.genericParadigm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 2018/7/31.
 * 盘子
 */

//泛型类
public class Plate<T>{
    private T item;

    public Plate(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    //无参数方法定义时，使用泛型
    public <E> List<E> newArrayList() {
        return new ArrayList<E>();
    }

    //有参数方法定义时，使用泛型
    public <V> void showClass(V v) {
        System.out.println(v.getClass());
    }

    //类或者方法定义时，使用通配符 <T extends Number>
    public <D extends Number> void showClassNumber(D t) {
        System.out.println(t.getClass());
    }

    //方法定义时，使用通配符 ？
    public void show(List<?> list) {
        list.add(null);
        //list.add(123); 编译错误
        for (Object object:list) {
            System.out.println(object);
        }
    }

}
