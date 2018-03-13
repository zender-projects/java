package com.zd.learn.java.basic.thread2.chapter05;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

class ToastQueue extends LinkedBlockingDeque<Toast> {}


//生产吐司
class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count;
    private Random random = new Random();

    public Toaster(ToastQueue queue) {
        this.toastQueue = queue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(500));
                Toast toast = new Toast(count ++);
                System.out.println(toast);

                toastQueue.push(toast);
            }
        }catch (InterruptedException ex) {
            System.out.println("Toaster interrupted.");
        }
        System.out.println("toaster off.");
    }
}

//抹黄油
class Butter implements Runnable {
    private ToastQueue queue, butterQueue;
    public Butter(ToastQueue queue, ToastQueue butterQueue) {
        this.queue = queue;
        this.butterQueue = butterQueue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                Toast t = queue.take();
                t.butter();
                System.out.println(t);
                butterQueue.put(t);
            }
        }catch (InterruptedException ex) {
            System.out.println("Butter interrupted");
        }
        System.out.println("Butter off");
    }
}

class Jammer implements Runnable {
    private ToastQueue butterQueue, finishQueue;
    public Jammer(ToastQueue butterQueue, ToastQueue finishQueue) {
        this.butterQueue = butterQueue;
        this.finishQueue = finishQueue;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                Toast toast = butterQueue.take();
                toast.jam();
                System.out.println(toast);
                finishQueue.put(toast);
            }
        }catch (Exception ex) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}

//消费者
class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int counter = 0;
    public Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                Toast toast = finishedQueue.take();
                if(toast.getId() != counter ||  toast.getStatus() != Toast.Status.JAMMED) {
                    System.out.println("Error >>>" + toast);
                    System.exit(1);
                }else{
                    System.out.println("Chomp! " + toast);
                }
            }
        }catch (Exception ex) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}
public class ToastOMatic {
    public static void main(String[] args) throws Exception{
        ToastQueue dryQueue = new ToastQueue(),
                    butterQueue = new ToastQueue(),
                    finishQueue = new ToastQueue();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Toaster(dryQueue));
        executorService.execute(new Butter(dryQueue, butterQueue));
        executorService.execute(new Jammer(butterQueue, finishQueue));

        executorService.execute(new Eater(finishQueue));

        TimeUnit.SECONDS.sleep(10);
        executorService.shutdown();
    }
}
