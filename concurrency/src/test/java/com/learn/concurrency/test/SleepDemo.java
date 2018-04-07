package com.learn.concurrency.test;

import java.util.concurrent.TimeUnit;

public class SleepDemo implements Runnable{


    @Override
    public void run() {
        try {
            synchronized (SleepDemo.class) {
                System.out.println(Thread.currentThread().getName() + " running....");
                System.out.println(Thread.currentThread().getName() + " sleep start");
                //Thread.sleep(1000);
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " sleep end");
            }
        }catch (InterruptedException ex) {

        }
    }



    public static void main(String[] args) {
        new Thread(new SleepDemo(), "Thread1").start();
        new Thread(new SleepDemo(), "Thread2").start();
    }
}
