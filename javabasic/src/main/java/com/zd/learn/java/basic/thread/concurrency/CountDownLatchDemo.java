package com.zd.learn.java.basic.thread.concurrency;


import java.awt.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/** 工作任务 */
class TaskPortion implements Runnable {

    private static int counter = 0;
    private final int id  = counter ++;
    private static Random random = new Random(47);
    private final CountDownLatch countDownLatch;

    public TaskPortion(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            doWork();
            countDownLatch.countDown();
        }catch (Exception ex) {

        }
    }

    public void doWork() throws Exception {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
        System.out.println(this + " completed!");
    }

    public String toString(){
        return String.format("%1$-3d", id);
    }
}

class WaitingTask implements Runnable {

    private static int counter = 0;
    private final int id = counter ++;
    private final CountDownLatch countDownLatch;
    public WaitingTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            System.out.println("Waiting task " + id + " is waiting...");
            countDownLatch.wait();
            System.out.println("Latch barrier passed for " + this);
        }catch (Exception ex) {

        }
    }

    public String toString(){
        return String.format("WaitingTask %1$-3d", id);
    }
}


public class CountDownLatchDemo {

    static final int SIZE = 100;
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for(int i = 0;i < 10;i ++) {
            executorService.execute(new WaitingTask(countDownLatch));
        }

        for(int i = 0;i < SIZE;i ++) {
            executorService.execute(new TaskPortion(countDownLatch));
        }

        System.out.println("Launched all tasks.");
        executorService.shutdown();
    }

}
