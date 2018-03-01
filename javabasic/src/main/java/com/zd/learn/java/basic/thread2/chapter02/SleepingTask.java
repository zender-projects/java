package com.zd.learn.java.basic.thread2.chapter02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff{


    @Override
    public void run() {
        try {
            while (super.countDown-- > 0) {
                System.out.println(super.status());
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (Exception ex){
            System.out.println("Interrupted");
        }
    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new SleepingTask());
        }
        executorService.shutdown();
    }
}
