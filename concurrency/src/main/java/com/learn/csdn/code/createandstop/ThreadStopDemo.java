package com.learn.csdn.code.createandstop;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

public class ThreadStopDemo {




    public static class RunnableTask1 implements Runnable {
        @Getter
        @Setter
        private volatile boolean canceled = false;
        @Override
        public void run() {
            try {
                while (!canceled) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " is running");
                }
                System.out.println(Thread.currentThread().getName() + " stoped, flag:" + canceled);
            }catch (Exception ex) {

            }
        }
    }

    public static void main(String[] args) throws Exception {
        RunnableTask1 runnable = new RunnableTask1();
        Thread t = new Thread(runnable);
        t.start();
        Thread t2 = new Thread(runnable);
        t2.start();
        TimeUnit.SECONDS.sleep(3);
        runnable.canceled = true;
    }

}
