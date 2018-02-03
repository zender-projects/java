package com.zd.learn.java.basic.thread.defined;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static  void main(String[] args) {
        singleThreadPool();  //序列化执行
    }

    public static void cachedThreadPool(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new LiftOff());
        }
        executorService.shutdown();
    }

    public static void fixedThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new LiftOff());
        }
        executorService.shutdown();
    }

    public static void singleThreadPool(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new LiftOff());
        }
        executorService.shutdown();
    }
}
