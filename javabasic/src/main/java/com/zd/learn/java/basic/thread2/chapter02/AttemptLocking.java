package com.zd.learn.java.basic.thread2.chapter02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {

    private ReentrantLock lock = new ReentrantLock();

    //不设置超时时间
    public void untimed() {
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
        try{
            captured = lock.tryLock(2 , TimeUnit.SECONDS);
        }catch (InterruptedException ex){
            throw new RuntimeException();
        }

        try{
            System.out.println("tryLock(2, TimeUnit.SECONDS):" + captured);
        }finally {
            if(captured){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args)  {

    }

}
