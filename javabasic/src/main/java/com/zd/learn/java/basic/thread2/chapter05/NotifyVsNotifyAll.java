package com.zd.learn.java.basic.thread2.chapter05;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * wait() notify() notifyAll() 只针对同一把锁有效
 * */

class Blocker {
    synchronized void waitingCall(){
        try{
            while(!Thread.interrupted()){
                wait();
                System.out.println(Thread.currentThread() + "  ");
            }
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    synchronized void prod(){ notify();}

    synchronized void prodAll() { notifyAll(); }
}

class Task implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

public class NotifyVsNotifyAll {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new Task());
        }

        executorService.execute(new Task2());

        //周期性调用Task的notify和notifyAll
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            boolean prod = true;
            @Override
            public void run() {
                if(prod) {
                    System.out.println("\n notify()");
                    Task.blocker.prod();
                    prod = false;
                }else {
                    System.out.println("\n notifyAll()");
                    Task.blocker.prodAll();
                    prod = true;
                }

            }
        },400, 400);


        TimeUnit.SECONDS.sleep(5);
        timer.cancel();

        System.out.println("Timer canceled.");

        TimeUnit.MILLISECONDS.sleep(200);

        System.out.println("Task2.blocker.prodAll()");
        Task2.blocker.prodAll();

        TimeUnit.MILLISECONDS.sleep(500);

        System.out.println("ShutdownNow()");
        executorService.shutdownNow();
    }

}
