package com.zd.learn.java.basic.thread.synchronize;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

class Toast{
    /* 状态 */
    public enum Status { DRY, BUTTERED, JAMMED}
    /* 初始状态 */
    private Status status = Status.DRY;
    private final int id;
    public Toast(int id) {
        this.id = id;
    }
    public void buttered(){ status = Status.BUTTERED; }

    public void jam(){ status = Status.JAMMED; }

    public Status getStatus(){ return status; }

    public Integer getId(){ return id; }

    public String toString() {
        return "Toast " + id + ":" + status;
    }
}

/* 吐司队列 */
class ToastQueue extends LinkedBlockingDeque<Toast>{}

/**
 * 制作吐司的任务.
 * */
class Toaster implements Runnable {

    private ToastQueue queue;
    private int count = 0;
    private Random random = new Random(47);
    public Toaster(ToastQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                /* 制作吐司*/
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(500));
                Toast toast = new Toast(count ++);
                System.out.println("制作吐司:" + toast);
                //加入消费队列，供下一环节使用
                queue.put(toast);
            }
        }   catch (InterruptedException ex) {
            System.out.println("Toaster interrupted.");
        }
        System.out.println("Toaster off");
    }
}

/**
 * 涂抹黄油
 * */
class Butterer implements Runnable {

    private ToastQueue dryqueue, butteredQueue;
    public Butterer(ToastQueue dry, ToastQueue buttered) {
        this.dryqueue = dry;
        this.butteredQueue = buttered;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                //从吐司队列中获取制作好的吐司
                Toast t = dryqueue.take();
                //涂抹黄油
                t.buttered();
                System.out.println("涂抹黄油:" + t);
                //加入到待摸蕃茄酱队列
                butteredQueue.put(t);
            }
        }catch (InterruptedException ex) {
            System.out.println("Bufferer interrupted!");
        }
        System.out.println("Butter off!");
    }
}

/**
 * 摸蕃茄酱
 * */
class Jammer implements Runnable{

    private ToastQueue butteredQueue, finishedQueue;
    public Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                /* 取出摸好黄油的吐司 */
                Toast t = butteredQueue.take();
                //摸蕃茄酱
                t.jam();
                System.out.println("摸蕃茄酱:" + t);
                //加入到完成队列
                finishedQueue.put(t);
            }
        }catch (InterruptedException ex) {
            System.out.println("Jammer interrupted.");
        }
        System.out.println("Jammer off.");
    }
}

/* 顾客 */
class Eater implements Runnable {

    private ToastQueue finishedQueue;
    private int counter = 0;
    public Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                Toast t = finishedQueue.take();
                if(t.getId() != counter ++ ||
                        t.getStatus() != Toast.Status.JAMMED) {
                    System.out.println(">>>> Error: " + t);
                    System.exit(0);
                }else{
                    System.out.println("Chomp!" + t);
                }
            }
        }catch (Exception ex) {
            System.out.println("Eater interrupted.");
        }
        System.out.println("Eater off!");
    }
}

public class ToastOMatic {


    public static void main(String[] args) throws Exception {
        ToastQueue dryQueue = new ToastQueue(),
                   butteredQueue = new ToastQueue(),
                   finishedQueue = new ToastQueue();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Toaster(dryQueue));
        executorService.execute(new Butterer(dryQueue, butteredQueue));
        executorService.execute(new Jammer(butteredQueue, finishedQueue));

        executorService.execute(new Eater(finishedQueue));

        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
