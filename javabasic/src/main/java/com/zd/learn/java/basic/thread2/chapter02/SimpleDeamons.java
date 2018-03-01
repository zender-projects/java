package com.zd.learn.java.basic.thread2.chapter02;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//deamon thread test
public class SimpleDeamons implements Runnable{


    @Override
    public void run() {
        try {
            while (true) {

                TimeUnit.MILLISECONDS.sleep(1000);

                System.out.println(Thread.currentThread() + ":" + this);
            }

        }catch (Exception ex){
            System.out.println("Interrupted");
        }
    }

    public static void main(String[] args) throws Exception {
        for(int i = 0;i < 10;i ++) {
            Thread deamon = new Thread(new SimpleDeamons());
            deamon.setDaemon(true);
            deamon.start();
        }
        System.out.println("All deamons started");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Main Thread End");
    }


}
