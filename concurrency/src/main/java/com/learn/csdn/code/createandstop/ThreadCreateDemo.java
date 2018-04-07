package com.learn.csdn.code.createandstop;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadCreateDemo {




    public static class ThreadTask extends Thread {
        //覆盖run方法，在此处编写业务逻辑
        @Override
        public void run() {
            System.out.println("Hello, I'am ThreadTask");
        }
    }

    public static class RunnableTask implements Runnable {
        //覆盖run方法，在此处编写业务逻辑
        @Override
        public void run() {
            System.out.println("Hello, I'am RunnableTask");
        }
    }


    public static class CallableTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "Hello, I'am CallableTask";
        }
    }
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<String> callableTask = new CallableTask();
        Future<String> result =  executorService.submit(callableTask);
        System.out.println(result.get());
    }
}
