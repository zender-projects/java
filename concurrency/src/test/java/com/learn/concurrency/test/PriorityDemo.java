package com.learn.concurrency.test;

import ch.qos.logback.core.util.ExecutorServiceUtil;

import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PriorityDemo implements Runnable{

    private int countDown = 5;
    private volatile double result;
    private int priority;

    public PriorityDemo(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return Thread.currentThread().getName() + ":" + Thread.currentThread().getPriority() + " : " + countDown;
    }

    @Override
    public void run() {
        //设置线程优先级
        Thread.currentThread().setPriority(priority);
        while (true) {
            //进行10万次浮点运算
            for(int i = 0;i < 100000;i ++) {
                result += (Math.PI + Math.E) / (double)i;
                if(i%1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if(--countDown == 0)
                return;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //提交5个优先级最小的线程任务
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new PriorityDemo(Thread.MIN_PRIORITY));
        }
        //提交1个优先级最大的任务
        executorService.execute(new PriorityDemo(Thread.MAX_PRIORITY));

        executorService.shutdown();
    }

}
