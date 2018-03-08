package com.zd.learn.java.basic.thread2.chapter03;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventChecker implements Runnable{

    private final int id;
    private IntGenerator intGenerator;

    public EventChecker(int id, IntGenerator intGenerator) {
        this.id = id;
        this.intGenerator = intGenerator;
    }

    @Override
    public void run() {
        while(!intGenerator.isCanceled()){
            int val = intGenerator.next();
            if(val % 2 != 0) {
                System.out.println(val + " is not even!");
                intGenerator.cancel();
            }
        }
    }

    public static  void test(IntGenerator generator, int count) {
        System.out.println("Press Ctrl+C to exit");
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i < count; i ++) {
            executorService.execute(new EventChecker(i, generator));
        }
        executorService.shutdown();
    }


    public static void test(IntGenerator generator) {
        test(generator, 10);
    }
}
