package com.learn.concurrency.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExceptionHandleTest {

    public static void main(String[] args) {
        //全局异常
        //Thread.setDefaultUncaughtExceptionHandler(new CustomizeExceptionHandler());
        /*Thread thread = new Thread(new ExceptionTask());
        thread.setUncaughtExceptionHandler(new CustomizeExceptionHandler());
        thread.start();

        Thread thread2 = new Thread(new ExceptionTask());
        thread2.setUncaughtExceptionHandler(new CustomizeExceptionHandler2());
        thread2.start();*/
        ExecutorService executorService = Executors.newCachedThreadPool(new CustomizeThreadFactory());
        executorService.execute(new ExceptionTask());
        executorService.shutdown();
        /*try {
            Thread thread = new Thread(new ExceptionTask());
            thread.start();
        }catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }

}

class ExceptionTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Exception Task ");
        throw new RuntimeException("Thread Exception");
    }
}

class CustomizeExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("catch thread exception:" + e);
    }
}
class CustomizeExceptionHandler2 implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("catch thread exception2:" + e);
    }
}

class CustomizeThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setUncaughtExceptionHandler(new CustomizeExceptionHandler());
        return  thread;
    }
}

