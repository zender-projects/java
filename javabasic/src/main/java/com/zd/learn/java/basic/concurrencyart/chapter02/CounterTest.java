package com.zd.learn.java.basic.concurrencyart.chapter02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个基于CAS实现的线程安全的计数器
 * 和
 * 一个非线程安全的计数器
 * */
public class CounterTest {

    private AtomicInteger atomicInteger = new AtomicInteger();
    private int i = 0;

    public static void main(String[] args) {
        final CounterTest counter = new CounterTest();
        List<Thread> threadList = new ArrayList<Thread>(600);
        long start = System.currentTimeMillis();
        for(int j = 0;j < 100;j ++) {
            //创建100个线程来并发测试两种计数器
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0;i < 10000;i ++) {
                        counter.safeCounter();
                        counter.counter();
                    }
                }
            });
            threadList.add(t);
        }

        //启动线程
        for(Thread t : threadList) {
            t.start();
        }

        //等待所有线程执行完成
        for (Thread t : threadList) {
            try {
                t.join();
            }catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        //输出结果
        System.out.println(counter.i);
        System.out.println(counter.atomicInteger.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    //基于CAS的自旋锁实现
    public void safeCounter(){
        for(;;) {
            int i = atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i, ++ i );
            if(suc){
                break;
            }
        }
    }

    //非线程安全计数器
    public void counter(){
        i ++;
    }
}
