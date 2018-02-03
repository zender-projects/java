package com.zd.learn.java.basic.thread.defined.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DaemonFromFactory implements Runnable{
    @Override
    public void run() {
        try{
            while(true){
                TimeUnit.SECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        }catch (Exception ex){

        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for(int i = 0;i < 10;i ++) {
            exec.execute(new DaemonFromFactory());
        }

        System.out.println("All daemons started");
        //主线程直线完毕，Daemon线程就会立即被销毁
        TimeUnit.MILLISECONDS.sleep(50);
        exec.shutdown();

    }
}
