package com.zd.learn.java.basic.thread.threadexception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ExceptionThread implements Runnable {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("Run() by " + t);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());

        throw new RuntimeException();
    }
}

class MyUncaughExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {

        System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
        System.out.println("created " + t);
        //设置异常处理器
        t.setUncaughtExceptionHandler(new MyUncaughExceptionHandler());
        System.out.println("eh=" + t.getUncaughtExceptionHandler());
        return t;
    }
}


public class CaptureUncaughException {

    public static void main(String[] args){
        //当前线程全局未捕获异常处理器
        //Thread.setDefaultUncaughtExceptionHandler(new MyUncaughExceptionHandler());

        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread());
    }

}
