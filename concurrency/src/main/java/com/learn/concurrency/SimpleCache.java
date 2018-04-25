package com.learn.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用读写锁实现的简易缓存.
 * @author mac
 * */
public class SimpleCache {

    private static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static Lock readLock = reentrantLock.readLock();
    private static Lock writeLock = reentrantLock.writeLock();

    private static Map<String, Object> container = new HashMap<String, Object>();


    /**
     * 获取数据.
     * @param  key
     * @return Object
     * */
    public static final Object get(String key) {
        readLock.lock();
        try{
            return container.get(key);
        }finally {
            readLock.unlock();
        }
    }


    /**
     * 添加数据.
     * @param key
     * @param value
     * */
    public static final void set(String key, Object value) {
        writeLock.lock();
        try{
            container.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }


    /**
     * 清空缓存.
     * */
    public static final void clear(){
        writeLock.lock();
        try{
            container.clear();
        }finally {
            writeLock.unlock();
        }

    }

}
