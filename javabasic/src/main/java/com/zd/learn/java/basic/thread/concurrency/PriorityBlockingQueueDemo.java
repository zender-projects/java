package com.zd.learn.java.basic.thread.concurrency;

//优先级队列Demo

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

//带有优先级的Task
class PrioritizedTask implements Runnable, Comparable<PrioritizedTask>{

    private Random random = new Random();
    private static int counter = 0;
    private int id = ++ counter;
    private int priority;

    protected static List<PrioritizedTask> sequence =
            new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(PrioritizedTask o) {
        return priority < o.priority ? 1 : (priority > o.priority ? -1 : 0);
    }

    @Override
    public void run() {
        try{
            TimeUnit.MILLISECONDS.sleep(random.nextInt(250));
        }catch (Exception ex) {

        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public String summary(){
        return "(" + id + ":" + priority + ")";
    }

    //优先级队列中的末端哨兵
    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService executorService;
        public EndSentinel(ExecutorService executorService) {
            super(-1); // 最低优先级
            this.executorService = executorService;
        }

        @Override
        public void run() {
            int count = 0;
            for(PrioritizedTask task : sequence) {
                System.out.println(task.summary());
                if( ++ count == 5){
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            this.executorService.shutdownNow();
        }
    }
}

//优先级Task的生产者
class PrioritizedTaskProducer implements Runnable {

    private Random random = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService executorService;
    public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService executorService){
        this.queue = queue;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        //随机优先级
        for(int i = 0;i < 20;i ++ ) {
            queue.add(new PrioritizedTask(random.nextInt(10)));
            Thread.yield();
        }

        //最高优先级
        try{
            //最高优先级
            for(int i = 0;i < 10;i ++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }

            //最低优先级
            for(int i = 0;i < 10;i ++) {
                queue.add(new PrioritizedTask(i));
            }

            //末端哨兵
            queue.add(new PrioritizedTask.EndSentinel(executorService));
        }catch (Exception ex) {

        }
        System.out.println("Finished PrioritizedTaskProducer");
    }
}


//Task消费者
class PrioritizedTaskConsumer implements Runnable {

    private PriorityBlockingQueue<Runnable> priorityBlockingQueue;

    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue) {
        this.priorityBlockingQueue = queue;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                PrioritizedTask task = (PrioritizedTask) priorityBlockingQueue.take();
                System.out.println(task.summary());
            }
        }catch (Exception ex) {

        }
        System.out.println("Finished PrioritizedTaskConsumer");
    }

}

public class PriorityBlockingQueueDemo {

    public static void main(String[] args){
        Random random = new Random(47);
        ExecutorService executorService = Executors.newCachedThreadPool();
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();

        executorService.execute(new PrioritizedTaskProducer(queue,executorService));
        executorService.execute(new PrioritizedTaskConsumer(queue));
    }

}
