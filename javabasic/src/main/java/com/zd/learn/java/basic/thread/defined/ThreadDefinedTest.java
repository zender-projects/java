package com.zd.learn.java.basic.thread.defined;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ThreadDefinedTest {

    public static void main(String[] args) throws Exception{

        //lambda thread
        //new Thread(() -> System.out.println("Hello World")).start();


        //启动callable thread
        FutureTask<String> futureTask = new FutureTask<String>(new Thread4());
        new Thread(futureTask).start();
        String rs = futureTask.get(2, TimeUnit.SECONDS);
        System.out.println(rs);


        //获取和设置线程名称
        Thread.currentThread().setName("CurrentThreadName");
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName);
    }
}

/**
 * 带返回值的线程
 *
 * 1.Thread类不能直接接受Callable
 * 2.FutureTask可以接收Callable
 * 3.FutureTask能获得返回值
 * 4.FutureTask implements RunnableFuture
 * 5.RunnableFuture extends Runnable
 * 6.Thread就可以接收一个FutureTask
 * */

class Thread4 implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "ok";
    }
}

class Thread1 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}


class Thread2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}


class Thread3 implements Callable<String>  {

    @Override
    public String call() throws Exception {
        String str = "";
        for (int i = 0; i < 10; i++) {
            str = str + ",";
        }
        return str;
    }
}