package com.zd.learn.java.basic.thread2.chapter02;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class TaskWithResult implements Callable<String> {

    private int id;
    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "result of TaskWithResult " + id;
    }
}
public class CallableDemo {
    public static void main(String[] args) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<String>> futures = new ArrayList<>();

        for(int i = 0;i < 10;i ++) {
            futures.add(executorService.submit(new TaskWithResult(i)));
        }

        for(Future<String> future : futures) {
            System.out.println(future.get());
        }

        executorService.shutdown();

    }
}
