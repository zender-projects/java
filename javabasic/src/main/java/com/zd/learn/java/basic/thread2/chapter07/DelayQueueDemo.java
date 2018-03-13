package com.zd.learn.java.basic.thread2.chapter07;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class DelayedTask implements Runnable, Delayed {

    private static int counter = 0;
    private final int id = counter ++;
    private final int delta;
    private final long trigger;

    protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();

    public DelayedTask(int delayMilliseconds) {
        //延迟时间
        delta = delayMilliseconds;
        //触发时间
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }



    @Override
    public long getDelay(TimeUnit unit) {
        //获取剩余时间
        return TimeUnit.MILLISECONDS.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask task = (DelayedTask)o;
        if(trigger < task.trigger) {
            return -1;
        }
        if(trigger > task.trigger) {
            return 1;
        }

        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary(){
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService executorService;
        public EndSentinel(int delay, ExecutorService executorService) {
            super(delay);
            this.executorService = executorService;
        }

        @Override
        public void run() {
            for(DelayedTask task : sequence) {
                System.out.println(task.summary());
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            executorService.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {

    private DelayQueue<DelayedTask> delayedTasks;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> queue) {
        this.delayedTasks = queue;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                delayedTasks.take().run();
            }
        }catch (InterruptedException ex) {

        }
    }
}

public class DelayQueueDemo {

    public static void main(String[] args){
        Random random = new Random(47);
        ExecutorService executorService = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for(int i = 0;i < 20;i ++) {
            queue.add(new DelayedTask(random.nextInt(5000)));
        }

        queue.add(new DelayedTask.EndSentinel(5000, executorService));

        executorService.execute(new DelayedTaskConsumer(queue));
    }
}
