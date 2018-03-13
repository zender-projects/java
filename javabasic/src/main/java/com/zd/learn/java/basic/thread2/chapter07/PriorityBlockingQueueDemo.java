package com.zd.learn.java.basic.thread2.chapter07;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {

    private Random random = new Random(47);
    private static int counter = 0;
    private final int id = ++ counter;
    private final int priority;
    protected List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

    public PrioritizedTask(int priority){
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(PrioritizedTask o) {
        return this.priority < o.priority ? 1 : (this.priority > o.priority ? -1 : 0);
    }

    @Override
    public void run() {
        try{
            TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
        }catch (InterruptedException ex) {

        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("%1$-3d", priority) + " Task " + id;
    }

    public String sammary(){
        return "(" + id + ":" + priority + ")";
    }

    //哨兵
    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService executorService;
        public EndSentinel(ExecutorService executorService) {
            super(-1);
            this.executorService = executorService;
        }

        @Override
        public void run() {
            int count = 0;
            for(PrioritizedTask task : sequence) {
                System.out.println(task.sammary());
                if(++ count %5 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()" );
            executorService.shutdownNow();
        }
    }
}


class PrioritizedTaskProducer implements Runnable {

    private Random random = new Random(47);
    private PriorityBlockingQueue<PrioritizedTask> queue;
    private ExecutorService executorService;
    public PrioritizedTaskProducer(PriorityBlockingQueue<PrioritizedTask> queue,
                                   ExecutorService executorService){
        this.queue = queue;
        this.executorService = executorService;
    }


    @Override
    public void run() {
        //random priority
        for(int i = 0;i < 20;i ++) {
            queue.add(new PrioritizedTask(random.nextInt(10)));
            Thread.yield();
        }
        //highest priority
        try{
            for(int i = 0;i < 10;i ++){
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }

            //lowest priority
            for(int i = 0;i < 10;i ++) {
                queue.add(new PrioritizedTask(i));
            }

            //end sentinel
            queue.add(new PrioritizedTask.EndSentinel(executorService));
        }catch (InterruptedException ex) {

        }
    }
}

class PrioritizedConsumer implements Runnable {

    private PriorityBlockingQueue<PrioritizedTask> queue;
    public PrioritizedConsumer(PriorityBlockingQueue<PrioritizedTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
     try{
         while (!Thread.interrupted()) {
             queue.take().run();
         }
     }   catch (InterruptedException ex) {

     }
        System.out.println("Finished PrioritizedTaskConsumer");
    }
}

public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {
        Random random = new Random(47);
        ExecutorService executorService = Executors.newCachedThreadPool();
        PriorityBlockingQueue<PrioritizedTask> queue = new PriorityBlockingQueue<>();
        executorService.execute(new PrioritizedTaskProducer(queue, executorService));
        executorService.execute(new PrioritizedConsumer(queue));
    }
}
