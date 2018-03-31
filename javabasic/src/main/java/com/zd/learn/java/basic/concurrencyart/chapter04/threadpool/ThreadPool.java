package com.zd.learn.java.basic.concurrencyart.chapter04.threadpool;

public interface ThreadPool<Job extends Runnable>{

    void execute(Job job);

    void shutdown();

    void addWorkders(int num);

    void removeWorkers(int num);

    int getJobSize();
}
