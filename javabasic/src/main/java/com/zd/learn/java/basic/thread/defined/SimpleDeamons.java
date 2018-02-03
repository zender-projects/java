package com.zd.learn.java.basic.thread.defined;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleDeamons implements Runnable{


    @Override
    public void run() {
        try{
            while(true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        }catch (Exception ex){

        }
    }

    public static void main(String[] args) throws Exception {
        for(int i = 0;i < 10;i ++) {
            Thread deamon = new Thread(new SimpleDeamons());
            deamon.setDaemon(true);
            deamon.start();
        }
        /*
        * main线程终止后，守护线程全部自动终止
        * */
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(175);
    }
}
