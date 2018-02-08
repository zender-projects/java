package com.zd.learn.java.basic.thread.cooperation;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Blocker {

    synchronized void waitingCall(){
        try{
            while(!Thread.interrupted()) {
                wait();
                System.out.println(Thread.currentThread() + "  ");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    synchronized void prod(){ notify(); }

    synchronized void prodAll(){ notifyAll(); }

}

class Task implements Runnable {

    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}
//两个Task在不同的对象锁上调用wait
class Task2 implements Runnable {

    static Blocker blocker = new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}


public class NotifyVSNotifyAll {


    public static void main(String[] args) throws  Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        //启动5个Task
        for(int i = 0;i < 5;i ++) {
            exec.execute(new Task());
        }
        //启动一个Task2
        exec.execute(new Task2());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod = true;
            @Override
            public void run() {
                if(prod) {
                    System.out.println("\n notify()");
                    Task.blocker.prod();  //唤醒一个
                    prod = false;
                }else{
                    System.out.println("\n notifyAll()");
                    Task.blocker.prodAll();  //唤醒所有
                    prod = true;
                }
            }
        },400, 400);

        TimeUnit.SECONDS.sleep(5);

        timer.cancel();

        System.out.println("\nTimer canceled");

        TimeUnit.MILLISECONDS.sleep(500);

        System.out.println("Task2 blocker.prodAll()");

        Task2.blocker.prodAll();

        TimeUnit.MILLISECONDS.sleep(500);

        System.out.println("\nShutting down");

        exec.shutdownNow();
    }

}

