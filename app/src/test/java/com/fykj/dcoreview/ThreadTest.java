package com.fykj.dcoreview;

import com.fykj.dcoreview.bean.thread.ProducerConsumer;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.xml.transform.Source;

/**
 * Created by administrator on 2018/8/1.
 * 线程Test
 * 可以理解单元测试本身是一个子线程，它会一条一条的执行方法中的代码，当执行到另开子线程的方法时，
 * 它不会停下来等待而是继续往下执行，执行完毕这个单元测试方法的任务也就完成了。
 * 而新开的线程还是在执行，只是它们的父容器单元测试所在的线程已销毁，因而没有打印语句在界面上。
 * 因此--test单元测试无法测试多线程。
 */

public class ThreadTest {

    //生产者消费者测试
    @Test
    public void testProducerConsumer(){
        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.start();
    }

    @Test
    public void test(){

        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义信号量，只能5个线程同时访问
        final Semaphore semaphore = new Semaphore(5);
        for(int i = 0;i < 20; i++){
            final int NO = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取许可
                        semaphore.acquire();
                        //availablePermits()指的是当前信号灯库中有多少个可以被使用
                        System.out.println("线程" + Thread.currentThread().getName() + "进入，当前已有" + (5 - semaphore.availablePermits()) + "个并发");
                        System.out.println("index:" + NO);
                        Thread.sleep(1000);
                        System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
                        //访问完毕，释放
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }
        //退出线程池
        executorService.shutdown();
    }

}
