package com.fykj.dcoreview;

import com.javaSummary.AlgorithmUtils;
import com.javaSummary.SemaphoreTest;
import com.javaSummary.bean.Person;
import com.javaSummary.bean.PersonSon;

import org.junit.Test;

/**
 * Created by administrator on 2018/9/6.
 */
public class JavaTest {


    @Test
    public void test(){

        //test单元测试无法测试多线程
        /*final SemaphoreTest test = new SemaphoreTest();
        for(int i=0 ;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test.test();
                }
            }).start();
        }*/
    }

    /**
     * 静态方法静态属性是否可以重写？ 可以继承，但不能重写。
     */
    @Test
    public void testStaticOvr(){
        Person person = new Person();
        Person person1 = new PersonSon();  //如果可以重写，此处应该是调用PersonSon的yell()，但实际上是调用的Person的yell()
        PersonSon son = new PersonSon();
        person.yell();
        person1.yell();
        son.yell();
    }

    @Test
    public void onTestAlgorithm(){
        AlgorithmUtils.onTestNarcissus(10,4000000000L);
    }
}
