package com.zd.learn.java.basic.thread.synchronize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator{

    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();


    @Override
    public int next() {
        //显式加锁
        lock.lock();
        try{
            ++ currentEvenValue;
            Thread.yield();
            ++ currentEvenValue;

            return currentEvenValue;
        }finally {
            //显式解锁
            lock.unlock();
        }
    }

    public static void main(String[] args){
        EvenChecker.test(new MutexEvenGenerator());
    }
}
