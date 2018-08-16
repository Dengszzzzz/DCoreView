package com.fykj.dcoreview.bean.thread;

import java.util.LinkedList;

/**
 * Created by administrator on 2018/8/1.
 * 线程
 * 生产者-消费者（producer-consumer）问题，也称作有界缓冲区（bounded-buffer）问题，两个进程共享一个公共的固定大小的缓冲区。
 * 其中一个是生产者，用于将消息放入缓冲区；另外一个是消费者，用于从缓冲区中取出消息。
 * 问题出现在当缓冲区已经满了，而此时生产者还想向其中放入一个新的数据项的情形，其解决方法是让生产者此时进行休眠，
 * 等待消费者从缓冲区中取走了一个或者多个数据后再去唤醒它。同样地，当缓冲区已经空了，而消费者还想去取消息，
 * 此时也可以让消费者进行休眠，等待生产者放入一个或者多个数据时再唤醒它。
 */

public class ProducerConsumer {

    private LinkedList<Object> storeHouse = new LinkedList<>();
    private int MAX = 10;

    public ProducerConsumer() {
    }

    public void start() {
        new Producer().start();
        new Comsumer().start();
    }

    class Producer extends Thread {


        public void run() {
            while (true) {
                synchronized (storeHouse){
                    try {
                        while (storeHouse.size() == MAX) {
                            System.out.println("storeHouse is full , please wait");
                            storeHouse.wait();
                        }
                        Object newO = new Object();
                        if (storeHouse.add(newO)) {
                            System.out.println("Producer put a Object to storeHouse");
                            Thread.sleep((long) (Math.random() * 3000));
                            storeHouse.notify();
                        }
                    } catch (InterruptedException e) {
                        System.out.println("producer is interrupted!");
                    }
                }
            }
        }
    }

    class Comsumer extends Thread {

        public void run() {
            while (true) {
                synchronized (storeHouse) {
                    try {
                        while (storeHouse.size() == 0) {
                            System.out.println("storeHouse is empty , please wait");
                            storeHouse.wait();
                        }
                        storeHouse.removeLast();
                        System.out.println("Comsumer get  a Object from storeHouse");
                        Thread.sleep((long) (Math.random() * 3000));
                        storeHouse.notify();
                    } catch (InterruptedException e) {
                        System.out.println("Consumer  is interrupted!");
                    }
                }
            }
        }
    }
}
