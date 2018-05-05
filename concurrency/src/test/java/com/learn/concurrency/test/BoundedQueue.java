package com.learn.concurrency.test;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用Condition实现有界队列
 * @author mac
 * */
public class BoundedQueue {

    private Object[] items;
    private int addIndex, removeIndex, count;

    private Lock lock = new ReentrantLock();
    AbstractQueuedLongSynchronizer.ConditionObject
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    /**
     * 添加元素，如果队列满，则阻塞插入线程
     * */
    public void add(Object obj) throws Exception {
        lock.lock();

        try{

            while(items.length == count) {
                notFull.await();
            }
            items[addIndex] = obj;
            if(++ addIndex == items.length) {
                addIndex = 0;
            }
            count ++;
            notEmpty.signal();

        }finally {
            lock.unlock();
        }
    }

    /**
     * 从队列中获取元素，如果为空，则阻塞获取线程
     * */
    public Object take() throws Exception {
        lock.lock();
        try{

            while(count == 0) {
                notEmpty.await();
            }
            Object obj = items[removeIndex];
            if(++ removeIndex == items.length){
                removeIndex = 0;
            }
            -- count;
            return obj;

        }finally {
            lock.unlock();
        }
    }

}
