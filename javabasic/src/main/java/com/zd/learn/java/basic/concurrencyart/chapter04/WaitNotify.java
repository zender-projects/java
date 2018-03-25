package com.zd.learn.java.basic.concurrencyart.chapter04;

import com.zd.learn.java.basic.concurrencyart.util.SleepUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();


    public static void main(String[] args) {
        Thread wait = new Thread(new Wait(), "WaitThread");
        wait.start();
        SleepUtil.sleep(1);
        Thread notify = new Thread(new Notify(), "NotifyThread");
        notify.start();

    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                //检查等待标记
                while (flag) {
                    try{
                        System.out.println(Thread.currentThread() + " flag is true. wait @"
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    }catch (InterruptedException ex) {

                    }
                }
                //等待被唤醒
                System.out.println(Thread.currentThread() + " flag is false. running @"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock. notify @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();  //唤醒在lock上等待的线程
                flag = false;      //将等待标记设置为false
                SleepUtil.sleep(5);
            }

            //再次尝试获取锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock again. sleep @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtil.sleep(5);
            }
        }
    }
}
