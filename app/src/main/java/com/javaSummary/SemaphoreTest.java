package com.javaSummary;

import java.util.concurrent.Semaphore;

/**
 * Created by administrator on 2018/9/6.
 */
public class SemaphoreTest {

    //Semaphore就是信号量
    private Semaphore mSemaphore = new Semaphore(5,true);  //定义计数器为5

    public void test(){
        try {
            mSemaphore.acquire();   //申请一个请求，计数-1
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "进来了");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "出去了");

        mSemaphore.release();   //释放一个请求，计数+1

    }

}
