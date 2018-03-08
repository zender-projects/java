package com.zd.learn.java.basic.thread2.chapter03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {

    private int currentValue = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public int next() {
        lock.lock();
        try {
            ++ currentValue;
            ++ currentValue;

            return currentValue;
        }finally {
            lock.unlock();
        }
    }


}
