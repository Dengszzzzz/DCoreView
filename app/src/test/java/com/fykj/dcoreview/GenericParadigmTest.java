package com.fykj.dcoreview;

import android.app.ApplicationErrorReport;

import com.fykj.dcoreview.bean.genericParadigm.Apple;
import com.fykj.dcoreview.bean.genericParadigm.Food;
import com.fykj.dcoreview.bean.genericParadigm.Fruit;
import com.fykj.dcoreview.bean.genericParadigm.Meat;
import com.fykj.dcoreview.bean.genericParadigm.Plate;
import com.fykj.dcoreview.bean.genericParadigm.RedApple;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by administrator on 2018/7/31.
 * 泛型测试
 * 1.掌握泛型类，泛型方法的写法
 * 2.区分 extends 和 super
 * 3.泛型的好处：1)类型检查  2)避免强制转换 3)性能优化
 */

public class GenericParadigmTest {

    @Test
    public void test(){
        //1."装苹果的盘子"无法转成"装水果的盘子",因为容器之间是没有继承关系的
        //Plate<Fruit> p = new Plate<>(new Apple());   //Error

        //2.上界通配符 Plate<? extends Fruit>，装Fruit及其子类的盘子
        //Plate<? extends Fruit> p = new Plate<>(new Apple());
        //Plate<? extends Fruit> p2 = new Plate<>(new RedApple());

        //副作用：
        //上界<? extends T>不能往里存，只能往外取
        //原因是编译器只知道容器内是Fruit或者它的派生类，但具体是什么类型不知道。
        // 可能是Fruit？可能是Apple？也可能是Banana，RedApple,，GreenApple？
        // 编译器在看到后面用Plate<Apple>赋值以后，盘子里没有被标上有“苹果”。
        //p.setItem(new Apple());    //Error
        //p.setItem(new Fruit());     //Error
        //Fruit newFruit = p.getItem();
        //Object newFruit2 = p.getItem();
        //Apple newFruit3 = p.getItem();    //Error


        //3.下界通配符 Plate<？ super Fruit>,装Fruit及其父类的盘子
        Plate<? super Fruit> p = new Plate<>(new Food());
        //副作用
        //下界<? super T>不影响往里存，但往外取只能放在Object对象里
        //p.setItem(new Fruit());
        //p.setItem(new Apple());  //???
        //Apple newFruit1 = p.getItem();  //Error
        //Fruit newFruit2 = p.getItem();  //Error
        //Object newFruit3 = p.getItem();


        /*List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        p.show(list);*/

    }

}
