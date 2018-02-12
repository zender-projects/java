package com.zd.learn.java.basic.thread.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 *  DelayQueue是一个无界队列，用于放置实现了Delayed接口到对象
 *  其中到对象只能在其到期时才能从队列中取走
 *  这种队列是有序到，即 队头对象到延迟到期时间最长
 * */
class DelayedTask implements Runnable, Delayed {

    private static int counter = 0;
    private int id = ++ counter;
    private final int delta;
    private final long trigger;
    protected List<DelayedTask> sequence = new ArrayList<DelayedTask>();

    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delayInMilliseconds, TimeUnit.MILLISECONDS);

        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {

        DelayedTask that = (DelayedTask)o;
        if(trigger < that.trigger) {
            return - 1;
        }
        if(trigger > that.trigger) {
            return 1;
        }

        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }
    @Override
    public String toString(){
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary(){
        return "("+ id +":"+delta+")";
    }


    public static class EndSentinel extends DelayedTask {
        private ExecutorService ex;
        public EndSentinel(int delay, ExecutorService ex) {
            super(delay);
            this.ex = ex;
        }
        @Override
        public void run(){
            for(DelayedTask pt : sequence) {
                System.out.println(pt.summary() + "  ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            ex.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {

    private DelayQueue<DelayedTask> q;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                q.take().run();
            }
        }catch (Exception ex) {

        }
        System.out.println("Finished DelayedTaskConsumer!");
    }
}

public class DelayQueueDemo {

    public static void main(String[] args) {
        Random random = new Random();
        ExecutorService executorService = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();

        for(int i = 0;i < 20;i ++) {
            queue.put(new DelayedTask(random.nextInt(5000)));
        }

        queue.add(new DelayedTask.EndSentinel(5000, executorService));
        executorService.execute(new DelayedTaskConsumer(queue));
    }

}
