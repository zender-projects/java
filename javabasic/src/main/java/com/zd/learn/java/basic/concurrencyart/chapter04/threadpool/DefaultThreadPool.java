package com.zd.learn.java.basic.concurrencyart.chapter04.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool{

    //最大工作线程数
    private static final int MAX_WORKER_NUMBERS = 10;
    //默认工作线程数
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    //最小工作线程数
    private static final int MIN_WORKER_NUMBERS = 1;

    //任务队列
    private LinkedList<Job> jobs = new LinkedList<>();

    //工作线程队列
    private List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    //工作者线程数量
    private int workNum = DEFAULT_WORKER_NUMBERS;

    //编号生成器
    private AtomicLong threadNum = new AtomicLong();


    //初始化工作线程
    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        workNum = num > MAX_WORKER_NUMBERS ?  MAX_WORKER_NUMBERS :
                                              num < MIN_WORKER_NUMBERS ?
                                                      MIN_WORKER_NUMBERS : num;
        initializeWorkers(workNum);
    }


    //初始化工作线程
    private void initializeWorkers(int num) {
        for (int i = 0;i < num;i ++) {
            Worker worker = new Worker();
            workers.add(worker);
            //启动worker
            Thread thread = new Thread(worker,
                    "Thread-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }


    @Override
    public void execute(Runnable runnable) {
        if(runnable != null) {
            synchronized (jobs){
                //添加job
                jobs.addLast((Job) runnable);
                //通知work进行执行
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkders(int num) {
        //现在workd最大数量
        synchronized (jobs) {
            if(num + this.workNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workNum;
            }
            //初始化num个worker
            initializeWorkers(num);
            this.workNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs){
            if(num > this.workNum) {
                throw new IllegalArgumentException("beyond work nums");
            }
            int count = 0;
            while(count < num) {
                Worker worker = workers.get(count);
                if(workers.remove(worker)) {
                    worker.shutdown();
                    count ++;
                }
            }
            this.workNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    class Worker implements Runnable {

        //是否运行标记
        private volatile boolean running = true;

        @Override
        public void run() {
            while(running) {
                Job job = null;
                synchronized (jobs) {
                   while (jobs.isEmpty()) {
                       try{
                           jobs.wait();
                       }catch (Exception ex) {
                           Thread.currentThread().interrupt();
                           return;
                       }
                   }
                   //获取一个Job
                   job = jobs.removeFirst();
                }
                if(job != null) {
                    try{
                        job.run();
                    }catch (Exception ex) {

                    }
                }
            }
        }

        public void shutdown(){
            running = false;
        }
    }
}
