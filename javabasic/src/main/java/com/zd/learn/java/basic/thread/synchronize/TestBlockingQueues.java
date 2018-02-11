package com.zd.learn.java.basic.thread.synchronize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

class LiftOff implements Runnable{

    private int countDown = 0;
    private static int taskCount = 0;
    private final int id = taskCount ++;

    public LiftOff(){ }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "("+ (countDown > 0 ? countDown : "LiftOff!") +")";
    }

    @Override
    public void run() {
        while(countDown -- > 0) {
            System.out.println(status());
            Thread.yield();
        }
    }
}

class LiftOffRunner implements Runnable {

    private BlockingQueue<LiftOff> rockets;
    public LiftOffRunner(BlockingQueue<LiftOff> rockets) {
        this.rockets = rockets;
    }


    /* 向队列中添加元素 */
    public void add(LiftOff lo) {
        try{
            rockets.put(lo);
        }catch (InterruptedException ex) {

        }
    }

    /* 利用线程从队列中获取元素 */
    @Override
    public void run() {

        try{
            while(!Thread.interrupted()) {
                /* 从阻塞队列中获取元素 */
                LiftOff rockt = rockets.take();
                rockt.run();
            }
        } catch (InterruptedException ex) {
            System.out.println("Waking from take()");
        }
        System.out.println("Exiting LiftOffRunner!");
    }
}

public class TestBlockingQueues {


    static void getKey() {
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    static void getKey(String message) {
        System.out.println(message);
        getKey();
    }

    static void test(String msg, BlockingQueue<LiftOff> queue) {
        System.out.println(msg);
        LiftOffRunner runner = new LiftOffRunner(queue);
        Thread t = new Thread(runner);
        t.start();
        for(int i = 0;i < 5;i ++) {
            runner.add(new LiftOff(5));
        }
        getKey("Press 'Enter' (" +msg+")");
        t.interrupt();
        System.out.println("Finished " + msg + " test");
    }

    public static void main(String[] args) {
        //无界队列
        test("LinkedBlockingQueue", new LinkedBlockingDeque<LiftOff>());
        //有界队列
        //test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
        //同步队列
        //test("SynchronousQueue", new SynchronousQueue<LiftOff>());
    }
}
