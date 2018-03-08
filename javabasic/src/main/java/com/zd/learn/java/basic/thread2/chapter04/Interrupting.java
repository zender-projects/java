package com.zd.learn.java.basic.thread2.chapter04;

import com.zd.learn.java.basic.thread2.chapter02.SleepingTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Sleep阻塞
 * IO阻塞
 * 锁阻塞
 *
 * */
class SleepBlocked implements Runnable {

    @Override
    public void run() {
        try{
            TimeUnit.SECONDS.sleep(100);
        }catch (InterruptedException ex) {
            System.out.println("Interrupted exception");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }

}

class IOBlocked implements Runnable {

    private InputStream inputStream;

    public IOBlocked(InputStream in) {
        this.inputStream = in;
    }

    @Override
    public void run() {
        try{
            System.out.println("Waiting for read()");
            inputStream.read();
        }catch (IOException ex) {
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from blocked I/O");
            }else{
                throw new RuntimeException(ex);
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}

class SynchronizedBlocked implements Runnable {

    public synchronized void f(){
        while(true) {
            Thread.yield();
        }
    }

    public SynchronizedBlocked(){
        new Thread(){
            @Override
            public void run() {
                f();  //永远不释放锁
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("Try to call f()");

        f();

        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}


public class Interrupting {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    static void test(Runnable runnable) throws InterruptedException {
        Future<?> future = executorService.submit(runnable);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + runnable.getClass().getName());
        //interrupted by Future.cancel(true)
        future.cancel(true);
        System.out.println("Interrupte sent to " + runnable.getClass().getName());
    }





    public static void main(String[] args) throws Exception{
        test(new SleepingTask());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());

        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }

}
