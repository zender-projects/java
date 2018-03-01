package com.zd.learn.java.basic.thread2.chapter02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//通过Executors 启动Daemon线程
public class DaemonFromFactory implements Runnable{

    @Override
    public void run() {
        try{
            while(true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + "  " + this);
            }
        }catch (Exception ex) {
            System.out.println("Interrupted");
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());

        for(int i = 0;i < 10;i ++) {
            executorService.execute(new DaemonFromFactory());
        }
        System.out.println("All daemon started");
        TimeUnit.SECONDS.sleep(5);

        System.out.println("Main Thread End");
    }
}
