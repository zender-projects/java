package com.learn.concurrency.test;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class SynchronizedDemo {

    public  int counter = 0;

    private ReentrantLock lock = new ReentrantLock();

    public void increment(){
        //加锁
        lock.lock();
        try {
            counter++;
        }finally {
            //释放锁
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception{

        SynchronizedDemo demo1 = new SynchronizedDemo();
        //SynchronizedDemo demo2 = new SynchronizedDemo();
        //启动两个线程操作同一个对象，看最终结果是否为2000
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new IncrementTask(demo1));
        executorService.execute(new IncrementTask(demo1));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdown();
        System.out.println(demo1.getCounter());


    }
}

class IncrementTask implements Runnable {

    private SynchronizedDemo synchronizedDemo;

    public IncrementTask(SynchronizedDemo synchronizedDemo) {
        this.synchronizedDemo = synchronizedDemo;
    }

    @Override
    public void run() {
        for(int i = 0;i < 5;i ++) {
            synchronizedDemo.increment();
        }
    }
}
