package com.learn.concurrency.test;

import java.util.concurrent.TimeUnit;

public class DaemonTest {

    public static void main(String[] args) throws Exception {

        //启动10个daemon线程
        for (int i = 0;i < 10;i ++) {
            Thread thread = new Thread(new DaemonTask());
            thread.setDaemon(true);
            thread.start();
        }
        System.out.println("All Daemon Thread have been started.");
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Main Thread has completed.");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("JVM Exit!");
            }
        });
    }
}

class DaemonTask implements Runnable {

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                System.out.println("Daemon thread is running...");
                TimeUnit.SECONDS.sleep(1);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName() + " : Daeming finally block.");
        }
    }
}
