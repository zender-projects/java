package com.zd.learn.java.basic.thread.synchronize;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {

    private ReentrantLock lock = new ReentrantLock();


    //尝试获取锁
    public void untimed(){
        boolean captured = lock.tryLock();
        try{
            System.out.println("tryLock():" + captured);
        }finally {
            if(captured){
                lock.unlock();
            }
        }
    }

    public void timed(){
        boolean captured = false;
        //在指定的时间内尝试获取锁
        try{
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        try{
            System.out.println("tryLock(2, TimeUnit.SECOND):" + captured);
        }finally {
            if(captured){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args){
        final  AttemptLocking attemptLocking = new AttemptLocking();
        attemptLocking.untimed();
        attemptLocking.timed();

        new Thread(){
            { setDaemon(true);}
            @Override
            public void run(){
                attemptLocking.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.yield();
        attemptLocking.untimed();
        attemptLocking.timed();
    }

}
