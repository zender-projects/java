package com.zd.learn.java.basic.concurrencyart.chapter01;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try{
                        TimeUnit.SECONDS.sleep(2);
                    }catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println(2);
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }

}
